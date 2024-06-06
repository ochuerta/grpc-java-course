package com.github.ochuerta.grpc.greeting.server;

import com.proto.greet.*;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GreetServiceImpl extends GreetServiceGrpc.GreetServiceImplBase {
    private static final Logger logger = LoggerFactory.getLogger(GreetServiceImpl.class);

    @Override
    public void greet(GreetingRequest request, StreamObserver<GreetingResponse> responseObserver) {
        try {
            // Extract the fields we need from the request
            Greeting greeting = request.getGreeting();
            String firstName = greeting.getFirstName();

            // Create the response message
            String result = "Hello " + firstName;
            GreetingResponse response = GreetingResponse.newBuilder()
                    .setResult(result)
                    .build();

            // Send the response
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            // Log the error and send an error response to the client
            logger.error("Error occurred while processing greet request", e);
            responseObserver.onError(Status.INTERNAL.withDescription(e.getMessage()).asException());
        }
    }

    @Override
    public void greetManyTimes(GreetingManyTimesRequest request, StreamObserver<GreetingManyTimesResponse> responseObserver) {
        String firstName = request.getGreeting();

        try {
            // Send a stream of GreetingManyTimesResponse messages to the client
            for (int i = 0; i < 10; i++) {
                String result = "Hello " + firstName + ", response number: " + i;
                GreetingManyTimesResponse response = GreetingManyTimesResponse.newBuilder()
                        .setResult(result)
                        .build();

                responseObserver.onNext(response);
                Thread.sleep(1000L); // Introduce a delay of 1 second before sending the next response
            }
        } catch (InterruptedException e) {
            // Handle the InterruptedException that may occur during Thread.sleep
            logger.error("Thread interrupted while sending greeting responses", e);
            responseObserver.onError(Status.INTERNAL.withDescription(e.getMessage()).asException());
            return; // Return immediately to prevent further execution
        } catch (Exception e) {
            // Handle any other exceptions that may occur during the execution of this code block
            logger.error("An error occurred while sending greeting responses", e);
            responseObserver.onError(Status.INTERNAL.withDescription(e.getMessage()).asException());
            return; // Return immediately to prevent further execution
        }

        try {
            // Indicate that the server has completed sending responses
            responseObserver.onCompleted();
        } catch (Exception e) {
            // Log any exceptions that may occur during the completion of the response stream
            logger.error("Error occurred while completing greetManyTimes response", e);
        }
    }

    @Override
    public StreamObserver<LongGreetRequest> longGreet(StreamObserver<LongGreetResponse> responseObserver) {
        StreamObserver<LongGreetRequest> streamObserverOfRequest = new StreamObserver<LongGreetRequest>() {

            String result = "";

            @Override
            public void onNext(LongGreetRequest value) {
                // client sends a message
            }

            @Override
            public void onError(Throwable t) {
                // client sends an error
            }

            @Override
            public void onCompleted() {
                // client is done sending messages

                // this is when we want to return a response (responseObserver)
            }
        };

        return streamObserverOfRequest;
    }
}