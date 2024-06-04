package com.github.ochuerta.grpc.greeting.client;

import com.proto.dummy.DummyServiceGrpc;
import com.proto.greet.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class GreetingClient {
    public static void main(String[] args) {
        System.out.println("Hello, I'm a gRPC Client!");

        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 50051)
                .usePlaintext()
                .build();

        // Add your gRPC client code here
        System.out.println("Creating stub");

        // create a greet service client (blocking - synchronous)

        // The code snippet below serves two purposes:
        // 1. It creates a client stub object 'greetClient' that allows the program to communicate with a gRPC server and
        //    call its methods. The GreetServiceGrpc.newBlockingStub(channel) method takes a channel object as input, which
        //    represents the connection to the gRPC server. The resulting greetClient object can be used to invoke remote
        //    methods on the server in a blocking manner, meaning the program will wait for the server's response before
        //    continuing.
        // 2. It constructs a Greeting object, which likely represents the data structure that will be sent to the gRPC
        //    server. The Greeting.newBuilder() method creates a builder object, which is then used to set the firstName and
        //    lastName fields of the Greeting object using the setFirstName("Oscar") and setLastName("Huerta") methods,
        //    respectively. Finally, the build() method is called to create the immutable Greeting object with the specified
        //    values.
        //
        // The output of this code snippet is the greetClient object, which can be used to invoke methods on the gRPC server,
        // and the greeting object, which contains the data to be sent to the server.
        //
        // The logic flow of this code is straightforward. First, it creates the greetClient object by calling
        // GreetServiceGrpc.newBlockingStub(channel) and passing in the channel object, which represents the connection to the
        // gRPC server. Then, it constructs the Greeting object by creating a builder, setting the firstName and lastName
        // fields, and finally building the immutable object.
        //
        // No complex data transformations or algorithms are involved in this code snippet. It simply creates the necessary
        // objects for communicating with the gRPC server and sending data to it.
        GreetServiceGrpc.GreetServiceBlockingStub greetClient = GreetServiceGrpc.newBlockingStub(channel);
        Greeting greeting = Greeting.newBuilder()
                .setFirstName("Oscar")
                .setLastName("Huerta")
                .build();

        // Unary
        // do the same for a GreetingRequest (using protocol buffers)
        // This code is creating an instance of the GreetingRequest class, which is likely a data
        // structure used to send a request to a gRPC server. Here's a breakdown of what's happening:
        // GreetingRequest.newBuilder() creates a new builder object for the GreetingRequest class.
        // Builders are a common pattern in Java for creating complex objects in a step-by-step manner.
        // .setGreeting(greeting) is a method call on the builder object that sets the greeting field
        // of the GreetingRequest object being built. The greeting variable is an instance of
        // another class (e.g., Greeting) that contains the actual greeting data to be sent in the
        // request.
        // .build() is a method call on the builder object that finalizes the construction of the
        // GreetingRequest object and returns an immutable instance of it.
        // The resulting GreetingRequest object is assigned to the greetingRequest variable.
        // So, the purpose of this code is to create a GreetingRequest object with the greeting field
        // set to the value of the greeting variable. This greetingRequest object can then be used to
        // send a request to a gRPC server, likely as part of a remote procedure call (RPC).
        // The input to this code is the greeting variable, which contains the data to be included in
        // the GreetingRequest object.
        // The output is the greetingRequest object, which represents the request to be sent to the
        // gRPC server.
        // The logic flow is straightforward:
        // Create a new builder object for GreetingRequest.
        // Set the greeting field on the builder using the greeting variable.
        // Build the immutable GreetingRequest object.
        // Assign the resulting object to the greetingRequest variable.
        // There are no complex data transformations or algorithms involved in this code snippet. It's
        // simply constructing a data object using the builder pattern, which is a common way to create
        // immutable objects in Java.
//        GreetingRequest greetingRequest = GreetingRequest.newBuilder()
//                .setGreeting(greeting)
//                .build();
//
//
//
//        // call RPC and get a response
//        GreetingResponse greetingResponse = greetClient.greet(greetingRequest);
//
//        System.out.println(greetingResponse.getResult());

        // Server Streaming
        GreetingManyTimesRequest greetingManyTimesRequest =
                GreetingManyTimesRequest.newBuilder()
                        .setGreeting("Oscar")
                        .build();

        greetClient.greetManyTimes(greetingManyTimesRequest).forEachRemaining(greetingManyTimesResponse -> {
            System.out.println(greetingManyTimesResponse.getResult());
        });

        // DummyServiceGrpc.DummyServiceFutureStub asyncClient = DummyServiceGrpc.newFutureStub(channel);

        // do something
        System.out.println("Shutting down channel");
        channel.shutdown();
    }
}

