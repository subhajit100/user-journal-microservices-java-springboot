package com.subhajit.microservices.api_gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiGatewayConfiguration {

    @Bean
    public RouteLocator gatewayRouter(RouteLocatorBuilder builder){
        return builder.routes()
                .route(p-> p.path("/api/auth/**")
                        .uri("lb://user-service"))
                .route(p-> p.path("/api/users/**")
                        .uri("lb://user-service"))  // using lb means querying the naming server with this microservice name, and load balancing among the active instances returned
                .route(p-> p.path("/api/journals/**")
                        .uri("lb://journal-service"))
                .build();
    }
}
