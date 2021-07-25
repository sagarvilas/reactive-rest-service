package com.example.reactive.meet.router;

import com.example.reactive.meet.handler.MeetHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class MeetRouter {

  @Bean
  public RouterFunction<ServerResponse> meetRoute(MeetHandler meetHandler) {

    return RouterFunctions.route(
            RequestPredicates.GET("/meetSequential")
                .and(RequestPredicates.accept(MediaType.TEXT_PLAIN)),
            meetHandler::sequential)
        .andRoute(
            RequestPredicates.GET("/meetParallel")
                .and(RequestPredicates.accept(MediaType.TEXT_PLAIN)),
            meetHandler::parallel)
        .andRoute(
            RequestPredicates.GET("/meetScheduler")
                .and(RequestPredicates.accept(MediaType.TEXT_PLAIN)),
            meetHandler::scheduler);
  }
}
