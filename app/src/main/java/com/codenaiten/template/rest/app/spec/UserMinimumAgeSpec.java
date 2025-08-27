package com.codenaiten.template.rest.app.spec;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
public class UserMinimumAgeSpec implements Specification<LocalDate>{

    private final Integer userMinimumAge;

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| IMPLEMENTED METHODS |---------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    @Override
    public boolean test( final LocalDate candidate ){
        if( Objects.isNull( candidate )) return false;
        return candidate.until( LocalDate.now() ).getYears() >= this.userMinimumAge;
    }

// ------------------------------------------------------------------------------------------------------------------ \\

}
