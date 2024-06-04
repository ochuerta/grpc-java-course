package com.github.ochuerta.grpc.calculator.server;

import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;

public class CalculatorServer {

    // The code snippet server.start(); is responsible for starting the gRPC server that has been
    // configured in the previous lines of code.
    // In the context of a gRPC application, a server is a component that listens for incoming
    // requests from clients and processes them accordingly. The ServerBuilder class is used to
    // create a server instance with the desired configuration, such as the port number on which
    // the server should listen for incoming requests.
    // In this case, the code ServerBuilder.forPort(50052) creates a ServerBuilder instance
    // configured to listen on port 50052. The .addService(new CalculatorServiceImpl()) line adds
    // an instance of the CalculatorServiceImpl class as a service to the server. This service
    // likely implements the logic for handling requests related to calculator operations.
    // The .build() method constructs the actual Server instance based on the configuration
    // provided through the ServerBuilder.
    public static void main(String[] args) throws IOException, InterruptedException {
        Server server = ServerBuilder.forPort(50052)
                .addService(new CalculatorServiceImpl())
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
