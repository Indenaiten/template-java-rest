package com.codenaiten.template.rest.app.service;

import com.codenaiten.template.rest.app.authentication.AuthenticatedUser;
import com.codenaiten.template.rest.app.entity.Account;
import com.codenaiten.template.rest.app.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final AccountRepository accountRepository;

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| IMPLEMENTED METHODS |---------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    @Override
    public UserDetails loadUserByUsername( final String login ) throws UsernameNotFoundException {
        Optional<Account> result = this.accountRepository.findByEmail( login );
        if( result.isEmpty() ) result = this.accountRepository.findByOwner_Username( login );
        if( result.isEmpty() ){
            try {
                final UUID uuid = UUID.fromString( login );
                result = this.accountRepository.findByOwner_Id( uuid );
            }
            catch( final IllegalArgumentException e ){
                log.debug( "Login {} is not a valid UUID", login, e );
            }
        }

        final Account account = result.orElseThrow( () -> new UsernameNotFoundException( login ));
        return new AuthenticatedUser( account );
    }

// ------------------------------------------------------------------------------------------------------------------ \\

}