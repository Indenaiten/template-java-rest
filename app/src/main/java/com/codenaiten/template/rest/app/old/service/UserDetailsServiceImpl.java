package com.codenaiten.template.rest.app.old.service;

import com.codenaiten.template.rest.app.old.authentication.AuthenticatedUser;
import com.codenaiten.template.rest.app.old.entity.Account;
import com.codenaiten.template.rest.app.old.repository.AccountRepository;
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

    /** Repository relacionado con las entidades de tipo {@link Account} */
    private final AccountRepository accountRepository;

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| IMPLEMENTED METHODS |---------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    @Override
    public UserDetails loadUserByUsername( final String login ) throws UsernameNotFoundException{
        // Step 01: Get account by Email
        Optional<Account> result = this.accountRepository.findByEmail( login );

        // Step 02: If not found, try to get account by Username
        if( result.isEmpty() ) result = this.accountRepository.findByOwner_Username( login );

        // Step 03: If not found, try to get account by ID
        if( result.isEmpty() ){
            try {
                // Convert login to UUID and try to get account by ID
                final UUID uuid = UUID.fromString( login );
                result = this.accountRepository.findByOwner_Id( uuid );
            }
            catch( final IllegalArgumentException e ){
                log.debug( "Login {} is not a valid UUID", login, e );
            }
        }

        // Step 04: If found get account, otherwise throw exception
        final Account account = result.orElseThrow( () -> new UsernameNotFoundException( login ));

        // Step 05: Create AuthenticatedUser and return
        return new AuthenticatedUser( account );
    }

// ------------------------------------------------------------------------------------------------------------------ \\

}