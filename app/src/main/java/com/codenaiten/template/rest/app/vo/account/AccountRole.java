package com.codenaiten.template.rest.app.vo.account;

import com.codenaiten.template.rest.app.AppMessage;
import com.codenaiten.template.rest.app.exception.validation.ValidationException;
import com.codenaiten.template.rest.app.vo.ValueObject;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class AccountRole extends ValueObject<Integer> {

    private static final List<Integer> SUPPORTED_ROLES = List.of( 1, 2 );

// ------------------------------------------------------------------------------------------------------------------ \\

    public static final AccountRole ADMIN = new AccountRole( 1, "ADMIN" );
    public static final AccountRole USER = new AccountRole( 2, "USER" );

// ------------------------------------------------------------------------------------------------------------------ \\

    private final String name;

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| CONSTRUCTOR |------------------------------------------------------------------------------------------------ \\
// ------------------------------------------------------------------------------------------------------------------ \\

    private AccountRole( final Integer value, final String name ){
        super( value );
        this.name = name;
    }

    public AccountRole( final Integer value ){
        super( value );
        switch( value ){
            case 1: this.name = ADMIN.getName(); break;
            case 2: this.name = USER.getName(); break;
            default:
                final String roles = SUPPORTED_ROLES.stream().map( String::valueOf ).collect( Collectors.joining( ", " ));
                throw new ValidationException( AppMessage.ERROR_VALIDATION_VO_ACCOUNT_ROLE_VALUE_INVALID, roles, value );
        }
    }

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| VALIDATION |------------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    public static boolean test( final Integer value ){
        return test( () -> new AccountRole( value ));
    }

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| GETTERS |---------------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    public static List<AccountRole> getSupportedRoles(){
        return SUPPORTED_ROLES.stream().map( AccountRole::new ).toList();
    }

// ------------------------------------------------------------------------------------------------------------------ \\

}
