package com.codenaiten.template.rest.app.exception.data.found;

import com.codenaiten.template.rest.app.AppMessage;
import com.codenaiten.template.rest.app.i18n.MessageI18n;
import com.codenaiten.template.rest.app.vo.image.ImageId;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public class ImageNotFoundByIdException extends NotFoundException{

    private static final MessageI18n MESSAGE = AppMessage.ERROR_DATA_IMAGE_NOT_FOUND_BY_ID;

// ------------------------------------------------------------------------------------------------------------------ \\

    private final ImageId id;

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| CONSTRUCTOR |------------------------------------------------------------------------------------------------ \\
// ------------------------------------------------------------------------------------------------------------------ \\

    public ImageNotFoundByIdException( final ImageId id ){
        super( MESSAGE, id );
        this.id = id;
    }

// ------------------------------------------------------------------------------------------------------------------ \\

}
