package br.uneb.sis035.rmichat.discovery;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class DiscoveryServerApplication {

    public static void main(String[] args) {
        try {
            final String serviceName = "DiscoveryServer";
            final DiscoveryServerImpl instance = new DiscoveryServerImpl();

            // Create the RMI registry
            Registry registry = LocateRegistry.createRegistry(Registry.REGISTRY_PORT);
            registry.bind(serviceName, instance);

            System.out.println("Discovery server is running...");
        } catch (Exception e) {
            System.err.println("Error starting the discovery server: " + e.getMessage());
        }
    }
}
