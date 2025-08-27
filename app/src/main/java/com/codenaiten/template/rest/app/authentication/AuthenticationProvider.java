package com.codenaiten.template.rest.app.authentication;

import com.codenaiten.template.rest.app.entity.Account;

import java.util.Locale;
import java.util.Optional;

public interface AuthenticationProvider {

    Optional<AuthenticatedUser> getAuthenticatedUser();
    Optional<Account> getAuthenticatedAccount();
    Optional<String> getAccessToken();
    Optional<Locale> getLanguage();
}
