package br.uneb.sis035.rmichat.client;

import java.rmi.RemoteException;

public class ChatClientApplication {

    public static void main(String[] args) throws RemoteException {
        try {
            // Create and connect a chat client
            ChatClientImpl client1 = new ChatClientImpl("Client2");
            client1.connectToServer("rmi://localhost:2099/ChatServer");

            // Start the chat session
            client1.startChatting();
        } catch (Exception e) {
            System.err.println("Error running the chat client: " + e.getMessage());
        }
    }
}