package com.vdobrikov.webservice.service.rpc.client;

import com.vdobrikov.rpc.GreeterGrpc;
import com.vdobrikov.rpc.HelloReply;
import com.vdobrikov.rpc.HelloRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BlockingRpcClient extends AbstractRpcClient {
    private static final Logger LOG = LoggerFactory.getLogger(BlockingRpcClient.class);

    private final GreeterGrpc.GreeterBlockingStub stub;

    public BlockingRpcClient(String host, int port) {
        super(host, port);
        this.stub = GreeterGrpc.newBlockingStub(channel);
    }

    public HelloReply greet() {
        LOG.info("Greeting..");
        HelloRequest request = HelloRequest.newBuilder().build();
        return stub.sayHello(request);
    }
}
