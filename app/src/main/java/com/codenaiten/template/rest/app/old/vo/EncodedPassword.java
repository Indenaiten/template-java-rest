package com.codenaiten.template.rest.app.old.vo;

public class EncodedPassword extends BaseValueObject<String> {

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| CONSTRUCTOR |------------------------------------------------------------------------------------------------ \\
// ------------------------------------------------------------------------------------------------------------------ \\

    /**
     * Constructor que crea un {@link EncodedPassword} a partir de un {@link String}.
     *
     * @param value {@link String} que representa el valor del {@link EncodedPassword}.
     */
    public EncodedPassword( final String value ){
        super( value );
    }

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| VALIDATION |------------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    /**
     * Valida si el valor de un {@link EncodedPassword} es valido.
     *
     * @param value {@link String} que representa el valor del {@link EncodedPassword}.
     *
     * @return {@code true} si el valor del {@link EncodedPassword} es valido, {@code false} en caso contrario.
     */
    public static boolean test( final String value ){
        return test( () -> new EncodedPassword( value ));
    }

// ------------------------------------------------------------------------------------------------------------------ \\

}
