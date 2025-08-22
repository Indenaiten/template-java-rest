package com.codenaiten.template.rest.app.authentication;

import com.codenaiten.template.rest.app.dto.result.AccountInfoResult;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Getter
public class AuthenticatedUser implements UserDetails {

    private final AccountInfoResult info;
    private final String username;
    private final String password;

    public AuthenticatedUser( final AccountInfoResult info, final String password ){
        this.info = info;
        this.username = info.owner().username();
        this.password = password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

}
