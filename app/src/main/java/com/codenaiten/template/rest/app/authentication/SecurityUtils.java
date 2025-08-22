package com.codenaiten.template.rest.app.authentication;

public interface SecurityUtils {

    boolean isPublicEndpoint( final String method, final String path );
    boolean isSecuredEndpoint( final String method, final String path );
}
