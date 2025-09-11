package com.codenaiten.template.rest.core.feature.user.constraint.violation;

import com.codenaiten.template.rest.core.feature.user.User;
import com.codenaiten.template.rest.core.shared.AppMessageKey;
import com.codenaiten.template.rest.core.shared.constraint.ConstraintViolation;
import lombok.Getter;

@Getter
public class UniquenessUserEmailConstraintViolation extends ConstraintViolation<User> {

//--------------------------------------------------------------------------------------------------------------------\\
//---| CONSTRUCTOR |--------------------------------------------------------------------------------------------------\\
//--------------------------------------------------------------------------------------------------------------------\\

    public UniquenessUserEmailConstraintViolation( final User user ){
        super( user, AppMessageKey.ERROR_CONSTRAINT_USER_EMAIL_UNIQUENESS, user.getEmail() );
    }

//--------------------------------------------------------------------------------------------------------------------\\

}
