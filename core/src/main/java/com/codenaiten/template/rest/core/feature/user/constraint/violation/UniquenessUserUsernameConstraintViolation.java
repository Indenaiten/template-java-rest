package com.codenaiten.template.rest.core.feature.user.constraint.violation;

import com.codenaiten.template.rest.core.CoreMessageKey;
import com.codenaiten.template.rest.core.feature.user.User;
import com.codenaiten.template.rest.core.shared.constraint.ConstraintViolation;
import lombok.Getter;

@Getter
public class UniquenessUserUsernameConstraintViolation extends ConstraintViolation<User> {

//--------------------------------------------------------------------------------------------------------------------\\
//---| CONSTRUCTOR |--------------------------------------------------------------------------------------------------\\
//--------------------------------------------------------------------------------------------------------------------\\

    public UniquenessUserUsernameConstraintViolation( final User user ){
        super( user, CoreMessageKey.ERROR_CONSTRAINT_USER_USERNAME_UNIQUENESS, user.getUsername() );
    }

//--------------------------------------------------------------------------------------------------------------------\\

}
