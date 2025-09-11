package com.codenaiten.template.rest.core.feature.user.constraint;

import com.codenaiten.template.rest.core.feature.user.User;
import com.codenaiten.template.rest.core.feature.user.constraint.violation.UniquenessUserUsernameConstraintViolation;
import com.codenaiten.template.rest.core.feature.user.port.spi.UserRepository;
import com.codenaiten.template.rest.core.feature.user.vo.UserUsername;
import com.codenaiten.template.rest.core.shared.constraint.Constraint;
import com.codenaiten.template.rest.core.shared.constraint.ConstraintViolation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

@Slf4j
@Component
public class UserUsernameConstraint implements Constraint<User> {

    private final UserRepository userRepository;

//--------------------------------------------------------------------------------------------------------------------\\
//---| CONSTRUCTOR |--------------------------------------------------------------------------------------------------\\
//--------------------------------------------------------------------------------------------------------------------\\

    public UserUsernameConstraint(final UserRepository userRepository ){
        log.info( "UserUsernameConstraint initialized" );

        if( Objects.isNull( userRepository ))
            throw new IllegalArgumentException( "UserRepository is required by UserUsernameConstraint" );

        this.userRepository = userRepository;
    }

//--------------------------------------------------------------------------------------------------------------------\\
//---| IMPLEMENTED METHODS |------------------------------------------------------------------------------------------\\
//--------------------------------------------------------------------------------------------------------------------\\

    @Override
    public Optional<ConstraintViolation<?>> check( final User candidate ){
        if( Objects.isNull( candidate ))
            throw new IllegalArgumentException( "User candidate to check UserUsernameConstraint is required" );

        final UserUsername username = candidate.getUsername();
        if( this.userRepository.exists( username ))
            return Optional.of( new UniquenessUserUsernameConstraintViolation( candidate ));

        return Optional.empty();
    }

//--------------------------------------------------------------------------------------------------------------------\\

}
