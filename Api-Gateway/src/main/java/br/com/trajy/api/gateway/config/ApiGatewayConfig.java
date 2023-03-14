package br.com.trajy.api.gateway.config;

import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.Buildable;
import org.springframework.cloud.gateway.route.builder.PredicateSpec;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.function.Function;

@Configuration
public class ApiGatewayConfig {

    @Bean
    public RouteLocator gatewayRouter(RouteLocatorBuilder builder) {
        Function<PredicateSpec, Buildable<Route>> function = predicateSpec -> {
            return predicateSpec.path("/get")
                    .filters(gatewayFilterSpec -> gatewayFilterSpec
                            .addRequestHeader("Hello-header", "Hello-world")
                            .addRequestParameter("Hello-parameter", "Hello-world")
                    )
                    .uri("http://httpbin.org:80");
        };
        return builder.routes()
                .route(function)
                .build();
    }

}
