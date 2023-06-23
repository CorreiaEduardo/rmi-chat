package br.uneb.sis035.rmichat.client;

import br.uneb.sis035.rmichat.ChatClient;
import br.uneb.sis035.rmichat.ChatServer;
import br.uneb.sis035.rmichat.Message;

import lombok.Getter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.time.format.DateTimeFormatter;

public class ChatClientImpl extends UnicastRemoteObject implements ChatClient {
    @Getter private String name;
    private ChatServer chatServer;

    public ChatClientImpl(String name) throws RemoteException {
        this.name = name;
    }

    @Override
    public void receiveMessage(Message message) throws RemoteException {
        String messageStr = "[" + message.getTimestamp().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")) + "] " +
                message.getSender().getName() + ": " +
                message.getMessage();

        System.out.println(messageStr);
    }

    public void connectToServer(String serverUrl) throws RemoteException {
        try {
            this.chatServer = (ChatServer) Naming.lookup(serverUrl);
            this.chatServer.registerClient("general", this);
            System.out.println("Connected to the chat server.");
        } catch (Exception e) {
            System.err.println("Error connecting to the chat server: " + e.getMessage());
        }
    }

    public void startChatting() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Chat session started. Enter '/quit' to exit.");

        try {
            String input;
            while ((input = reader.readLine()) != null) {
                if (input.equalsIgnoreCase("/quit")) {
                    break;
                }
                sendMessage(input);
                System.out.print("\r");
            }
        } catch (IOException e) {
            System.err.println("Error reading user input: " + e.getMessage());
        }

        System.out.println("Chat session ended.");
    }

    private void sendMessage(String message) throws RemoteException {
        final Message general = new MessageImpl(this, "general", message);
        chatServer.broadcastMessage(general);
    }

}
