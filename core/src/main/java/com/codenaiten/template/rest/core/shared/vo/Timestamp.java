package com.codenaiten.template.rest.core.shared.vo;

import com.codenaiten.template.rest.core.CoreMessageKey;
import com.codenaiten.template.rest.core.shared.exception.ValidationException;

import java.io.Serial;
import java.time.LocalDateTime;

/**
 * Value Object que representa una fecha y hora de un momento determinado.
 *
 * @see BaseValueObject
 * @see LocalDateTime
 */
public class Timestamp extends BaseValueObject<LocalDateTime> {

    @Serial
    private static final long serialVersionUID = 1L;

//--------------------------------------------------------------------------------------------------------------------\\
//---| CONSTRUCTOR |--------------------------------------------------------------------------------------------------\\
//--------------------------------------------------------------------------------------------------------------------\\

    /**
     * Constructor que crea un {@link Timestamp} a partir de un {@link LocalDateTime} válido.
     *
     * <li>El valor <strong>NO</strong> es {@code null}</li>
     * <li>El valor <strong>NO</strong> es posterior a la fecha y hora actual</li>
     *
     * @param value {@link LocalDateTime} que representa el valor del {@link Timestamp}.
     *
     * @throws ValidationException Si el valor del {@link Timestamp} no es válido.
     */
    public Timestamp( final LocalDateTime value ){
        super( value );
        if( value.isAfter( LocalDateTime.now() )) throw new ValidationException( CoreMessageKey.ERROR_VALIDATION_VO_TIMESTAMP_DATE_FUTURE, value );
    }

//--------------------------------------------------------------------------------------------------------------------\\
//---| BUILDER |------------------------------------------------------------------------------------------------------\\
//--------------------------------------------------------------------------------------------------------------------\\

    /**
     * Obtiene un nuevo {@link Timestamp} con la fecha y hora actual.
     *
     * @return {@link Timestamp} con la fecha y hora actual.
     */
    public static Timestamp now(){
        return new Timestamp( LocalDateTime.now() );
    }

//--------------------------------------------------------------------------------------------------------------------\\
//---| VALIDATION |---------------------------------------------------------------------------------------------------\\
//--------------------------------------------------------------------------------------------------------------------\\

    /**
     * Valida si el valor del {@link Timestamp} es válido.
     *
     * @param value {@link LocalDateTime} que representa el valor del {@link Timestamp}.
     *
     * @return {@code true} si el valor del {@link Timestamp} es válido, {@code false} en caso contrario.
     */
    public static boolean test( final LocalDateTime value ){
        return test( () -> new Timestamp( value ));
    }

//--------------------------------------------------------------------------------------------------------------------\\

}
