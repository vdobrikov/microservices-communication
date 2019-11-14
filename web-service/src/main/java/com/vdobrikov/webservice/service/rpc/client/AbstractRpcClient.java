package com.vdobrikov.webservice.service.rpc.client;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Closeable;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class AbstractRpcClient implements Closeable {
    private static final Logger LOG = LoggerFactory.getLogger(AbstractRpcClient.class);

    protected final ManagedChannel channel;

    public AbstractRpcClient(String host, int port) {
        this.channel = ManagedChannelBuilder.forAddress(host, port).usePlaintext().build();
    }

    @Override
    public void close() throws IOException {
        shutdown();
    }

    public void shutdown() {
        try {
            LOG.info("Shutting down..");
            channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            LOG.warn("Interrupted");
            shutdown();
        }
    }
}
