package com.codenaiten.template.rest.core.feature.user.constraint;

import com.codenaiten.template.rest.core.feature.user.User;
import com.codenaiten.template.rest.core.feature.user.constraint.violation.MinimumAgeUserBirthdateConstraintViolation;
import com.codenaiten.template.rest.core.feature.user.port.MinimumUserAgeProvider;
import com.codenaiten.template.rest.core.shared.constraint.Constraint;
import com.codenaiten.template.rest.core.shared.constraint.ConstraintViolation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserBirthdateConstraint implements Constraint<User> {

    private final MinimumUserAgeProvider minimumUserAgeProvider;

//--------------------------------------------------------------------------------------------------------------------\\
//---| IMPLEMENTED METHODS |------------------------------------------------------------------------------------------\\
//--------------------------------------------------------------------------------------------------------------------\\

    @Override
    public Optional<ConstraintViolation<?>> check( final User candidate ){
        if( Objects.isNull( candidate ))
            throw new IllegalArgumentException( "User candidate to check UserBirthdateConstraint is required" );

        final Integer minAge = this.minimumUserAgeProvider.getMinimumAge();
        final Integer age = candidate.getAge();
        if( age > minAge ) return Optional.of( new MinimumAgeUserBirthdateConstraintViolation( candidate, minAge ));

        return Optional.empty();
    }

//--------------------------------------------------------------------------------------------------------------------\\

}
