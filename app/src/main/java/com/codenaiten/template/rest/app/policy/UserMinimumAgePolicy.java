package com.codenaiten.template.rest.app.policy;

import com.codenaiten.template.rest.app.AppMessage;
import com.codenaiten.template.rest.app.exception.validation.ValidationException;
import com.codenaiten.template.rest.app.properties.AppProperties;
import com.codenaiten.template.rest.app.spec.UserMinimumAgeSpec;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;

@Slf4j
@RequiredArgsConstructor
public class UserMinimumAgePolicy {

    private final AppProperties appProperties;

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| METHODS |---------------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    public void check( final LocalDate birthdate ){
        final Integer userMinimumAge = this.appProperties.getUserMinimumAge();
        if( new UserMinimumAgeSpec( userMinimumAge ).not().test( birthdate )){
            throw new ValidationException( AppMessage.ERROR_USER_MIN_AGE, userMinimumAge, birthdate );
        }
    }

// ------------------------------------------------------------------------------------------------------------------ \\

}
