package com.example.reactive.meet.client;

import com.example.reactive.ReactiveSlowApplication;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.test.StepVerifier;

import java.time.Duration;

@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    classes = ReactiveSlowApplication.class)
public class ServiceWebClientTest {

  @LocalServerPort private int port;

  private ServiceWebClient client;

  @BeforeEach
  private void init() {
    WebClient client2 = WebClient.create("http://localhost:" + port);
    client = new ServiceWebClient(client2);
  }

  @Test
  public void parallelTest() {
    StepVerifier.create(client.getResultParallel())
        .expectNextMatches(actual -> actual.equals("I reached early...\n" + "Sorry I am late..."))
        .expectComplete()
        .verifyThenAssertThat()
        .tookLessThan(Duration.ofSeconds(5));
  }

  @Test
  public void parallelSchedulerTest() {
    StepVerifier.create(client.getResultParallelScheduler())
        .expectNextMatches(actual -> actual.equals("I reached early...\n" + "Sorry I am late..."))
        .expectComplete()
        .verifyThenAssertThat()
        .tookLessThan(Duration.ofSeconds(5));
  }

  @Test
  public void sequentialTest() {
    StepVerifier.create(client.getResultSequential())
        .expectNextMatches(actual -> actual.equals("I reached early...\n" + "Sorry I am late..."))
        .expectComplete()
        .verifyThenAssertThat()
        .tookMoreThan(Duration.ofSeconds(6));
  }
}
