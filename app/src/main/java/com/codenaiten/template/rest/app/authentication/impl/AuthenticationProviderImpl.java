package com.codenaiten.template.rest.app.authentication.impl;

import com.codenaiten.template.rest.app.authentication.AuthenticatedUser;
import com.codenaiten.template.rest.app.authentication.AuthenticationProvider;
import com.codenaiten.template.rest.app.dto.result.AccountInfoResult;
import com.codenaiten.template.rest.app.dto.result.UserInfoResult;
import com.codenaiten.template.rest.app.vo.account.AccountId;
import com.codenaiten.template.rest.app.vo.user.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

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
    public Optional<UserId> getAuthenticatedUserId(){
        return this.getAuthenticatedUser()
                .map( AuthenticatedUser::getInfo )
                .map( AccountInfoResult::owner )
                .map( UserInfoResult::id )
                .map( UserId::new );
    }

    @Override
    public Optional<AccountId> getAuthenticatedAccountId(){
        return this.getAuthenticatedUser()
                .map( AuthenticatedUser::getInfo )
                .map( AccountInfoResult::id )
                .map( AccountId::new );
    }

// ------------------------------------------------------------------------------------------------------------------ \\

}
