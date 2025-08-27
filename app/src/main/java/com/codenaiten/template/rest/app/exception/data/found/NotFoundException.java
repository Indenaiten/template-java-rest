package com.codenaiten.template.rest.app.exception.data.found;

import com.codenaiten.template.rest.app.AppMessage;
import com.codenaiten.template.rest.app.exception.data.DataException;
import com.codenaiten.template.rest.app.i18n.MessageI18n;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public class NotFoundException extends DataException {

    private static final AppMessage MESSAGE = AppMessage.ERROR_DATA_NOT_FOUND;

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| CONSTRUCTOR |------------------------------------------------------------------------------------------------ \\
// ------------------------------------------------------------------------------------------------------------------ \\

    public NotFoundException(){
        super( MESSAGE );
    }

    public NotFoundException( final MessageI18n message, final Object... args ){
        super( message, args );
    }

// ------------------------------------------------------------------------------------------------------------------ \\

}
