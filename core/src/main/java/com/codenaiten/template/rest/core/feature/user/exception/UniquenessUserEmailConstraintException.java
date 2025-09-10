package com.codenaiten.template.rest.core.feature.user.exception;

import com.codenaiten.template.rest.core.feature.user.User;
import com.codenaiten.template.rest.core.shared.AppMessage;
import com.codenaiten.template.rest.core.shared.exception.ConstraintException;
import lombok.Getter;

@Getter
public class UniquenessUserEmailConstraintException extends ConstraintException {

    private final User user;

//--------------------------------------------------------------------------------------------------------------------\\
//---| CONSTRUCTOR |--------------------------------------------------------------------------------------------------\\
//--------------------------------------------------------------------------------------------------------------------\\

    public UniquenessUserEmailConstraintException( final User user ){
        super( AppMessage.ERROR_CONSTRAINT_USER_EMAIL_UNIQUENESS, user.getEmail() );
        this.user = user;
    }

//--------------------------------------------------------------------------------------------------------------------\\

}
