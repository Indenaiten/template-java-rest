package com.codenaiten.template.rest.core.message;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum AppMessage implements Message{

    // Error
    ERROR_GENERIC( "An unexpected error has occurred" );

// ------------------------------------------------------------------------------------------------------------------ \\

    private final String value;

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| IMPLEMENTED METHODS |---------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    @Override
    public String value(){
        return this.value;
    }

// ------------------------------------------------------------------------------------------------------------------ \\

}
