package br.com.trajy.api.gateway.init;

import static org.springframework.boot.SpringApplication.run;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = {
        "br.com.trajy.api.gateway.config"
})
@SpringBootApplication
public class ApiGatewayApplication {
    public static void main(String[] args) {
        run(ApiGatewayApplication.class, args);
    }
}