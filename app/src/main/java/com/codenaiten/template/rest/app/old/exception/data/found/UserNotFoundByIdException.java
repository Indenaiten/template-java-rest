package com.codenaiten.template.rest.app.old.exception.data.found;

import com.codenaiten.template.rest.app.old.AppMessage;
import com.codenaiten.template.rest.app.old.i18n.MessageI18n;
import com.codenaiten.template.rest.app.old.vo.user.UserId;
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
