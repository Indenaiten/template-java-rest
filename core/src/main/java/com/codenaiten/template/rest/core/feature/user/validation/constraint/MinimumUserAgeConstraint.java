package com.codenaiten.template.rest.core.feature.user.validation.constraint;

import com.codenaiten.template.rest.core.feature.user.User;
import com.codenaiten.template.rest.core.feature.user.port.spi.MinimumUserAgeProvider;
import com.codenaiten.template.rest.core.shared.AppMessage;
import com.codenaiten.template.rest.core.shared.exception.ConstraintException;
import com.codenaiten.template.rest.core.shared.validator.Constraint;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

@Slf4j
@Component
public class MinimumUserAgeConstraint implements Constraint<User> {

    private final MinimumUserAgeProvider minimumUserAgeProvider;

//--------------------------------------------------------------------------------------------------------------------\\
//---| CONSTRUCTOR |--------------------------------------------------------------------------------------------------\\
//--------------------------------------------------------------------------------------------------------------------\\

    public MinimumUserAgeConstraint( final MinimumUserAgeProvider minimumUserAgeProvider ){
        log.info( "MinimumUserAgeConstraint initialized" );

        if( Objects.isNull( minimumUserAgeProvider ))
            throw new IllegalArgumentException( "MinimumUserAgeProvider is required by MinimumUserAgeConstraint" );

        this.minimumUserAgeProvider = minimumUserAgeProvider;
    }

//--------------------------------------------------------------------------------------------------------------------\\
//---| IMPLEMENTED METHODS |------------------------------------------------------------------------------------------\\
//--------------------------------------------------------------------------------------------------------------------\\

    @Override
    public Optional<ConstraintException> check(final User candidate ){
        if( Objects.isNull( candidate ))
            throw new IllegalArgumentException( "User candidate to check MinimumUserAgeConstraint is required" );

        final Integer minAge = this.minimumUserAgeProvider.getMinimumAge();
        final Integer age = candidate.getAge();
        if( age > minAge )
            return Optional.of( new ConstraintException( AppMessage.ERROR_CONSTRAINT_USER_MINIMUM_AGE, minAge, age ));

        return Optional.empty();
    }

//--------------------------------------------------------------------------------------------------------------------\\

}
