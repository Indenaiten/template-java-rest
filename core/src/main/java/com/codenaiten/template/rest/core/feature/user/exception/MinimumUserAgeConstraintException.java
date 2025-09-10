package com.codenaiten.template.rest.core.feature.user.exception;

import com.codenaiten.template.rest.core.feature.user.User;
import com.codenaiten.template.rest.core.shared.AppMessage;
import com.codenaiten.template.rest.core.shared.exception.ConstraintException;
import lombok.Getter;

@Getter
public class MinimumUserAgeConstraintException extends ConstraintException {

    private final User user;
    private final Integer minimumAge;

//--------------------------------------------------------------------------------------------------------------------\\
//---| CONSTRUCTOR |--------------------------------------------------------------------------------------------------\\
//--------------------------------------------------------------------------------------------------------------------\\

    public MinimumUserAgeConstraintException( final User user, final Integer minimumAge ){
        super( AppMessage.ERROR_CONSTRAINT_USER_MINIMUM_AGE, minimumAge, user.getAge() );
        this.user = user;
        this.minimumAge = minimumAge;
    }

//--------------------------------------------------------------------------------------------------------------------\\

}
