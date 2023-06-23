package br.uneb.sis035.rmichat;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ChatClient extends Remote {
    void receiveMessage(Message message) throws RemoteException;
    String getName() throws RemoteException;
}
