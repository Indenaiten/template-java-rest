package com.codenaiten.template.rest.app.vo.account;

import com.codenaiten.template.rest.app.AppMessage;
import com.codenaiten.template.rest.app.exception.validation.ValidationException;
import com.codenaiten.template.rest.app.vo.ValueObject;
import com.codenaiten.template.rest.app.vo.user.UserId;

import java.util.UUID;

public class AccountId extends ValueObject<UUID> {

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| CONSTRUCTOR |------------------------------------------------------------------------------------------------ \\
// ------------------------------------------------------------------------------------------------------------------ \\

    public AccountId( final UUID value ){
        super( value );
    }

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| BUILDER |---------------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    public static AccountId random(){
        final UUID id = UUID.randomUUID();
        return new AccountId( id );
    }

    public static AccountId of( final String value ){
        final UUID id;
        try{ id = UUID.fromString( value ); }
        catch( final IllegalArgumentException e ){
            throw new ValidationException( AppMessage.ERROR_VALIDATION_VO_ACCOUNT_ID_VALUE_INVALID, value );
        }
        return new AccountId( id );
    }

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| VALIDATION |------------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    public static boolean test( final UUID value ){
        return test( () -> new AccountId( value ));
    }

    public static boolean test( final String value ){
        return test( () -> UserId.of( value ));
    }

// ------------------------------------------------------------------------------------------------------------------ \\

}
