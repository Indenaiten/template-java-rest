package com.codenaiten.template.rest.core.feature.user.constraint;

import com.codenaiten.template.rest.core.feature.user.User;
import com.codenaiten.template.rest.core.feature.user.constraint.violation.UniquenessUserUsernameConstraintViolation;
import com.codenaiten.template.rest.core.feature.user.port.UserRepository;
import com.codenaiten.template.rest.core.shared.constraint.Constraint;
import com.codenaiten.template.rest.core.shared.constraint.ConstraintViolation;
import com.codenaiten.template.rest.core.shared.vo.Email;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
public class UserEmailConstraint implements Constraint<User> {

    private final UserRepository userRepository;

//--------------------------------------------------------------------------------------------------------------------\\
//---| IMPLEMENTED METHODS |------------------------------------------------------------------------------------------\\
//--------------------------------------------------------------------------------------------------------------------\\

    @Override
    public Optional<ConstraintViolation<?>> check( final User candidate ) {
        if( Objects.isNull( candidate ))
            throw new IllegalArgumentException( "User candidate to check UserEmailConstraint is required" );

        final Email email = candidate.getEmail();
        if( !this.userRepository.exists( email ))
            return Optional.of( new UniquenessUserUsernameConstraintViolation( candidate ));

        return Optional.empty();
    }

//--------------------------------------------------------------------------------------------------------------------\\

}
