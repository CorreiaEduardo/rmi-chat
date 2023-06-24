package br.uneb.sis035.rmichat;

import br.uneb.sis035.rmichat.ChatClient;
import br.uneb.sis035.rmichat.Message;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 * The ChatServer interface represents a server in a distributed chat system.
 * It provides methods for registering clients, broadcasting messages, retrieving
 * chat history, obtaining available channels, and synchronizing messages.
 */
public interface ChatServer extends Remote {

    /**
     * Registers a client with the specified channel.
     *
     * @param channel The channel to register the client to.
     * @param client The client to register.
     * @throws RemoteException If an error occurs during the remote method invocation.
     */
    void registerClient(String channel, ChatClient client) throws RemoteException;

    /**
     * Broadcasts a message to all clients in the specified channel.
     *
     * @param message The message to be broadcasted.
     * @throws RemoteException If an error occurs during the remote method invocation.
     */
    void broadcastMessage(Message message) throws RemoteException;

    /**
     * Retrieves the chat history for the specified channel.
     *
     * @param channel The channel to retrieve the chat history from.
     * @return The list of messages representing the chat history.
     * @throws RemoteException If an error occurs during the remote method invocation.
     */
    List<Message> getChatHistory(String channel) throws RemoteException;

    /**
     * Retrieves the list of available channels.
     *
     * @return The list of available channels.
     * @throws RemoteException If an error occurs during the remote method invocation.
     */
    List<String> getAvailableChannels() throws RemoteException;
}
