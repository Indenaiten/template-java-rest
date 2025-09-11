package com.codenaiten.template.rest.core.feature.user.constraint;

import com.codenaiten.template.rest.core.feature.user.User;
import com.codenaiten.template.rest.core.feature.user.constraint.violation.AlreadyExistsUserIdConstraintViolation;
import com.codenaiten.template.rest.core.feature.user.port.spi.UserRepository;
import com.codenaiten.template.rest.core.feature.user.vo.UserId;
import com.codenaiten.template.rest.core.shared.constraint.Constraint;
import com.codenaiten.template.rest.core.shared.constraint.ConstraintViolation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

@Slf4j
@Component
public class UserIdConstraint implements Constraint<User> {

    private final UserRepository userRepository;

//--------------------------------------------------------------------------------------------------------------------\\
//---| CONSTRUCTOR |--------------------------------------------------------------------------------------------------\\
//--------------------------------------------------------------------------------------------------------------------\\

    public UserIdConstraint( final UserRepository userRepository ){
        log.info( "UserIdConstraint initialized" );

        if( Objects.isNull( userRepository ))
            throw new IllegalArgumentException( "UserRepository is required by UserIdConstraint" );

        this.userRepository = userRepository;
    }

//--------------------------------------------------------------------------------------------------------------------\\
//---| IMPLEMENTED METHODS |------------------------------------------------------------------------------------------\\
//--------------------------------------------------------------------------------------------------------------------\\

    @Override
    public Optional<ConstraintViolation<?>> check( final User candidate ){
        if( Objects.isNull( candidate ))
            throw new IllegalArgumentException( "Account candidate to check UserIdConstraint is required" );

        final UserId id = candidate.getId();
        if( this.userRepository.exists( id )) return Optional.of( new AlreadyExistsUserIdConstraintViolation( candidate ));

        return Optional.empty();
    }

//--------------------------------------------------------------------------------------------------------------------\\

}
