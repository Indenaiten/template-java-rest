package com.codenaiten.template.rest.boot.config;

import com.codenaiten.template.rest.app.authentication.SecurityHelper;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;
import java.util.List;

@Slf4j
@Configuration
@RequiredArgsConstructor
@SecurityScheme( name = "bearerAuth", type = SecuritySchemeType.HTTP, scheme = "bearer", bearerFormat = "JWT" )
@OpenAPIDefinition( security = @SecurityRequirement( name = "bearerAuth" ))
public class OpenApiConfig {

    private final SecurityHelper securityHelper;

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| BEANS |------------------------------------------------------------------------------------------------------ \\
// ------------------------------------------------------------------------------------------------------------------ \\

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info( new Info().title( "API Rest Template" ).version( "v1" ))
                .servers( List.of(
                    new Server().url( "http://localhost:8080" ).description( "Servidor Local" ),
                    new Server().url( "https://test.indenaiten.com" ).description( "Servidor de Pruebas" )
                ))
                .tags( List.of(
                        new Tag().name( "Configuraciones" ).description( "Operaciones relacionadas con la configuración de la API" ),
                        new Tag().name( "Autenticación" ).description( "Operaciones relacionadas con la autenticación" ),
                        new Tag().name( "Cuentas" ).description( "Operaciones relacionadas con las cuentas de usuario" ),
                        new Tag().name( "Usuarios" ).description( "Operaciones relacionadas con la información de usuarios" )
                ));
    }

    @Bean
    public GroupedOpenApi publicGroup() {
        return GroupedOpenApi.builder().group( "public" ).pathsToMatch( "/**" )
                .addOpenApiCustomizer(openApi -> {
                    if( openApi.getPaths() == null ) return;
                    openApi.getPaths().forEach(( path, item ) ->
                        item.readOperationsMap().forEach(( httpMethod, operation ) -> {
                            if( this.securityHelper.isPublicEndpoint( httpMethod.name(), path ))
                                operation.setSecurity( Collections.emptyList() );
                        })
                    );
                })
                .build();
    }
    
// ------------------------------------------------------------------------------------------------------------------ \\

}
