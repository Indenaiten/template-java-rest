package com.codenaiten.template.rest.app.spec;

/**
 * Interfaz para definir una especificación que permite evaluar si un candidato cumple con ciertos criterios para ser
 * considerado válido o no.
 *
 * @param <T> El tipo de objeto que se evaluará contra la especificación.
 */
public interface Specification<T> {

    /**
     * Evalúa si el candidato cumple con los criterios definidos por la especificación.
     *
     * @param candidate {@link T} El objeto a evaluar.
     *
     * @return {@code true} si el candidato cumple con los criterios, {@code false} en caso contrario.
     */
    boolean test( T candidate );

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| DEFAULT METHODS |-------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    /**
     * Combina esta especificación con otra utilizando una conjunción lógica (AND).
     *
     * @param other {@link Specification} Otra especificación a combinar.
     *
     * @return {@link Specification} Una nueva especificación que representa la conjunción de ambas.
     */
    default Specification<T> and( final Specification<T> other ){
        return ( candidate ) -> this.test( candidate ) && other.test( candidate );
    }

    /**
     * Combina esta especificación con otra utilizando una disyunción lógica (OR).
     *
     * @param other {@link Specification} Otra especificación a combinar.
     *
     * @return {@link Specification} Una nueva especificación que representa la disyunción de ambas.
     */
    default Specification<T> or( final Specification<T> other ){
        return ( candidate ) -> this.test( candidate ) || other.test( candidate );
    }

    /**
     * Invierte el resultado de esta especificación, creando una nueva que evalúa si el candidato NO cumple con los
     * criterios definidos por esta especificación.
     *
     * @return {@link Specification} Una nueva especificación que representa la negación de esta.
     */
    default Specification<T> not(){
        return ( candidate ) -> !this.test( candidate );
    }

// ------------------------------------------------------------------------------------------------------------------ \\

}
