package br.com.trajy.api.gateway.config;

import static org.apache.commons.lang3.StringUtils.contains;
import static org.apache.commons.lang3.StringUtils.lowerCase;
import static org.apache.commons.lang3.StringUtils.remove;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.AbstractRequestService;
import org.springdoc.core.GenericResponseService;
import org.springdoc.core.GroupedOpenApi;
import org.springdoc.core.OpenAPIService;
import org.springdoc.core.OperationService;
import org.springdoc.core.SpringDocConfigProperties;
import org.springdoc.core.SpringDocProviders;
import org.springdoc.core.SwaggerUiConfigProperties;
import org.springdoc.core.customizers.SpringDocCustomizers;
import org.springdoc.webflux.api.MultipleOpenApiWebFluxResource;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.cloud.gateway.route.RouteDefinitionLocator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@OpenAPIDefinition
@Configuration
public class OpenApiCentralizedDocumentationConfig {

    private static final String ROUTE_DEFINITION_ID_PREFIX = "ReactiveCompositeDiscoveryClient_";

    @Bean
    public List<GroupedOpenApi> getApiDocumentations(SwaggerUiConfigProperties swaggerUiConfigProperties, RouteDefinitionLocator routeDefinitionLocator) {
        log.info("Open Api Documentation Auto Configuration");
        List<GroupedOpenApi> groupedApiDocs = new ArrayList<>();
        routeDefinitionLocator.getRouteDefinitions().collectList().block().stream()
                .filter(route -> contains(route.getId(), ROUTE_DEFINITION_ID_PREFIX))
                .forEach(route -> {
                    final String apiName = lowerCase(remove(route.getId(), ROUTE_DEFINITION_ID_PREFIX));
                    groupedApiDocs.add(GroupedOpenApi.builder().pathsToMatch("/".concat(apiName).concat("/**"))
                            .group(apiName).build()
                    );
                    log.info("{} Documentation Configured", apiName);
                });
        return groupedApiDocs;
    }

    @Bean
    MultipleOpenApiWebFluxResource multipleOpenApiResource(List<GroupedOpenApi> groupedOpenApis,
                                                           ObjectFactory<OpenAPIService> defaultOpenAPIBuilder, AbstractRequestService requestBuilder,
                                                           GenericResponseService responseBuilder, OperationService operationParser,
                                                           SpringDocConfigProperties springDocConfigProperties,
                                                           SpringDocProviders springDocProviders, SpringDocCustomizers springDocCustomizers) {
        log.info("MultipleOpenApiWebFluxResource Bean Generated");
        return new MultipleOpenApiWebFluxResource(groupedOpenApis,
                defaultOpenAPIBuilder, requestBuilder,
                responseBuilder, operationParser,
                springDocConfigProperties,
                springDocProviders, springDocCustomizers);
    }
}
