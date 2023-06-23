package br.uneb.sis035.rmichat.server;

import br.uneb.sis035.rmichat.ChatServer;
import redis.clients.jedis.JedisPool;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ChatServerApplication {

    public static void main(String[] args) {
        try {
            JedisPool pool = new JedisPool("localhost", 6379);

            // Create and export the chat server
            ChatServer chatServer = new ChatServerImpl(pool);

            // Get the RMI registry (or create it if it doesn't exist)
            Registry registry = LocateRegistry.createRegistry(2099);

            // Bind the chat server to the registry
            registry.bind("ChatServer", chatServer);

            System.out.println("Chat server is running...");
        } catch (Exception e) {
            System.err.println("Error starting the chat server: " + e.getMessage());
        }
    }
}
