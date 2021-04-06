package com.vdobrikov.webservice.web.rest;

import com.vdobrikov.webservice.service.RpcService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class HelloController {

    private final RpcService rpcService;

    public HelloController(RpcService rpcService) {
        this.rpcService = rpcService;
    }

    @Async
    @GetMapping("/hello")
    public CompletableFuture<List<String>> helloAsync(@RequestParam(name = "name", required = false) String name) {
        List<CompletableFuture<String>> futures = rpcService.greet(name);
        return CompletableFuture.allOf(futures.toArray(new CompletableFuture<?>[futures.size()]))
            .thenApply(ignore -> futures.stream()
                .map(CompletableFuture::join)
                .collect(Collectors.toList()));
    }
}
