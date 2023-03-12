package br.com.trajy.service.registry.init;

import static org.springframework.boot.SpringApplication.run;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = "br.com.trajy.service.registry.config")
@EnableEurekaServer
@SpringBootApplication
public class ServiceRegistryApplication {
    public static void main(String[] args) {
        run(ServiceRegistryApplication.class, args);
    }
}