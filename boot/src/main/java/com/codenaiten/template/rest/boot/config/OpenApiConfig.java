package com.codenaiten.template.rest.boot.config;

import com.codenaiten.template.rest.app.authentication.SecurityUtils;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;

@Configuration
@RequiredArgsConstructor
@SecurityScheme( name = "bearerAuth", type = SecuritySchemeType.HTTP, scheme = "bearer", bearerFormat = "JWT" )
@OpenAPIDefinition( security = @SecurityRequirement( name = "bearerAuth" ))
public class OpenApiConfig {

    private final SecurityUtils securityUtils;

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| PUBLIC GROUP |----------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    @Bean
    public GroupedOpenApi publicGroup() {
        return GroupedOpenApi.builder().group( "public" ).pathsToMatch( "/**" )
                .addOpenApiCustomizer(openApi -> {
                    if( openApi.getPaths() == null ) return;
                    openApi.getPaths().forEach(( path, item ) ->
                        item.readOperationsMap().forEach(( httpMethod, operation ) -> {
                            if( this.securityUtils.isPublicEndpoint( httpMethod.name(), path ))
                                operation.setSecurity( Collections.emptyList() );
                        })
                    );
                })
                .build();
    }
    
// ------------------------------------------------------------------------------------------------------------------ \\

}
