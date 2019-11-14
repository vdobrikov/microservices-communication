package com.vdobrikov.webservice.service.rpc.client;

import com.google.common.util.concurrent.ListenableFuture;
import com.vdobrikov.rpc.GreeterGrpc;
import com.vdobrikov.rpc.HelloReply;
import com.vdobrikov.rpc.HelloRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AsyncRpcClient extends AbstractRpcClient {
    private static final Logger LOG = LoggerFactory.getLogger(AsyncRpcClient.class);

    private final GreeterGrpc.GreeterFutureStub stub;

    public AsyncRpcClient(String host, int port) {
        super(host, port);
        this.stub = GreeterGrpc.newFutureStub(channel);
    }

    public ListenableFuture<HelloReply> greet() {
        LOG.info("Greeting..");
        HelloRequest request = HelloRequest.newBuilder().build();
        return stub.sayHello(request);
    }
}
