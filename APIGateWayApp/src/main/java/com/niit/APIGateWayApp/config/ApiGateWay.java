package com.niit.APIGateWayApp.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.CrossOrigin;

@Configuration
public class ApiGateWay {

    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder){
//        return builder.routes().route(predicateSpec -> predicateSpec.path("/v1/**")
//                .uri("http://localhost:8084/*")).route(predicateSpec -> predicateSpec
//                .path("/v2/**").uri("http://localhost:8085/*")).build();

        return builder.routes().route(predicateSpec -> predicateSpec.path("/v1/**")
                .uri("lb://AuthenticationApp")).route(predicateSpec -> predicateSpec
                .path("/v2/**").uri("lb://TodoListApp")).build();
    }
}
