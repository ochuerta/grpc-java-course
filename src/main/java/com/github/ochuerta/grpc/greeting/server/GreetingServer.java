package com.github.ochuerta.grpc.greeting.server;

import io.grpc.Server;
import io.grpc.ServerBuilder;


import java.io.IOException;

public class GreetingServer {
    public static void main(String[] args) throws IOException, InterruptedException {
        System.out.println("Hello gRPC!");

        Server server = ServerBuilder.forPort(50051)
                .addService(new GreetServiceImpl())
                .build();

        server.start();

        Runtime.getRuntime().addShutdownHook(new Thread( () -> {
            System.out.println("Received Shutdown Signal. Shutting down gRPC server gracefully...");
            server.shutdown();
            System.out.println("gRPC server shut down");
        }  ));

        server.awaitTermination();

    }
}
