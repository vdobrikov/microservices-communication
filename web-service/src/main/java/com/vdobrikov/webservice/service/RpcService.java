package com.vdobrikov.webservice.service;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.vdobrikov.rpc.HelloReply;
import com.vdobrikov.webservice.service.rpc.client.AsyncRpcClient;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import static java.util.stream.Collectors.toList;

@Service
public class RpcService {
    private static final int RPC_PORT = 50051;

    private final Set<String> rpcHosts;
    private final TaskExecutor futureExecutor;

    private final LoadingCache<String, ManagedChannel> hostToChannel = CacheBuilder.newBuilder()
            .maximumSize(5)
            .expireAfterAccess(1, TimeUnit.MINUTES)
            .build(new CacheLoader<String, ManagedChannel>() {
                @Override
                public ManagedChannel load(String host) throws Exception {
                    return ManagedChannelBuilder.forAddress(host, RPC_PORT).usePlaintext().build();
                }
            });

    public RpcService(@Value("${rpc-hosts}") Set<String> rpcHosts, TaskExecutor futureExecutor) {
        this.rpcHosts = rpcHosts;
        this.futureExecutor = futureExecutor;
    }

    public List<CompletableFuture<String>> greet() {
        return rpcHosts.stream()
                .map(host -> getReplyFrom(host))
                .map(this::fromListenableFuture)
                .collect(toList());
    }

    private ListenableFuture<HelloReply> getReplyFrom(String host) {
        try {
            AsyncRpcClient client = new AsyncRpcClient(hostToChannel.get(host));
            return client.greet();
        } catch (ExecutionException e) {
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
