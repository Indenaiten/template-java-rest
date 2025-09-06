package com.codenaiten.template.rest.app.old.spec;

import com.codenaiten.template.rest.app.old.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Specification que permite comprobar si la edad de un {@link User} es mayor o igual a la edad mínima establecida en el
 * sistema.
 *
 * @see Specification
 * @see LocalDate
 */
@Slf4j
@RequiredArgsConstructor
public class UserMinimumAgeSpec implements Specification<LocalDate>{

    /** Edad mínima permitida para el acceso al sistema */
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
