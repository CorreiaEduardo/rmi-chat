package br.uneb.sis035.rmichat;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface ChatServer extends Remote {

    /**
     * Registers the client on the server,
     *
     * @param channel the chat name, it is unique and identifies a chat
     * @param client the client to be registered
     * @return the client unique identification, used to create new chats
     * */
    void registerClient(String channel, ChatClient client) throws RemoteException;

    void broadcastMessage(Message message) throws RemoteException;

    List<Message> getChatHistory(String channel) throws RemoteException;

    List<String> getAvailableChannels() throws RemoteException;

    void synchronizeMessage(Message message) throws RemoteException;
}
