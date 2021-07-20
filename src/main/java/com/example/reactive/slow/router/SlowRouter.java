package com.example.reactive.slow.router;

import com.example.reactive.slow.handler.SlowHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class SlowRouter {

    @Bean
    public RouterFunction<ServerResponse> slowRoute(SlowHandler slowHandler) {

        return RouterFunctions
                .route(RequestPredicates.GET("/slow")
                        .and(RequestPredicates
                                .accept(MediaType.TEXT_PLAIN)), slowHandler::hello);
    }
}