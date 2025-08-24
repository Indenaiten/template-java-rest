package com.codenaiten.template.rest.app.exception.account;

import com.codenaiten.template.rest.app.exception.NotFoundException;
import com.codenaiten.template.rest.app.i18n.AppMessage;
import com.codenaiten.template.rest.app.vo.account.AccountId;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public class AccountNotFoundByIdException extends NotFoundException{

    private final AccountId id;

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| CONSTRUCTOR |------------------------------------------------------------------------------------------------ \\
// ------------------------------------------------------------------------------------------------------------------ \\

    public AccountNotFoundByIdException( final AccountId id ){
        super( AppMessage.ERROR_ACCOUNT_NOT_FOUND_BY_ID, id );
        this.id = id;
    }

// ------------------------------------------------------------------------------------------------------------------ \\

}
