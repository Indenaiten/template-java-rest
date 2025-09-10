package com.codenaiten.template.rest.core.feature.user.validation.constraint;

import com.codenaiten.template.rest.core.feature.user.User;
import com.codenaiten.template.rest.core.feature.user.exception.UniquenessUserUsernameConstraintException;
import com.codenaiten.template.rest.core.feature.user.port.spi.UserRepository;
import com.codenaiten.template.rest.core.feature.user.vo.UserUsername;
import com.codenaiten.template.rest.core.shared.exception.ConstraintException;
import com.codenaiten.template.rest.core.shared.validator.Constraint;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

@Slf4j
@Component
public class UniquenessUserUsernameConstraint implements Constraint<User> {

    private final UserRepository userRepository;

//--------------------------------------------------------------------------------------------------------------------\\
//---| CONSTRUCTOR |--------------------------------------------------------------------------------------------------\\
//--------------------------------------------------------------------------------------------------------------------\\

    public UniquenessUserUsernameConstraint(final UserRepository userRepository ){
        log.info( "UniquenessUserUsernameConstraint initialized" );

        if( Objects.isNull( userRepository ))
            throw new IllegalArgumentException( "UserRepository is required by UniquenessUserUsernameConstraint" );

        this.userRepository = userRepository;
    }


//--------------------------------------------------------------------------------------------------------------------\\
//---| IMPLEMENTED METHODS |------------------------------------------------------------------------------------------\\
//--------------------------------------------------------------------------------------------------------------------\\

    @Override
    public Optional<ConstraintException> check( final User candidate ){
        if( Objects.isNull( candidate ))
            throw new IllegalArgumentException( "User candidate to check UniquenessUserUsernameConstraint is required" );

        final UserUsername username = candidate.getUsername();
        if( this.userRepository.exists( username ))
            return Optional.of( new UniquenessUserUsernameConstraintException( candidate ));

        return Optional.empty();
    }

//--------------------------------------------------------------------------------------------------------------------\\

}
