package com.codenaiten.template.rest.app.authentication.impl;


import com.codenaiten.template.rest.app.authentication.PasswordEncoderManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class PasswordEncoderManagerImpl implements PasswordEncoderManager {

    private final PasswordEncoder passwordEncoder;

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| IMPLEMENTED METHODS |---------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    @Override
    public String hash( final String password ) {
        return Optional.ofNullable( password ).map( this.passwordEncoder::encode ).orElse( null );
    }

    @Override
    public boolean check( final String password, final String hash ) {
        if( password == null ) return false;
        return Optional.ofNullable( hash )
                .map( target -> this.passwordEncoder.matches( password, target ))
                .orElse( false );
    }

// ------------------------------------------------------------------------------------------------------------------ \\

}
