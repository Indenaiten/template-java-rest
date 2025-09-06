package com.codenaiten.template.rest.app.old.authentication;

public interface PasswordEncoderManager {

    String hash( String password );
    boolean matches( String password, String hash );
    boolean notMatches( String password, String hash );
}
