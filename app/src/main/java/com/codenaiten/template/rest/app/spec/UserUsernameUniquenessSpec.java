package com.codenaiten.template.rest.app.spec;

import com.codenaiten.template.rest.app.repository.UserRepository;
import com.codenaiten.template.rest.app.vo.user.UserUsername;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
public class UserUsernameUniquenessSpec implements Specification<UserUsername>{

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
