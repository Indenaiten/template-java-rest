package com.codenaiten.template.rest.app.authentication.impl;

import com.codenaiten.template.rest.app.authentication.AuthenticatedUser;
import com.codenaiten.template.rest.app.authentication.AuthenticationProvider;
import com.codenaiten.template.rest.app.entity.Account;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationProviderImpl implements AuthenticationProvider {

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| IMPLEMENTED METHODS |---------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    @Override
    public Optional<AuthenticatedUser> getAuthenticatedUser() {
        return Optional.ofNullable( SecurityContextHolder.getContext())
                .map( SecurityContext::getAuthentication )
                .map( Authentication::getPrincipal )
                .filter( AuthenticatedUser.class::isInstance )
                .map( AuthenticatedUser.class::cast );
    }

    @Override
    public Optional<Account> getAuthenticatedAccount(){
        return this.getAuthenticatedUser().map( AuthenticatedUser::getAccount );
    }

    @Override
    public Optional<String> getAccessToken(){
        return this.getAuthenticatedUser().flatMap( AuthenticatedUser::getToken );
    }

    @Override
    public Optional<Locale> getLanguage(){
        return this.getAuthenticatedAccount().flatMap( Account::getLang ).map( Locale::forLanguageTag );
    }

// ------------------------------------------------------------------------------------------------------------------ \\

}
