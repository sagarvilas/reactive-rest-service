package com.example.reactive.slow.handler;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class SlowHandler {

  public Mono<ServerResponse> hello(ServerRequest request) {
    try {
      Thread.sleep(4000L);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    return ServerResponse.ok().contentType(MediaType.TEXT_PLAIN)
      .body(BodyInserters.fromValue("Sorry I am late..."));
  }
}