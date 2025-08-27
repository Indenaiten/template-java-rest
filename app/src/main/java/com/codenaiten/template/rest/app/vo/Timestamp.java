package com.codenaiten.template.rest.app.vo;

import com.codenaiten.template.rest.app.AppMessage;
import com.codenaiten.template.rest.app.exception.validation.ValidationException;

import java.time.LocalDateTime;

public class Timestamp extends ValueObject<LocalDateTime> {

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| CONSTRUCTOR |------------------------------------------------------------------------------------------------ \\
// ------------------------------------------------------------------------------------------------------------------ \\

    public Timestamp( final LocalDateTime value ){
        super( value );
        if( value.isAfter( LocalDateTime.now() ))
            throw new ValidationException( AppMessage.ERROR_VALIDATION_VO_TIMESTAMP_VALUE_FUTURE, value );
    }

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| VALIDATION |------------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    public static boolean test( final LocalDateTime value ){
        return test( () -> new Timestamp( value ));
    }

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| BUILDER |---------------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    public static Timestamp now(){
        return new Timestamp( LocalDateTime.now() );
    }

// ------------------------------------------------------------------------------------------------------------------ \\

}
