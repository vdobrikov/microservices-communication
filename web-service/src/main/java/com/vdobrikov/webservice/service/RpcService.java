package com.vdobrikov.webservice.service;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.vdobrikov.rpc.HelloReply;
import com.vdobrikov.webservice.service.rpc.client.AsyncRpcClient;
import com.vdobrikov.webservice.service.rpc.client.BlockingRpcClient;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

import static java.util.stream.Collectors.toList;

@Service
public class RpcService {
    private static final int RPC_PORT = 50051;

    private final Set<String> rpcHosts;
    private final TaskExecutor futureExecutor;

    public RpcService(@Value("${rpc-hosts}") Set<String> rpcHosts, TaskExecutor futureExecutor) {
        this.rpcHosts = rpcHosts;
        this.futureExecutor = futureExecutor;
    }

    public List<String> greetBlocking() {
        return rpcHosts.stream()
                .map(host -> getBlockingReplyFrom(host))
                .map(HelloReply::getMessage)
                .collect(toList());
    }

    public List<CompletableFuture<String>> greetAsync() {
        return rpcHosts.stream()
                .map(host -> getAsyncReplyFrom(host))
                .map(this::fromListenableFuture)
                .collect(toList());
    }

    private HelloReply getBlockingReplyFrom(String host) {
        try (BlockingRpcClient client = new BlockingRpcClient(host, RPC_PORT)) {
            return client.greet();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private ListenableFuture<HelloReply> getAsyncReplyFrom(String host) {
        try (AsyncRpcClient client = new AsyncRpcClient(host, RPC_PORT)) {
            return client.greet();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private CompletableFuture<String> fromListenableFuture(ListenableFuture<HelloReply> listenableFuture) {
        CompletableFuture<String> completableFuture = new CompletableFuture<>();
        Futures.addCallback(listenableFuture, new FutureCallback<HelloReply>() {
            @Override
            public void onSuccess(@NullableDecl HelloReply helloReply) {
                completableFuture.complete(helloReply == null ? null : helloReply.getMessage());
            }

            @Override
            public void onFailure(Throwable throwable) {
                completableFuture.completeExceptionally(throwable);
            }
        }, futureExecutor);
        return completableFuture;
    }
}
