package com.codenaiten.template.rest.core.feature.account.constraint.violation;

import com.codenaiten.template.rest.core.feature.account.Account;
import com.codenaiten.template.rest.core.shared.AppMessageKey;
import com.codenaiten.template.rest.core.shared.constraint.ConstraintViolation;
import lombok.Getter;

@Getter
public class AlreadyExistsAccountIdConstraintViolation extends ConstraintViolation<Account> {

//--------------------------------------------------------------------------------------------------------------------\\
//---| CONSTRUCTOR |--------------------------------------------------------------------------------------------------\\
//--------------------------------------------------------------------------------------------------------------------\\

    public AlreadyExistsAccountIdConstraintViolation(final Account account ){
        super( account, AppMessageKey.ERROR_CONSTRAINT_ACCOUNT_ID_ALREADY_EXISTS, account.getId() );
    }

//--------------------------------------------------------------------------------------------------------------------\\

}
