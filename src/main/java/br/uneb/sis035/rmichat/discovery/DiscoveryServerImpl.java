package br.uneb.sis035.rmichat.discovery;

import br.uneb.sis035.rmichat.ChatServer;
import br.uneb.sis035.rmichat.DiscoveryServer;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DiscoveryServerImpl extends UnicastRemoteObject implements DiscoveryServer {

    private final List<ChatServer> servers;

    protected DiscoveryServerImpl() throws RemoteException {
        this.servers = new ArrayList<>();
    }

    @Override
    public void registerServer(ChatServer server) throws RemoteException {
        this.servers.add(server);
    }

    @Override
    public List<ChatServer> findSiblings(ChatServer target) throws RemoteException {
        return servers.stream().filter(it -> !it.equals(target)).collect(Collectors.toList());
    }
}
