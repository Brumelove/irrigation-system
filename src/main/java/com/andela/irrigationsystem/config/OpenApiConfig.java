package com.andela.irrigationsystem.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.License;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration for OpenAPI library
 *
 * @author brume
 */
@Configuration
public class OpenApiConfig {
    /**
     * Helper method to populate fields like project title, version and description on Swagger UI
     *
     * @param appName        the application name
     * @param appDescription the application description
     * @param appVersion     the application version
     * @return the {@link OpenAPI} object for configuring the properties on Swagger UI
     */
    @Bean
    public OpenAPI openAPI(@Value("${app.name}") String appName, @Value("${app.description}") String appDescription,
                           @Value("${app.version}") String appVersion) {
        return new OpenAPI()
                .info(new io.swagger.v3.oas.models.info.Info()
                        .title(appName)
                        .version(appVersion)
                        .description(appDescription)
                        .termsOfService("http://swagger.io/terms/")
                        .license(new License().name("Apache 2.0").url("http://springdoc.org")));

    }
}
