package com.codenaiten.template.rest.app.authentication;

public interface SecurityHelper {

    boolean isPublicEndpoint( final String method, final String path );
    boolean isSecuredEndpoint( final String method, final String path );
}
