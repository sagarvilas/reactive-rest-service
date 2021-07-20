package com.example.reactive.fast.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.Instant;

@Component
@Slf4j
public class FastHandler {

    public Mono<ServerResponse> hello(ServerRequest request) {
        return ServerResponse.ok().contentType(MediaType.TEXT_PLAIN)
                .body(BodyInserters.fromValue("I reached early..."))
                .delayElement(Duration.ofSeconds(2l))
                .doOnSubscribe(x -> log.info("Fast doOnSubscribe called at :" + Instant.now()))
                .doOnNext(x -> log.info("Fast onNext called at :" + Instant.now()));
    }
}