package com.codenaiten.template.rest.app.policy;

import com.codenaiten.template.rest.app.entity.User;
import com.codenaiten.template.rest.app.exception.UserUsernameAlreadyException;
import com.codenaiten.template.rest.app.repository.UserRepository;
import com.codenaiten.template.rest.app.spec.UserUsernameUniquenessSpec;
import com.codenaiten.template.rest.app.vo.user.UserUsername;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Policy que permite validar la unicidad de un {@link UserUsername} de un {@link User}.
 */
@Slf4j
@RequiredArgsConstructor
public class UserUsernameUniquenessPolicy {

    /** Repository relacionado con las entidades de tipo {@link User} */
    private final UserRepository userRepository;

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| METHODS |---------------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    /**
     * Comprueba si el {@link UserUsername} de un {@link User} proporcionado no está registrado todavía.
     *
     * @param username {@link UserUsername} de un {@link User} que se va a evaluar.
     *
     * @throws UserUsernameAlreadyException si el {@link UserUsername} ya está registrado por otro {@link User}.
     *
     * @see UserUsernameUniquenessSpec
     */
    public void check( final UserUsername username ){
        if( new UserUsernameUniquenessSpec( this.userRepository ).not().test( username )){
            throw new UserUsernameAlreadyException( username );
        }
    }

// ------------------------------------------------------------------------------------------------------------------ \\

}
