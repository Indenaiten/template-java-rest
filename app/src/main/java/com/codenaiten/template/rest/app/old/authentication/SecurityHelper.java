package com.codenaiten.template.rest.app.old.authentication;

public interface SecurityHelper {

    boolean isPublicEndpoint( final String method, final String path );
    boolean isSecuredEndpoint( final String method, final String path );
}
