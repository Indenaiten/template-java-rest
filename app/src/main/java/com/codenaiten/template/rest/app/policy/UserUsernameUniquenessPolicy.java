package com.codenaiten.template.rest.app.policy;

import com.codenaiten.template.rest.app.exception.UserUsernameAlreadyException;
import com.codenaiten.template.rest.app.repository.UserRepository;
import com.codenaiten.template.rest.app.spec.UserUsernameUniquenessSpec;
import com.codenaiten.template.rest.app.vo.user.UserUsername;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class UserUsernameUniquenessPolicy {

    private final UserRepository userRepository;

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| METHODS |---------------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    public void check( final UserUsername username ){
        if( new UserUsernameUniquenessSpec( this.userRepository ).not().test( username )){
            throw new UserUsernameAlreadyException( username );
        }
    }

// ------------------------------------------------------------------------------------------------------------------ \\

}
