package com.codenaiten.template.rest.app.exception;

import com.codenaiten.template.rest.app.AppMessage;
import com.codenaiten.template.rest.app.i18n.MessageI18n;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;

@Slf4j
@Getter
public class UserMinAgeException extends AppException{

    private static final MessageI18n MESSAGE = AppMessage.ERROR_USER_MIN_AGE;

// ------------------------------------------------------------------------------------------------------------------ \\

    private final Integer userMinimumAge;
    private final LocalDate birthdate;

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| CONSTRUCTOR |------------------------------------------------------------------------------------------------ \\
// ------------------------------------------------------------------------------------------------------------------ \\

    public UserMinAgeException( final Integer userMinimumAge, final LocalDate birthdate ){
        super( MESSAGE, userMinimumAge, birthdate );
        this.userMinimumAge = userMinimumAge;
        this.birthdate = birthdate;
    }

// ------------------------------------------------------------------------------------------------------------------ \\

}
