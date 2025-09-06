package com.codenaiten.template.rest.app.service;

import com.codenaiten.template.rest.app.port.AccountRepositoryPort;
import com.codenaiten.template.rest.app.port.UserRepositoryPort;
import com.codenaiten.template.rest.core.entity.Account;
import com.codenaiten.template.rest.core.service.AccountService;
import com.codenaiten.template.rest.core.vo.Email;
import com.codenaiten.template.rest.core.vo.account.AccountId;
import com.codenaiten.template.rest.core.vo.user.UserId;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Locale;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class DefaultAccountService implements AccountService{

    private final UserRepositoryPort userRepository;
    private final AccountRepositoryPort accountRepository;

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| IMPLEMENTED METHODS |---------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    @Override
    public void create( final Account account ){
        log.info( "Creating account: {}", account );

        final AccountId id = account.getId();
        if( this.accountRepository.exists( id ))
            throw new IllegalArgumentException( "Account ID already exists" );

        final UserId owner = account.getOwner();
        if( this.userRepository.exists( owner ))
            throw new IllegalArgumentException( "User not found" );

        final Email email = account.getEmail();
        if( this.accountRepository.exists( email ))
            throw new IllegalArgumentException( "Account Email already exists" );

        this.accountRepository.save( account );
    }

    @Override
    public void update( final Account account ){
        log.info( "Updating account: {}", account );

        // Get original Account
        final AccountId id = account.getId();
        final Account original = this.accountRepository.find( id )
                .orElseThrow(() -> new IllegalArgumentException( "Account not found" ));

        // Check fields that cannot be updated in this operation
        if( !Objects.equals( original.getOwner(), account.getOwner() ))
            throw new IllegalArgumentException( "Account Owner cannot be updated" );

        // Check some fields that can be updated in this operation
        //TODO: Comprobar si el nuevo lenguaje esta soportado
        final Optional<Locale> lang = account.getLang();
        if( !Objects.equals( original.getLang(), lang ))
                throw new IllegalArgumentException( "Account Language not supported" );

        final Email email = account.getEmail();
        if( original.getEmail().different( email ) && this.accountRepository.exists( email ))
                throw new IllegalArgumentException( "Account Email already exists" );

        // Check if any field has changed
        if( !Objects.equals( original.getLang(), account.getLang() ) ||
            !Objects.equals( original.getRole(), account.getRole() ) ||
            !Objects.equals( original.getEmail(), account.getEmail() ) ||
            !Objects.equals( original.getPassword(), account.getPassword() )
        ){
            original.setUpdatedAt();
            this.accountRepository.save( original );
        }
    }

    @Override
    public void delete( final Account account ){
        log.info( "Deleting account: {}", account );

        this.accountRepository.delete( account );
    }

// ------------------------------------------------------------------------------------------------------------------ \\

}
