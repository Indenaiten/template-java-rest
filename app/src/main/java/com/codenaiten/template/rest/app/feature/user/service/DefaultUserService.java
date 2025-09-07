package com.codenaiten.template.rest.app.feature.user.service;

import com.codenaiten.template.rest.app.port.UserRepositoryPort;
import com.codenaiten.template.rest.core.feature.user.User;
import com.codenaiten.template.rest.core.feature.user.policy.UserMinimumAgePolicy;
import com.codenaiten.template.rest.core.feature.user.service.UserService;
import com.codenaiten.template.rest.core.feature.user.vo.UserId;
import com.codenaiten.template.rest.core.feature.user.vo.UserUsername;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class DefaultUserService implements UserService{

    private final UserRepositoryPort userRepository;
    private final UserMinimumAgePolicy userMinimumAgePolicy;

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| IMPLEMENTED METHODS |---------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    @Override
    public void create( final User user ){
        log.info( "Creating user: {}", user );

        // Check if user exists
        final UserId id = user.getId();
        if( this.userRepository.exists( id ))
            throw new IllegalArgumentException( "User ID already exists" );

        // Check fields that can be updated in this operation
        final UserUsername username = user.getUsername();
        if( this.userRepository.exists( username ))
            throw new IllegalArgumentException( "User Username already exists" );

        final LocalDate birthdate = user.getBirthdate();
        if( !this.userMinimumAgePolicy.check( birthdate ))
            throw new IllegalArgumentException( "User must be 18 years old or older" );

        this.userRepository.save( user );
    }

    @Override
    public void update( final User user ){
        log.info( "Updating user: {}", user );

        // Get original User
        final UserId id = user.getId();
        final User original = this.userRepository.find( id )
                .orElseThrow(() -> new IllegalArgumentException( "User not found" ));

        // Check some fields that can be updated in this operation
        final Optional<UUID> image = user.getImage();
        if( !Objects.equals( image, original.getImage() )){
            //TODO: Comprobar si existe la imagen
        }

        final UserUsername username = user.getUsername();
        if( original.getUsername().different( username ) && this.userRepository.exists( username ))
                throw new IllegalArgumentException( "User Username already exists" );

        final LocalDate birthdate = user.getBirthdate();
        if( !Objects.equals( birthdate, original.getBirthdate() ) && !this.userMinimumAgePolicy.check( birthdate ))
                throw new IllegalArgumentException( "User minimum age not reached" );

        // Check if any field has changed
        if( !Objects.equals( original.getImage(), user.getImage() ) ||
            !Objects.equals( original.getUsername(), user.getUsername() ) ||
            !Objects.equals( original.getName(), user.getName() ) ||
            !Objects.equals( original.getSurname(), user.getSurname() ) ||
            !Objects.equals( original.getBirthdate(), user.getBirthdate() )
        ){
            original.setUpdatedAt();
            this.userRepository.save( user );
        }
    }

    @Override
    public void delete( final User user ){
        log.info( "Deleting user: {}", user );

        this.userRepository.delete( user );
    }

// ------------------------------------------------------------------------------------------------------------------ \\

}
