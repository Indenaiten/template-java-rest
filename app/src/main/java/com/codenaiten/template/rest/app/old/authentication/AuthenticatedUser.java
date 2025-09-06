package com.codenaiten.template.rest.app.old.authentication;

import com.codenaiten.template.rest.app.old.entity.Account;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Getter
@Setter
public class AuthenticatedUser implements UserDetails {

    private final Account account;
    private final String username;
    private final String password;
    private final AccountRole role;
    private String token;

    public AuthenticatedUser( final Account account ){
        this.account = account;
        this.username = account.getOwner().getUsername();
        this.password = account.getPassword();
        this.role = new AccountRole( account.getRole() );
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of( new SimpleGrantedAuthority( this.role.getName() ));
    }

    public Optional<String> getToken(){
        return Optional.ofNullable( this.token );
    }

}
