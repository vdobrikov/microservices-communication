package com.vdobrikov;

import com.vdobrikov.rpc.GreeterGrpc;
import com.vdobrikov.rpc.HelloReply;
import com.vdobrikov.rpc.HelloRequest;
import io.grpc.stub.StreamObserver;

import java.util.logging.Logger;

public class GreeterGrpcImpl extends GreeterGrpc.GreeterImplBase {
    private static final Logger LOG = Logger.getLogger(GreeterGrpcImpl.class.getName());

    private final String serviceId;

    public GreeterGrpcImpl(String serviceId) {
        this.serviceId = serviceId;
    }

    @Override
    public void sayHello(HelloRequest request, StreamObserver<HelloReply> responseObserver) {
        String name = request.getName();
        name = name.isEmpty() ? "[no-name]" : name;
        LOG.info(String.format("Handling request for %s..", name));
        HelloReply reply = HelloReply.newBuilder()
                .setMessage(String.format("Hello %s from service %s", name, serviceId))
                .build();

        responseObserver.onNext(reply);
        responseObserver.onCompleted();
    }
}
