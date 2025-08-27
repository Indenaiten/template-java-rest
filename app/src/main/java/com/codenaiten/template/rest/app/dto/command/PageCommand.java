package com.codenaiten.template.rest.app.dto.command;

import java.util.Optional;

public record PageCommand(
        Integer page,
        Integer size
) {

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| BUILDER |---------------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    public static PageCommand of( Integer page, Integer size ){
        page = Optional.ofNullable( page ).orElse( 0 );
        size = Optional.ofNullable( size ).orElse( 25 );
        return new PageCommand( page, size );
    }
    public static PageCommand of( Integer page ){
        page = Optional.ofNullable( page ).orElse( 0 );
        return of( page, null );
    }

    public static PageCommand create(){
        return of( null, null );
    }

// ------------------------------------------------------------------------------------------------------------------ \\

}
