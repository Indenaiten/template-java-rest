package com.codenaiten.template.rest.core.feature.user.constraint.violation;

import com.codenaiten.template.rest.core.feature.media.Media;
import com.codenaiten.template.rest.core.feature.user.User;
import com.codenaiten.template.rest.core.shared.AppMessageKey;
import com.codenaiten.template.rest.core.shared.constraint.ConstraintViolation;
import lombok.Getter;

@Getter
public class InvalidContentTypeUserImageConstraintViolation extends ConstraintViolation<User> {

    private final Media image;

//--------------------------------------------------------------------------------------------------------------------\\
//---| CONSTRUCTOR |--------------------------------------------------------------------------------------------------\\
//--------------------------------------------------------------------------------------------------------------------\\

    public InvalidContentTypeUserImageConstraintViolation(final User user, final Media image ){
        super( user, AppMessageKey.ERROR_CONSTRAINT_USER_IMAGE_CONTENT_TYPE_INVALID, image.getId(), image.getContentType() );
        this.image = image;
    }

//--------------------------------------------------------------------------------------------------------------------\\

}
