package com.codenaiten.template.rest.core.feature.media.constraint.violation;

import com.codenaiten.template.rest.core.CoreMessageKey;
import com.codenaiten.template.rest.core.feature.media.Media;
import com.codenaiten.template.rest.core.shared.constraint.ConstraintViolation;
import lombok.Getter;

@Getter
public class AlreadyExistsMediaIdConstraintViolation extends ConstraintViolation<Media> {

//--------------------------------------------------------------------------------------------------------------------\\
//---| CONSTRUCTOR |--------------------------------------------------------------------------------------------------\\
//--------------------------------------------------------------------------------------------------------------------\\

    public AlreadyExistsMediaIdConstraintViolation( final Media media ){
        super( media, CoreMessageKey.ERROR_CONSTRAINT_MEDIA_ID_ALREADY_EXISTS, media.getId() );
    }

//--------------------------------------------------------------------------------------------------------------------\\

}
