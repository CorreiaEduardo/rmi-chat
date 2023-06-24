package br.uneb.sis035.rmichat;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ChatClient extends Remote {
    /**
     * Receives a message from the chat server.
     *
     * @param message The message received from the chat server.
     * @throws RemoteException If a remote communication error occurs.
     */
    void receiveMessage(Message message) throws RemoteException;

    /**
     * Retrieves the name of the chat client.
     *
     * @return The name of the chat client.
     * @throws RemoteException If a remote communication error occurs.
     */
    String getName() throws RemoteException;
}
