package com.codenaiten.template.rest.app.policy;

import com.codenaiten.template.rest.app.entity.User;
import com.codenaiten.template.rest.app.exception.UserMinAgeException;
import com.codenaiten.template.rest.app.exception.validation.ValidationException;
import com.codenaiten.template.rest.app.properties.AppProperties;
import com.codenaiten.template.rest.app.spec.UserMinimumAgeSpec;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;

/**
 * Policy que permite comprobar si la edad de un {@link User} es mayor o igual a la edad mínima establecida en el
 * sistema.
 */
@Slf4j
@RequiredArgsConstructor
public class UserMinimumAgePolicy {

    /** Properties con información relacionada con la configuración de la aplicación */
    private final AppProperties appProperties;

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| METHODS |---------------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    /**
     * Comprueba si la edad del {@link User} es mayor o igual a la edad mínima establecida en el sistema.
     *
     * @param birthdate {@link LocalDate} que representa la fecha de nacimiento del {@link User}.
     *
     * @throws ValidationException si la edad de {@link User} es inferior a la edad mínima establecida en el sistema.
     *
     * @see UserMinimumAgeSpec
     */
    public void check( final LocalDate birthdate ){
        final Integer userMinimumAge = this.appProperties.getUserMinimumAge();
        if( new UserMinimumAgeSpec( userMinimumAge ).not().test( birthdate )){
            throw new UserMinAgeException( userMinimumAge, birthdate );
        }
    }

// ------------------------------------------------------------------------------------------------------------------ \\

}
