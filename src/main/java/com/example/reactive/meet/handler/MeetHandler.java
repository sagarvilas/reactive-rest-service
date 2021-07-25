package com.example.reactive.meet.handler;

import com.example.reactive.meet.client.ServiceWebClient;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class MeetHandler {

  private final ServiceWebClient webClient;

  public MeetHandler(ServiceWebClient serviceWebClient) {
    this.webClient = serviceWebClient;
  }

  public Mono<ServerResponse> sequential(ServerRequest request) {
    ServerResponse.BodyBuilder resp = ServerResponse.ok();
    return resp.contentType(MediaType.TEXT_PLAIN)
        .body(webClient.getResultSequential(), String.class);
  }

  public Mono<ServerResponse> parallel(ServerRequest request) {
    ServerResponse.BodyBuilder resp = ServerResponse.ok();
    return resp.contentType(MediaType.TEXT_PLAIN).body(webClient.getResultParallel(), String.class);
  }

  public Mono<ServerResponse> scheduler(ServerRequest request) {
    ServerResponse.BodyBuilder resp = ServerResponse.ok();
    return resp.contentType(MediaType.TEXT_PLAIN)
        .body(webClient.getResultParallelScheduler().onErrorReturn("error"), String.class);
  }
}
