package com.codenaiten.template.rest.app.old.spec;

import com.codenaiten.template.rest.app.old.entity.User;
import com.codenaiten.template.rest.app.old.repository.UserRepository;
import com.codenaiten.template.rest.app.old.vo.user.UserUsername;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

/**
 * Specification que permite comprobar la unicidad de un {@link UserUsername} de un {@link User}.
 *
 * @see Specification
 * @see UserUsername
 */
@Slf4j
@RequiredArgsConstructor
public class UserUsernameUniquenessSpec implements Specification<UserUsername>{

    /** Repository relacionado con las entidades de tipo {@link User} */
    private final UserRepository userRepository;

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| IMPLEMENTED METHODS |---------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    @Override
    public boolean test( final UserUsername candidate ){
        if( Objects.isNull( candidate )) return false;
        return !this.userRepository.existsByUsername( candidate.value() );
    }

// ------------------------------------------------------------------------------------------------------------------ \\

}
