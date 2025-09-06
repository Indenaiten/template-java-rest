package com.codenaiten.template.rest.app.policy;

import com.codenaiten.template.rest.core.policy.UserMinimumAgePolicy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Objects;

@Slf4j
@Component
@RequiredArgsConstructor
public class DefaultUserMinimumAgePolicy implements UserMinimumAgePolicy{

    private static final Integer MINIMUM_AGE = 18;
// ------------------------------------------------------------------------------------------------------------------ \\
// ---| IMPLEMENTED METHODS |---------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    @Override
    public boolean check( final Integer age ){
        if( Objects.isNull( age )) return false;
        return age >= MINIMUM_AGE;
    }

    @Override
    public boolean check( final LocalDate birthDate ){
        if( Objects.isNull( birthDate )) return false;
        final Integer age = birthDate.until( LocalDate.now() ).getYears();
        return check( age );
    }


    // ------------------------------------------------------------------------------------------------------------------ \\

}
