package com.example.reactive.fast.router;

import com.example.reactive.fast.handler.FastHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class FastRouter {

    @Bean
    public RouterFunction<ServerResponse> fastRoute(FastHandler fastHandler) {

        return RouterFunctions
                .route(RequestPredicates.GET("/fast")
                        .and(RequestPredicates
                                .accept(MediaType.TEXT_PLAIN)), fastHandler::hello);
    }
}