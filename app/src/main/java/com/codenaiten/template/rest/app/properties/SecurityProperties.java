package com.codenaiten.template.rest.app.properties;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Slf4j
@Getter
@Component
public class SecurityProperties extends Properties {

    @Value( "${app.security.ignore-paths.all:}" )
    private String ignorePaths;

    @Value( "${app.security.ignore-paths.get:}" )
    private String ignorePathsGet;

    @Value( "${app.security.ignore-paths.post:}" )
    private String ignorePathsPost;

    @Value( "${app.security.ignore-paths.put:}" )
    private String ignorePathsPut;

    @Value( "${app.security.ignore-paths.patch:}" )
    private String ignorePathsPatch;

    @Value( "${app.security.ignore-paths.delete:}" )
    private String ignorePathsDelete;

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

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| GETTERS |---------------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    public List<String> getIgnorePaths() {
        return this.convertToList( this.ignorePaths );
    }

    public List<String> getIgnorePathsGet() {
        return this.convertToList( this.ignorePathsGet );
    }

    public List<String> getIgnorePathsPost() {
        return this.convertToList( this.ignorePathsPost );
    }

    public List<String> getIgnorePathsPut() {
        return this.convertToList( this.ignorePathsPut );
    }

    public List<String> getIgnorePathsPatch() {
        return this.convertToList( this.ignorePathsPatch );
    }

    public List<String> getIgnorePathsDelete() {
        return this.convertToList( this.ignorePathsDelete );
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
