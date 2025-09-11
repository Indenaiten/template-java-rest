package com.codenaiten.template.rest.core.feature.user.constraint.violation;

import com.codenaiten.template.rest.core.feature.user.User;
import com.codenaiten.template.rest.core.shared.AppMessageKey;
import com.codenaiten.template.rest.core.shared.constraint.ConstraintViolation;
import lombok.Getter;

@Getter
public class MinimumAgeUserBirthdateConstraintViolation extends ConstraintViolation<User> {

    private final Integer minimumUserAge;

//--------------------------------------------------------------------------------------------------------------------\\
//---| CONSTRUCTOR |--------------------------------------------------------------------------------------------------\\
//--------------------------------------------------------------------------------------------------------------------\\

    public MinimumAgeUserBirthdateConstraintViolation(final User user, final Integer minimumUserAge ){
        super( user, AppMessageKey.ERROR_CONSTRAINT_USER_BIRTHDATE_MINIMUM_AGE, minimumUserAge, user.getAge() );
        this.minimumUserAge = minimumUserAge;
    }

//--------------------------------------------------------------------------------------------------------------------\\

}
