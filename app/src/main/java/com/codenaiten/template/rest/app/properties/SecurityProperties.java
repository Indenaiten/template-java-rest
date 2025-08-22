package com.codenaiten.template.rest.app.properties;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Getter
@Component
public class SecurityProperties extends Properties {

    @Value( "${app.security.ignore-paths:}" )
    private String ignorePaths;

    @Value( "${app.security.cors.allowed-origins:}" )
    private String corsAllowedOrigins;

    @Value( "${app.security.cors.allowed-methods:}" )
    private String corsAllowedMethods;

    @Value( "${app.security.cors.allowed-headers:}" )
    private String corsAllowedHeaders;

    @Value( "${app.security.cors.exposed-headers:}" )
    private String corsExposedHeaders;

    @Value( "${app.security.cors.allow-credentials:}" )
    private Boolean corsAllowCredentials;

    @Value( "${app.security.token.access-token.secret}" )
    private String accessTokenSecret;

    @Value( "${app.security.token.access-token.expiration:180000}" )
    private Long accessTokenExpiration;

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| GETTERS |---------------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    public List<String> getIgnorePaths() {
        return this.convertToList( this.ignorePaths );
    }

    public List<String> getCorsAllowedOrigins() {
        return this.convertToList( this.corsAllowedOrigins );
    }

    public List<String> getCorsAllowedMethods() {
        return this.convertToList( this.corsAllowedMethods );
    }

    public List<String> getCorsAllowedHeaders() {
        return this.convertToList( this.corsAllowedHeaders );
    }

    public List<String> getCorsExposedHeaders() {
        return this.convertToList( this.corsExposedHeaders );
    }

    public Optional<Boolean> getCorsAllowCredentials() {
        return this.getOptional( this.corsAllowCredentials );
    }

// ------------------------------------------------------------------------------------------------------------------ \\

}
