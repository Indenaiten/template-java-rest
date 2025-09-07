package com.codenaiten.template.rest.core.feature.user.policy;

import java.time.LocalDate;

public interface UserMinimumAgePolicy{
    boolean check( Integer age );
    boolean check( LocalDate birthDate );
}
