package br.uneb.sis035.rmichat;

import java.rmi.RemoteException;

public interface DiscoverableChatServer extends ChatServer {
    void synchronizeMessage(Message message) throws RemoteException;
}
