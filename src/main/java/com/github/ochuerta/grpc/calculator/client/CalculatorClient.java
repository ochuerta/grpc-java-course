package com.github.ochuerta.grpc.calculator.client;

import com.proto.calculator.CalculatorServiceGrpc;
import com.proto.calculator.PrimeNumberDecompositionRequest;
import com.proto.calculator.SumRequest;
import com.proto.calculator.SumResponse;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.Iterator;

import java.util.stream.StreamSupport;

public class CalculatorClient {
    public static void main(String[] args) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 50052)
                .usePlaintext()
                .build();

        CalculatorServiceGrpc.CalculatorServiceBlockingStub stub = CalculatorServiceGrpc.newBlockingStub(channel);

        // Unary
//        SumRequest request = SumRequest.newBuilder()
//                .setFirstNumber(10)
//                .setSecondNumber(100)
//                .build();
//
//        SumResponse response = stub.sum(request);
//
//        System.out.println("The sum of " + request.getFirstNumber() + " and " + request.getSecondNumber() + " = " + response.getSumResult());


        // Server Streaming

        Long number = 27234299232349234L;

        boolean allPrimeFactorsDecomposed = StreamSupport.stream(
                        Spliterators.spliteratorUnknownSize(
                                stub.primeNumberDecomposition(PrimeNumberDecompositionRequest.newBuilder()
                                        .setNumber(number).build()),
                                0),
                        false)
                .anyMatch(primeNumberDecompositionResponse -> {
                    System.out.println("Prime factor: " + primeNumberDecompositionResponse.getPrimeFactor());
                    if (primeNumberDecompositionResponse.getPrimeFactor() == number) {
                        // If the prime factor is equal to the original number,
                        // it means all prime factors have been decomposed
                        return true; // Stop the stream
                    }
                    return false; // Continue the stream
                });

        if (allPrimeFactorsDecomposed) {
            System.out.println("All prime factors have been decomposed.");
        }

        channel.shutdown();


    }
}
