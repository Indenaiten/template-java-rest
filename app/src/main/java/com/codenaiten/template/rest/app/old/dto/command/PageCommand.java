package com.codenaiten.template.rest.app.old.dto.command;

import java.util.Optional;

public record PageCommand(
        Integer page,
        Integer size
) {

    public static final Integer DEFAULT_PAGE = 0;
    public static final Integer DEFAULT_SIZE = 25;

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| CONSTRUCTOR |------------------------------------------------------------------------------------------------ \\
// ------------------------------------------------------------------------------------------------------------------ \\

    public PageCommand{
        page = Optional.ofNullable( page ).orElse( DEFAULT_PAGE );
        size = Optional.ofNullable( size ).orElse( DEFAULT_SIZE );
    }

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| BUILDER |---------------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    public static PageCommand of( Integer page ){
        return new PageCommand( page, null );
    }

    public static PageCommand create(){
        return new PageCommand( null, null );
    }

// ------------------------------------------------------------------------------------------------------------------ \\

}
