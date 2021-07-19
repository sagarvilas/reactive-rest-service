package com.example.reactive.slow.handler;

import org.junit.jupiter.api.Test;
import org.springframework.mock.web.reactive.function.server.MockServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.test.StepVerifier;

import java.time.Duration;

public class SlowHandlerTest {


    @Test
    public void test() {
        SlowHandler handler = new SlowHandler();
        StepVerifier.create(handler.hello(MockServerRequest.builder().build()))
                .expectNextMatches(actual -> actual.rawStatusCode() == 200)
                .expectComplete()
                .verifyThenAssertThat()
                .tookMoreThan(Duration.ofSeconds(4));
    }
}
