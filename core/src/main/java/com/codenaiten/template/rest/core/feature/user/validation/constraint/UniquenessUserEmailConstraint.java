package com.codenaiten.template.rest.core.feature.user.validation.constraint;

import com.codenaiten.template.rest.core.feature.user.User;
import com.codenaiten.template.rest.core.feature.user.port.spi.UserRepository;
import com.codenaiten.template.rest.core.shared.AppMessage;
import com.codenaiten.template.rest.core.shared.exception.ConstraintException;
import com.codenaiten.template.rest.core.shared.validator.Constraint;
import com.codenaiten.template.rest.core.shared.vo.Email;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

@Slf4j
@Component
public class UniquenessUserEmailConstraint implements Constraint<User> {

    private final UserRepository userRepository;

//--------------------------------------------------------------------------------------------------------------------\\
//---| CONSTRUCTOR |--------------------------------------------------------------------------------------------------\\
//--------------------------------------------------------------------------------------------------------------------\\

    public UniquenessUserEmailConstraint( final UserRepository userRepository ){
        log.info( "UniquenessUserEmailConstraint initialized" );

        if( Objects.isNull( userRepository ))
            throw new IllegalArgumentException( "UserRepository is required by UniquenessUserEmailConstraint" );

        this.userRepository = userRepository;
    }

//--------------------------------------------------------------------------------------------------------------------\\
//---| IMPLEMENTED METHODS |------------------------------------------------------------------------------------------\\
//--------------------------------------------------------------------------------------------------------------------\\

    @Override
    public Optional<ConstraintException> check( final User candidate ) {
        if( Objects.isNull( candidate ))
            throw new IllegalArgumentException( "User candidate to check UniquenessUserEmailConstraint is required" );

        final Email email = candidate.getEmail();
        if( !this.userRepository.exists( email ))
            return Optional.of( new ConstraintException( AppMessage.ERROR_CONSTRAINT_USER_MINIMUM_AGE, email ));

        return Optional.empty();
    }

//--------------------------------------------------------------------------------------------------------------------\\

}
