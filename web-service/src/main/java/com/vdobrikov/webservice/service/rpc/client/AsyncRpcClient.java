package com.vdobrikov.webservice.service.rpc.client;

import com.google.common.util.concurrent.ListenableFuture;
import com.vdobrikov.rpc.GreeterGrpc;
import com.vdobrikov.rpc.HelloReply;
import com.vdobrikov.rpc.HelloRequest;
import io.grpc.ManagedChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nullable;
import java.io.Closeable;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class AsyncRpcClient implements Closeable {
    private static final Logger LOG = LoggerFactory.getLogger(AsyncRpcClient.class);

    private final GreeterGrpc.GreeterFutureStub stub;

    public AsyncRpcClient(ManagedChannel channel) {
        this.stub = GreeterGrpc.newFutureStub(channel);
    }

    public ListenableFuture<HelloReply> greet(@Nullable String name) {
        LOG.info("Greeting {}..", name);
        HelloRequest.Builder builder = HelloRequest.newBuilder();
        if (name != null) {
            builder.setName(name);
        }
        return stub.sayHello(builder.build());
    }

    @Override
    public void close() throws IOException {
        shutdown();
    }

    public void shutdown() {
        try {
            LOG.info("Shutting down..");
            ((ManagedChannel) stub.getChannel()).shutdown().awaitTermination(5, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            LOG.warn("Interrupted");
            shutdown();
        }
    }
}
