package com.github.ochuerta.grpc.calculator.server;

import com.proto.calculator.*;
import io.grpc.stub.StreamObserver;


public class CalculatorServiceImpl extends CalculatorServiceGrpc.CalculatorServiceImplBase {

    // This method overrides the sum method from the base class
    // It takes a SumRequest object and a StreamObserver<SumResponse> object as input
    @Override
    public void sum(SumRequest request, StreamObserver<SumResponse> responseObserver) {

        // This comment indicates that the code below extracts the necessary fields from the request
        // extract the fields we need from the request

        // A new SumResponse object is created using the SumResponse.newBuilder() method
        // The setSumResult method is called on the builder to set the sum result
        // The sum result is calculated by adding the firstNumber and secondNumber from the request
        SumResponse sumResponse = SumResponse.newBuilder()
                .setSumResult(request.getFirstNumber() + request.getSecondNumber())
                .build();

        // The onNext method is called on the responseObserver to send the sumResponse back
        responseObserver.onNext(sumResponse);

        // The onCompleted method is called on the responseObserver to indicate the end of the response
        responseObserver.onCompleted();
    }

    @Override
    public void primeNumberDecomposition(PrimeNumberDecompositionRequest request, StreamObserver<PrimeNumberDecompositionResponse> responseObserver) {
        Long number = request.getNumber();
        Long divisor = 2L;

        while (number > 1) {
            if (number % divisor == 0) {
                number = number / divisor;
                responseObserver.onNext(PrimeNumberDecompositionResponse.newBuilder()
                        .setPrimeFactor(divisor)
                        .build());
            } else {
                divisor++;
            }
        }
        responseObserver.onCompleted();
    }
}
