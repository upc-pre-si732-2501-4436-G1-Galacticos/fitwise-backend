package org.upc.fitwise.shared.infrastructure.documentation.openapi.configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfiguration {
    @Bean
    public OpenAPI FitwisePlatformOpenApi() {
        // General configuration
        //URL SWAGGER  http://localhost:8080/swagger-ui/index.html
        var openApi = new OpenAPI();
        openApi.info(new Info()
                .title("Fitwise API")
                .description("Fitwise application REST API documentation.")
                .version("v1.0.0")
                .license(new License().name("Apache 2.0")
                        .url("https://springdoc.org")))
                .externalDocs(new ExternalDocumentation()
                        .description("Fitwise wiki Documentation")
                        .url("https://Fitwise.github.io/docs"));
        // Add security scheme

        final String securitySchemeName = "bearerAuth";

        openApi.addSecurityItem(new SecurityRequirement()
                        .addList(securitySchemeName))
                .components(new Components()
                        .addSecuritySchemes(securitySchemeName,
                                new SecurityScheme()
                                        .name(securitySchemeName)
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")));
        return openApi;
    }
}
