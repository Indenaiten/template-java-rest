package com.codenaiten.template.rest.app.policy;

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
            throw new IllegalArgumentException( "User must be at least %d years old: %s".formatted( MINIMUM_AGE, birthdate ));
        }
    }

// ------------------------------------------------------------------------------------------------------------------ \\

}
