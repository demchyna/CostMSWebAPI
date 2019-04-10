package com.mdem.costms.config;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.ApiKeyVehicle;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Configuration
@EnableSwagger2
@PropertySource("classpath:security.properties")
public class SwaggerConfig {

    @Value("${security.headerName}")
    private String HEADER_NAME;     // Authorization

    @Value("${urls.secureUrl}")
    private String SECURE_URL;      // /api/**

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                //.host("http://localhost:8080")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.mdem.costms.controller"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(metaData())
                .securityContexts(Lists.newArrayList(securityContext()))
                .securitySchemes(Lists.newArrayList(apiKey()));
    }

    private ApiInfo metaData() {
        return new ApiInfo(
                "CostMS Web API",
                "Web API Service for Cost Management System App.",
                "1.0",
                "",
                new Contact("Mykola Demchyna","http://demchyna.com.ua", "mykolademchyna@gmail.com"),
                "Apache License Version 2.0",
                "https://www.apache.org/licenses/LICENSE-2.0",
                new ArrayList<VendorExtension>(Collections.emptyList()));
    }

    private ApiKey apiKey() {
        return new ApiKey("token", HEADER_NAME, "header");
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                .forPaths(PathSelectors.ant(SECURE_URL))
                .build();
    }

    private List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Lists.newArrayList(new SecurityReference("token", authorizationScopes));
    }

    @Bean
    SecurityConfiguration security() {
        return new SecurityConfiguration(
                null,
                null,
                null,
                null,
                "Bearer ",
                ApiKeyVehicle.HEADER,
                HEADER_NAME,
                null);
    }
}