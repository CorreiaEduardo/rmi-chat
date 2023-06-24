package br.uneb.sis035.rmichat;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface DiscoveryServer extends Remote {
    void registerServer(DiscoverableChatServer server) throws RemoteException;
    List<DiscoverableChatServer> findSiblings(DiscoverableChatServer target) throws RemoteException;
}
