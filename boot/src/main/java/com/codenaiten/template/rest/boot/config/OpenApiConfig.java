package com.codenaiten.template.rest.boot.config;

import com.codenaiten.template.rest.app.properties.SecurityProperties;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.AntPathMatcher;

import java.util.List;

@Configuration
@RequiredArgsConstructor
@SecurityScheme( name = "bearerAuth", type = SecuritySchemeType.HTTP, scheme = "bearer", bearerFormat = "JWT" )
@OpenAPIDefinition( security = @SecurityRequirement( name = "bearerAuth" ))
public class OpenApiConfig {

    private final SecurityProperties securityProperties;

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| PUBLIC GROUP |----------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    @Bean
    public GroupedOpenApi publicGroup() {
        final var matcher = new AntPathMatcher();

        return GroupedOpenApi.builder().group( "public" ).pathsToMatch( "/**" )
                .addOpenApiCustomizer(openApi -> {
                    if( openApi.getPaths() == null ) return;

                    final List<String> publicPaths = this.securityProperties.getIgnorePaths();
                    if( publicPaths == null || publicPaths.isEmpty() ) return;

                    openApi.getPaths().forEach(( path, item ) -> {
                        boolean isPublic = publicPaths.stream()
                                .map( String::trim )
                                .filter( s -> !s.isEmpty() )
                                .anyMatch( p -> matcher.match( p, path ));
                        if( isPublic ) item.readOperations().forEach( op -> op.setSecurity( List.of() ));
                    });
                })
                .build();
    }

// ------------------------------------------------------------------------------------------------------------------ \\

}
