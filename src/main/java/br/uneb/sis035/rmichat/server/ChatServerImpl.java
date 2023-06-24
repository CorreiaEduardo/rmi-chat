package br.uneb.sis035.rmichat.server;

import br.uneb.sis035.rmichat.ChatClient;
import br.uneb.sis035.rmichat.DiscoverableChatServer;
import br.uneb.sis035.rmichat.DiscoveryServer;
import br.uneb.sis035.rmichat.Message;

import com.google.gson.Gson;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class ChatServerImpl extends UnicastRemoteObject implements DiscoverableChatServer {
    private final JedisPool jedisPool;
    private final DiscoveryServer discoveryServer;
    private final Map<String, List<ChatClient>> channels;
    private final Gson gson;

    public ChatServerImpl(JedisPool pool, DiscoveryServer discoveryServer) throws RemoteException {
        this.jedisPool = pool;
        this.discoveryServer = discoveryServer;
        this.channels = new HashMap<>();
        this.gson = new Gson();

        try (Jedis jedis = jedisPool.getResource()) {
            final Set<String> keys = jedis.keys("*");
            for (String key : keys) {
                channels.put(key, new ArrayList<>());
            }
        }
    }

    @Override
    public void registerClient(String channel, ChatClient client) throws RemoteException {
        final List<ChatClient> channelClients = channels.getOrDefault(channel, new ArrayList<>());
        channelClients.add(client);

        channels.put(channel, channelClients);
    }

    @Override
    public void broadcastMessage(Message message) throws RemoteException {
        final List<ChatClient> channelClients = channels.getOrDefault(message.getChannel(), new ArrayList<>());
        message.setTimestamp(LocalDateTime.now());

        for (DiscoverableChatServer sibling : discoveryServer.findSiblings(this)) {
            new Thread(() -> {
                try {
                    sibling.synchronizeMessage(message);
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
            })
                .start();
        }

        for (ChatClient client : channelClients) {
            new Thread(() -> {
                try {
                    client.receiveMessage(message);
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
            }).start();
        }

        storeMessage(message);
    }

    @Override
    public List<Message> getChatHistory(String channel) throws RemoteException {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis
                    .lrange(channel, 0, -1)
                    .stream()
                    .map(it -> gson.fromJson(it, MessageImpl.class))
                    .collect(Collectors.toList());
        }
    }

    @Override
    public List<String> getAvailableChannels() throws RemoteException {
        return new ArrayList<>(channels.keySet());
    }

    public void synchronizeMessage(Message message) throws RemoteException {
        final List<ChatClient> channelClients = channels.getOrDefault(message.getChannel(), new ArrayList<>());

        for (ChatClient client : channelClients) {
            client.receiveMessage(message);
        }
    }

    private void storeMessage(Message message) throws RemoteException {
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.rpush(message.getChannel(), gson.toJson(message));
        }
    }
}
