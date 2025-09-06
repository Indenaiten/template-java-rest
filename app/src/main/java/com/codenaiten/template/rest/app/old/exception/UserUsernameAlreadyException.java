package com.codenaiten.template.rest.app.old.exception;

import com.codenaiten.template.rest.app.old.AppMessage;
import com.codenaiten.template.rest.app.old.i18n.MessageI18n;
import com.codenaiten.template.rest.app.old.vo.user.UserUsername;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public class UserUsernameAlreadyException extends AppException{

    private static final MessageI18n MESSAGE = AppMessage.ERROR_USER_USERNAME_ALREADY_EXISTS;

// ------------------------------------------------------------------------------------------------------------------ \\

    private final UserUsername username;

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| CONSTRUCTOR |------------------------------------------------------------------------------------------------ \\
// ------------------------------------------------------------------------------------------------------------------ \\

    public UserUsernameAlreadyException( final UserUsername username ){
        super( MESSAGE, username );
        this.username = username;
    }

// ------------------------------------------------------------------------------------------------------------------ \\

}
