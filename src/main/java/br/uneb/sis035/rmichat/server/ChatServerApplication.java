package br.uneb.sis035.rmichat.server;

import br.uneb.sis035.rmichat.ChatServer;
import br.uneb.sis035.rmichat.DiscoveryServer;
import redis.clients.jedis.JedisPool;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ChatServerApplication {

    public static void main(String[] args) {
        try {
            final String serviceName = "ChatServer";
            JedisPool pool = new JedisPool("localhost", 6379);
            DiscoveryServer discoveryServer = (DiscoveryServer) Naming.lookup("rmi://localhost:1099/DiscoveryServer");

            // Create and export the chat server
            ChatServer chatServer = new ChatServerImpl(pool, discoveryServer);

            // Get the RMI registry (or create it if it doesn't exist)
            Registry registry = LocateRegistry.getRegistry(Registry.REGISTRY_PORT);
            try {
                // Bind the chat server to the registry
                registry.bind(serviceName, chatServer);
            } catch (Exception e) {
                registry = LocateRegistry.createRegistry(Registry.REGISTRY_PORT);
            } finally {
                registry.rebind(serviceName, chatServer);
                discoveryServer.registerServer(chatServer);
            }

            System.out.println("Chat server is running...");
        } catch (Exception e) {
            System.err.println("Error starting the chat server: " + e.getMessage());
        }
    }
}
