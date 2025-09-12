package com.codenaiten.template.rest.core.feature.account.constraint.violation;

import com.codenaiten.template.rest.core.CoreMessageKey;
import com.codenaiten.template.rest.core.feature.account.Account;
import com.codenaiten.template.rest.core.shared.constraint.ConstraintViolation;
import lombok.Getter;

@Getter
public class InvalidAccountOwnerConstraintViolation extends ConstraintViolation<Account> {

//--------------------------------------------------------------------------------------------------------------------\\
//---| CONSTRUCTOR |--------------------------------------------------------------------------------------------------\\
//--------------------------------------------------------------------------------------------------------------------\\

    public InvalidAccountOwnerConstraintViolation( final Account account ){
        super( account, CoreMessageKey.ERROR_CONSTRAINT_ACCOUNT_OWNER_INVALID, account.getOwner() );
    }

//--------------------------------------------------------------------------------------------------------------------\\

}
