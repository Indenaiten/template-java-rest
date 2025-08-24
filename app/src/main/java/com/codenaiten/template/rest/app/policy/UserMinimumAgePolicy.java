package com.codenaiten.template.rest.app.policy;

import com.codenaiten.template.rest.app.exception.ValidationException;
import com.codenaiten.template.rest.app.i18n.AppMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
public class UserMinimumAgePolicy {

    public static final int MINIMUM_AGE = 18;

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| METHODS |---------------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    public boolean test( final LocalDate birthdate ){
        if( Objects.isNull( birthdate )) return false;
        return birthdate.until( LocalDate.now() ).getYears() >= MINIMUM_AGE;
    }

    public void check( final LocalDate birthdate ){
        if( !this.test( birthdate )){
            throw new ValidationException( AppMessage.ERROR_VALIDATION_POLICY_USER_MIN_AGE, MINIMUM_AGE, birthdate );
        }
    }

// ------------------------------------------------------------------------------------------------------------------ \\

}
