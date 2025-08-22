package com.codenaiten.template.rest.app.authentication;

import com.codenaiten.template.rest.app.vo.account.AccountId;
import com.codenaiten.template.rest.app.vo.user.UserId;

import java.util.Optional;

public interface AuthenticationProvider {

    Optional<AuthenticatedUser> getAuthenticatedUser();
    Optional<UserId> getAuthenticatedUserId();
    Optional<AccountId> getAuthenticatedAccountId();
    Optional<String> getAccessToken();
}
