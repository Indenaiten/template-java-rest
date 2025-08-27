package com.codenaiten.template.rest.app.exception.data.found;

import com.codenaiten.template.rest.app.AppMessage;
import com.codenaiten.template.rest.app.i18n.MessageI18n;
import com.codenaiten.template.rest.app.vo.user.UserId;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public class UserNotFoundByIdException extends NotFoundException{

    private static final MessageI18n MESSAGE = AppMessage.ERROR_DATA_USER_NOT_FOUND_BY_ID;

// ------------------------------------------------------------------------------------------------------------------ \\

    private final UserId id;

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| CONSTRUCTOR |------------------------------------------------------------------------------------------------ \\
// ------------------------------------------------------------------------------------------------------------------ \\

    public UserNotFoundByIdException( final UserId id ){
        super( MESSAGE, id );
        this.id = id;
    }

// ------------------------------------------------------------------------------------------------------------------ \\

}
