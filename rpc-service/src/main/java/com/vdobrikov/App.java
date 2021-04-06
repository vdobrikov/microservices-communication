package com.vdobrikov;

import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.Closeable;
import java.io.IOException;
import java.util.logging.Logger;

public class App implements Closeable {
    private static final Logger LOG = Logger.getLogger(App.class.getName());

    private static final String ENV_GRPC_SERVICE_ID = "GRPC_SERVICE_ID";
    private static final String DEFAULT_SERVICE_ID = "DEFAULT_ID";
    private static final int PORT = 50051;

    private Server server;

    private void start() throws IOException {
        LOG.info(String.format("Starting RPC service %s on port %s", getServiceId(), PORT));
        server = ServerBuilder.forPort(PORT).addService(new GreeterGrpcImpl(getServiceId()))
                .build().start();
        LOG.info("Listening on " + PORT);

        try {
            server.awaitTermination();
        } catch (InterruptedException e) {
            LOG.info("Shutting down");
        }
    }

    private String getServiceId() {
        String envServiceId = System.getenv(ENV_GRPC_SERVICE_ID);
        return envServiceId == null ? DEFAULT_SERVICE_ID : envServiceId;
    }

    private void shutdown() {
        if (server != null) {
            server.shutdown();
        }
    }

    public static void main(String[] args) throws IOException {
        try (App app = new App()) {
            app.start();
        }
    }

    @Override
    public void close() throws IOException {
        shutdown();
    }
}
