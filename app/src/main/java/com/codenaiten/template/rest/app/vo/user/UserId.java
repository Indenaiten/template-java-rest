package com.codenaiten.template.rest.app.vo.user;

import com.codenaiten.template.rest.app.exception.ValidationException;
import com.codenaiten.template.rest.app.i18n.AppMessage;
import com.codenaiten.template.rest.app.vo.ValueObject;

import java.util.UUID;

public class UserId extends ValueObject<UUID> {

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| CONSTRUCTOR |------------------------------------------------------------------------------------------------ \\
// ------------------------------------------------------------------------------------------------------------------ \\

    public UserId( final UUID value ){
        super( value );
    }

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| BUILDER |---------------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    public static UserId random(){
        final UUID id = UUID.randomUUID();
        return new UserId( id );
    }

    public static UserId of( final String value ){
        final UUID id;
        try{ id = UUID.fromString( value ); }
        catch( final IllegalArgumentException e ){
            throw new ValidationException( AppMessage.ERROR_VALIDATION_VO_USER_ID_VALUE_INVALID, value );
        }
        return new UserId( id );
    }

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| VALIDATION |------------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    public static boolean test( final UUID value ){
        return test( () -> new UserId( value ));
    }

    public static boolean test( final String value ){
        return test( () -> UserId.of( value ));
    }

// ------------------------------------------------------------------------------------------------------------------ \\

}
