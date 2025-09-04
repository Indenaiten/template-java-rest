package com.codenaiten.template.rest.app.vo.account;

import com.codenaiten.template.rest.app.AppMessage;
import com.codenaiten.template.rest.app.entity.Account;
import com.codenaiten.template.rest.app.exception.validation.ValidationException;
import com.codenaiten.template.rest.app.vo.BaseValueObject;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Value Object que representa el rol de una {@link Account}.
 *
 * @see BaseValueObject
 * @see Integer
 */
@Getter
public class AccountRole extends BaseValueObject<Integer> {

    /** Lista de identificadores de roles soportados */
    private static final List<Integer> SUPPORTED_ROLES = List.of( 1, 2 );

// ------------------------------------------------------------------------------------------------------------------ \\

    /** Rol de administrador */
    public static final AccountRole ADMIN = new AccountRole( 1, "ADMIN" );

    /** Rol de usuario */
    public static final AccountRole USER = new AccountRole( 2, "USER" );

// ------------------------------------------------------------------------------------------------------------------ \\

    /** Nombre del rol de la {@link Account} */
    private final String name;

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| CONSTRUCTOR |------------------------------------------------------------------------------------------------ \\
// ------------------------------------------------------------------------------------------------------------------ \\

    /**
     * Constructor privado que crea un {@link AccountRole} a partir del identificador y el nombre del rol.
     *
     * @param value {@link Integer} que representa el identificador del rol.
     * @param name {@link String} que representa el nombre del rol.
     */
    private AccountRole( final Integer value, final String name ){
        super( value );
        this.name = name;
    }

    /**
     * Constructor privado que crea un {@link AccountRole} a partir del identificador.
     *
     * @param value {@link Integer} que representa el identificador del rol.
     */
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

    /**
     * Valida si el valor de un {@link AccountRole} es valido.
     *
     * @param value {@link Integer} que representa el valor del {@link AccountRole}.
     *
     * @return {@code true} si el valor del {@link AccountRole} es valido, {@code false} en caso contrario.
     */
    public static boolean test( final Integer value ){
        return test( () -> new AccountRole( value ));
    }

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| GETTERS |---------------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    /**
     * Obtiene una lista de {@link AccountRole} soportados.
     *
     * @return {@link List} con la lista de {@link AccountRole} soportados.
     */
    public static List<AccountRole> getSupportedRoles(){
        return SUPPORTED_ROLES.stream().map( AccountRole::new ).toList();
    }

// ------------------------------------------------------------------------------------------------------------------ \\

}
