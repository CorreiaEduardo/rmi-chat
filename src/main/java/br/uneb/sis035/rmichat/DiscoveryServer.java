package br.uneb.sis035.rmichat;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface DiscoveryServer extends Remote {
    void registerServer(ChatServer server) throws RemoteException;
    List<ChatServer> findSiblings(ChatServer target) throws RemoteException;
}
