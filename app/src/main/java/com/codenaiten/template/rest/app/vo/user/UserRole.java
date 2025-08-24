package com.codenaiten.template.rest.app.vo.user;

import com.codenaiten.template.rest.app.exception.ValidationException;
import com.codenaiten.template.rest.app.i18n.AppMessage;
import com.codenaiten.template.rest.app.vo.ValueObject;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class UserRole extends ValueObject<Integer> {

    private static final List<Integer> ROLES = List.of( 1, 2 );
    public static final UserRole ADMIN = new UserRole( 1 );
    public static final UserRole USER = new UserRole( 2 );

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| CONSTRUCTOR |------------------------------------------------------------------------------------------------ \\
// ------------------------------------------------------------------------------------------------------------------ \\

    public UserRole( final Integer value ){
        super( value );
        if( ROLES.stream().noneMatch( role -> Objects.equals( role, value ))){
            final String roles = ROLES.stream().map( String::valueOf ).collect( Collectors.joining( ", " ));
            throw new ValidationException( AppMessage.ERROR_VALIDATION_VO_USER_ROLE_VALUE_INVALID, roles, value );
        }
    }

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| VALIDATION |------------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    public static boolean test( final Integer value ){
        return test( () -> new UserRole( value ));
    }

// ------------------------------------------------------------------------------------------------------------------ \\

}
