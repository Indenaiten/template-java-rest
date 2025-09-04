package com.codenaiten.template.rest.app.factory;

import com.codenaiten.template.rest.app.AppMessage;
import com.codenaiten.template.rest.app.entity.Image;
import com.codenaiten.template.rest.app.exception.validation.ValidationException;
import com.codenaiten.template.rest.app.vo.Timestamp;
import com.codenaiten.template.rest.app.vo.image.ImageContentType;
import com.codenaiten.template.rest.app.vo.image.ImageId;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

/**
 * Factory que permite crear objetos de tipo {@link Image}.
 */
@Slf4j
@RequiredArgsConstructor
public class ImageFactory {

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| METHODS |---------------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    /**
     * Permite crear un objeto de tipo {@link Image} a partir de los datos obligatorios proporcionados.
     *
     * @param imageContentType {@link ImageContentType} que representa el tipo de contenido de la imagen.
     *
     * @return {@link Factory} con los datos proporcionados de la {@link Image}.
     */
    public Factory create( final ImageContentType imageContentType ) {
        // Step 01: Check Required fields
        if( Objects.isNull( imageContentType )) throw new ValidationException( AppMessage.ERROR_VALIDATION_IMAGE_CONTENT_TYPE_REQUIRED );

        // Step 02 - Return Factory
        return new Factory( imageContentType.value() );
    }

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| FACTORY |---------------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    /**
     * Clase que permite crear un objeto de tipo {@link Image} con un patrón Builder.
     */
    @RequiredArgsConstructor
    public class Factory {

        /** Información requerida del {@link ImageContentType} de la {@link Image} */
        private final String imageContentType;

    // -------------------------------------------------------------------------------------------------------------- \\

        /**
         * Permite crear un objeto de tipo {@link Image} a partir de los datos proporcionados en el {@link Factory}.
         *
         * @return {@link Image} creado a partir de los datos proporcionados en el {@link Factory}.
         */
        public Image build(){
            // Step 01: Create Image and return
            final ImageId id = ImageId.random();
            final Timestamp now = Timestamp.now();
            return Image.builder().id( id.value() ).contentType( this.imageContentType ).createdAt( now.value() )
                    .updatedAt( now.value() ).build();
        }
    }

// ------------------------------------------------------------------------------------------------------------------ \\

}
