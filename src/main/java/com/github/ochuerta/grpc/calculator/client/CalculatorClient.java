package com.github.ochuerta.grpc.calculator.client;

import com.proto.calculator.CalculatorServiceGrpc;
import com.proto.calculator.SumRequest;
import com.proto.calculator.SumResponse;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class CalculatorClient {
    public static void main(String[] args) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 50052)
                .usePlaintext()
                .build();

        CalculatorServiceGrpc.CalculatorServiceBlockingStub stub = CalculatorServiceGrpc.newBlockingStub(channel);

        SumRequest request = SumRequest.newBuilder()
                .setFirstNumber(10)
                .setSecondNumber(100)
                .build();

        SumResponse response = stub.sum(request);

        System.out.println("The sum of " + request.getFirstNumber() + " and " + request.getSecondNumber() + " = " + response.getSumResult());
    }
}
