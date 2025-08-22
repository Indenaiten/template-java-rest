package com.codenaiten.template.rest.app.authentication;

public interface PasswordEncoderManager {

    String hash( String password );
    boolean check( String password, String hash );
}
