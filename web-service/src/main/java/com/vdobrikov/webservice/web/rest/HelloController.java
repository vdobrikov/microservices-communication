package com.vdobrikov.webservice.web.rest;

import com.vdobrikov.webservice.service.RpcService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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

    @GetMapping("/hello-blocking")
    public List<String> helloBlocking() {
        return rpcService.greetBlocking();
    }

    @GetMapping("/hello-async")
    public List<String> helloAsync() {
        return rpcService.greetAsync().stream()
                .map(CompletableFuture::join)
                .collect(Collectors.toList());
    }
}
