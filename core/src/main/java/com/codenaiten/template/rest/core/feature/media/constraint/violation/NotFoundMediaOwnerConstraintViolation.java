package com.codenaiten.template.rest.core.feature.media.constraint.violation;

import com.codenaiten.template.rest.core.feature.media.Media;
import com.codenaiten.template.rest.core.shared.AppMessageKey;
import com.codenaiten.template.rest.core.shared.constraint.ConstraintViolation;
import lombok.Getter;

@Getter
public class NotFoundMediaOwnerConstraintViolation extends ConstraintViolation<Media> {

//--------------------------------------------------------------------------------------------------------------------\\
//---| CONSTRUCTOR |--------------------------------------------------------------------------------------------------\\
//--------------------------------------------------------------------------------------------------------------------\\

    public NotFoundMediaOwnerConstraintViolation( final Media media ){
        super( media, AppMessageKey.ERROR_CONSTRAINT_MEDIA_OWNER_NOT_FOUND, media.getOwner() );
    }

//--------------------------------------------------------------------------------------------------------------------\\

}
