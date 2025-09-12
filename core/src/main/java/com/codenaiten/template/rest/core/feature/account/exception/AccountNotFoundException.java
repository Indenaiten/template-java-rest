package com.codenaiten.template.rest.core.feature.account.exception;

import com.codenaiten.template.rest.core.CoreMessageKey;
import com.codenaiten.template.rest.core.feature.account.vo.AccountId;
import com.codenaiten.template.rest.core.shared.exception.LocalizedException;
import lombok.Getter;

import java.util.Optional;

@Getter
public class AccountNotFoundException extends LocalizedException {

    public static final CoreMessageKey DEFAULT = CoreMessageKey.ERROR_ACCOUNT_NOT_FOUND;
    public static final CoreMessageKey MESSAGE_KEY = CoreMessageKey.ERROR_ACCOUNT_NOT_FOUND_BY_ID;

//--------------------------------------------------------------------------------------------------------------------\\

    private final AccountId id;

//--------------------------------------------------------------------------------------------------------------------\\
//---| CONSTRUCTOR |--------------------------------------------------------------------------------------------------\\
//--------------------------------------------------------------------------------------------------------------------\\

    public AccountNotFoundException( final AccountId id ){
        super( MESSAGE_KEY, id );
        this.id = id;
    }

    public AccountNotFoundException(){
        super( DEFAULT );
        this.id = null;
    }

    public AccountNotFoundException( final Throwable cause, final AccountId id ){
        super( cause, MESSAGE_KEY, id );
        this.id = id;
    }

    public AccountNotFoundException( final Throwable cause ){
        super( cause, DEFAULT );
        this.id = null;
    }

//--------------------------------------------------------------------------------------------------------------------\\
//---| GETTER |-------------------------------------------------------------------------------------------------------\\
//--------------------------------------------------------------------------------------------------------------------\\

    public Optional<AccountId> getId() {
        return Optional.ofNullable( this.id );
    }

//--------------------------------------------------------------------------------------------------------------------\\

}
