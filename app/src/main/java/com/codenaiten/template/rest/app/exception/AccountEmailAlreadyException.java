package com.codenaiten.template.rest.app.exception;

import com.codenaiten.template.rest.app.AppMessage;
import com.codenaiten.template.rest.app.i18n.MessageI18n;
import com.codenaiten.template.rest.app.vo.Email;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public class AccountEmailAlreadyException extends AppException{

    private static final MessageI18n MESSAGE = AppMessage.ERROR_ACCOUNT_EMAIL_ALREADY_EXISTS;

// ------------------------------------------------------------------------------------------------------------------ \\

    private final Email email;

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| CONSTRUCTOR |------------------------------------------------------------------------------------------------ \\
// ------------------------------------------------------------------------------------------------------------------ \\

    public AccountEmailAlreadyException( final Email email ){
        super( MESSAGE, email );
        this.email = email;
    }

// ------------------------------------------------------------------------------------------------------------------ \\

}
