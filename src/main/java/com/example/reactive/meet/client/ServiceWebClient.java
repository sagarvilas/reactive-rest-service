package com.example.reactive.meet.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ServiceWebClient {

  private final WebClient client;

  public ServiceWebClient(WebClient localClient) {
    this.client = localClient;
  }

  public Mono<String> getResultSequential() {
    Mono<String> r1 = getSlowResponse();
    Mono<String> r2 = getFastResponse();
    return r2.concatWith(r1).collectList().map(x -> construct(x)).single();
  }

  private String construct(List<String> x) {
    return x.stream().collect(Collectors.joining("\n"));
  }

  public Mono<String> getResultParallel() {
    return Flux.just(getFastResponse(), getSlowResponse())
        .flatMap(Function.identity())
        .collect(Collectors.joining("\n"));
  }

  public Mono<String> getResultParallelScheduler() {
    return Flux.just(getFastResponse(), getSlowResponse())
        .subscribeOn(Schedulers.boundedElastic())
        .parallel(2)
        .runOn(Schedulers.parallel())
        .flatMap(Function.identity())
        .sequential()
        .publishOn(Schedulers.single())
        .collect(Collectors.joining("\n"));
  }

  private Mono<String> getSlowResponse() {
    return client
        .get()
        .uri("/slow")
        .accept(MediaType.TEXT_PLAIN)
        .exchangeToMono(x -> x.bodyToMono(String.class));
  }

  private Mono<String> getFastResponse() {
    return client
        .get()
        .uri("/fast")
        .accept(MediaType.TEXT_PLAIN)
        .exchangeToMono(x -> x.bodyToMono(String.class));
  }
}
