package com.vdobrikov;

import com.vdobrikov.rpc.GreeterGrpc;
import com.vdobrikov.rpc.HelloReply;
import com.vdobrikov.rpc.HelloRequest;
import io.grpc.stub.StreamObserver;

public class GreeterGrpcImpl extends GreeterGrpc.GreeterImplBase {

    private final String serviceId;

    public GreeterGrpcImpl(String serviceId) {
        this.serviceId = serviceId;
    }

    @Override
    public void sayHello(HelloRequest request, StreamObserver<HelloReply> responseObserver) {
        HelloReply reply = HelloReply.newBuilder()
                .setMessage("Hello from service " + serviceId)
                .build();

        responseObserver.onNext(reply);
        responseObserver.onCompleted();
    }
}
