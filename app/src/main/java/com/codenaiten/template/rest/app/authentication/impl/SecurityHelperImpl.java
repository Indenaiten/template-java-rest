package com.codenaiten.template.rest.app.authentication.impl;

import com.codenaiten.template.rest.app.authentication.SecurityHelper;
import com.codenaiten.template.rest.app.properties.SecurityProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.util.PathMatcher;

import java.util.Objects;

@Slf4j
@Component
@RequiredArgsConstructor
public class SecurityHelperImpl implements SecurityHelper {

    private final SecurityProperties securityProperties;
    private final PathMatcher pathMatcher;

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| IMPLEMENTED METHODS |---------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    @Override
    public boolean isPublicEndpoint( final String method, final String path ) {
        final HttpMethod httpMethod = HttpMethod.valueOf( method.toUpperCase() );
        return this.securityProperties.getIgnorePaths().stream().anyMatch( p -> pathMatcher.match( p, path )) ||

               ( Objects.equals( HttpMethod.GET, httpMethod ) && this.securityProperties.getIgnorePathsGet().stream()
                       .anyMatch( p -> pathMatcher.match( p, path ))) ||

               ( Objects.equals( HttpMethod.POST, httpMethod ) && this.securityProperties.getIgnorePathsPost().stream()
                       .anyMatch( p -> pathMatcher.match( p, path ))) ||

               ( Objects.equals( HttpMethod.PUT, httpMethod ) && this.securityProperties.getIgnorePathsPut().stream()
                       .anyMatch( p -> pathMatcher.match( p, path ))) ||

               ( Objects.equals( HttpMethod.PATCH, httpMethod ) && this.securityProperties.getIgnorePathsPatch().stream()
                       .anyMatch( p -> pathMatcher.match( p, path ))) ||

               ( Objects.equals( HttpMethod.DELETE, httpMethod ) && this.securityProperties.getIgnorePathsDelete().stream()
                       .anyMatch( p -> pathMatcher.match( p, path )));
    }

    @Override
    public boolean isSecuredEndpoint( final String method, final String path ) {
        return !this.isPublicEndpoint( method, path );
    }

// ------------------------------------------------------------------------------------------------------------------ \\

}
