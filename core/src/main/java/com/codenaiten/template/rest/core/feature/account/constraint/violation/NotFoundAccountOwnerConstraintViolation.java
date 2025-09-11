package com.codenaiten.template.rest.core.feature.account.constraint.violation;

import com.codenaiten.template.rest.core.feature.account.Account;
import com.codenaiten.template.rest.core.shared.AppMessageKey;
import com.codenaiten.template.rest.core.shared.constraint.ConstraintViolation;
import lombok.Getter;

@Getter
public class NotFoundAccountOwnerConstraintViolation extends ConstraintViolation<Account> {

//--------------------------------------------------------------------------------------------------------------------\\
//---| CONSTRUCTOR |--------------------------------------------------------------------------------------------------\\
//--------------------------------------------------------------------------------------------------------------------\\

    public NotFoundAccountOwnerConstraintViolation( final Account account ){
        super( account, AppMessageKey.ERROR_CONSTRAINT_ACCOUNT_OWNER_NOT_FOUND, account.getOwner() );
    }

//--------------------------------------------------------------------------------------------------------------------\\

}
