package com.codenaiten.template.rest.core.feature.user.constraint.violation;

import com.codenaiten.template.rest.core.CoreMessageKey;
import com.codenaiten.template.rest.core.feature.media.Media;
import com.codenaiten.template.rest.core.feature.user.User;
import com.codenaiten.template.rest.core.shared.constraint.ConstraintViolation;
import lombok.Getter;

@Getter
public class InvalidOwnerUserImageConstraintViolation extends ConstraintViolation<User> {

    private final Media image;

//--------------------------------------------------------------------------------------------------------------------\\
//---| CONSTRUCTOR |--------------------------------------------------------------------------------------------------\\
//--------------------------------------------------------------------------------------------------------------------\\

    public InvalidOwnerUserImageConstraintViolation(final User user, final Media image ){
        super( user, CoreMessageKey.ERROR_CONSTRAINT_USER_IMAGE_OWNER_INVALID, user.getId(), image.getId() );
        this.image = image;
    }

//--------------------------------------------------------------------------------------------------------------------\\

}
