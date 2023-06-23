package br.uneb.sis035.rmichat.server;

import br.uneb.sis035.rmichat.ChatClient;
import br.uneb.sis035.rmichat.ChatServer;
import br.uneb.sis035.rmichat.Message;

import com.google.gson.Gson;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ChatServerImpl extends UnicastRemoteObject implements ChatServer {
    private final Map<String, List<ChatClient>> channels;
    private final JedisPool jedisPool;

    public ChatServerImpl(JedisPool pool) throws RemoteException {
        this.channels = new HashMap<>();
        this.jedisPool = pool;
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

        for (ChatClient client : channelClients) {
            client.receiveMessage(message);
        }

        storeMessage(message);
    }

    public List<Message> getChatHistory(String channel) throws RemoteException {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis
                    .lrange(channel, 0, -1)
                    .stream()
                    .map(it -> new Gson().fromJson(it, Message.class))
                    .collect(Collectors.toList());
        }
    }

    private void storeMessage(Message message) throws RemoteException {
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.rpush(message.getChannel(), new Gson().toJson(message));
        }
    }
}