package com.codenaiten.template.rest.app.old.exception.data.found;

import com.codenaiten.template.rest.app.old.AppMessage;
import com.codenaiten.template.rest.app.old.i18n.MessageI18n;
import com.codenaiten.template.rest.app.old.vo.account.AccountId;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public class AccountNotFoundByIdException extends NotFoundException{

    private static final MessageI18n MESSAGE = AppMessage.ERROR_DATA_ACCOUNT_NOT_FOUND_BY_ID;

// ------------------------------------------------------------------------------------------------------------------ \\

    private final AccountId id;

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| CONSTRUCTOR |------------------------------------------------------------------------------------------------ \\
// ------------------------------------------------------------------------------------------------------------------ \\

    public AccountNotFoundByIdException( final AccountId id ){
        super( MESSAGE, id );
        this.id = id;
    }

// ------------------------------------------------------------------------------------------------------------------ \\

}
