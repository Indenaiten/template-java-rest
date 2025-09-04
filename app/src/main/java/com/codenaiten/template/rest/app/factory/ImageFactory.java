package com.codenaiten.template.rest.app.factory;

import com.codenaiten.template.rest.app.AppMessage;
import com.codenaiten.template.rest.app.entity.Image;
import com.codenaiten.template.rest.app.entity.User;
import com.codenaiten.template.rest.app.exception.validation.ValidationException;
import com.codenaiten.template.rest.app.vo.Timestamp;
import com.codenaiten.template.rest.app.vo.image.ImageContentType;
import com.codenaiten.template.rest.app.vo.image.ImageId;
import com.codenaiten.template.rest.app.vo.user.UserSurname;
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
     * @param owner {@link User} que representa el propietario de la {@link Image}.
     * @param imageContentType {@link ImageContentType} que representa el tipo de contenido de la {@link Image}.
     * @param size {@link Long} que representa el tamaño del contenido de la {@link Image}.
     *
     * @return {@link Factory} con los datos proporcionados de la {@link Image}.
     */
    public Factory create( final User owner, final ImageContentType imageContentType, final Long size ) {
        // Step 01: Check Required fields
        if( Objects.isNull( owner )) throw new ValidationException( AppMessage.ERROR_VALIDATION_IMAGE_OWNER_REQUIRED );
        if( Objects.isNull( imageContentType )) throw new ValidationException( AppMessage.ERROR_VALIDATION_IMAGE_CONTENT_TYPE_REQUIRED );
        if( Objects.isNull( size )) throw new ValidationException( AppMessage.ERROR_VALIDATION_IMAGE_SIZE_REQUIRED );

        // Step 02 - Return Factory
        return new Factory( owner, imageContentType.value(), size );
    }

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| FACTORY |---------------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    /**
     * Clase que permite crear un objeto de tipo {@link Image} con un patrón Builder.
     */
    @RequiredArgsConstructor
    public class Factory {

        /** Información requerida del {@link User} propietario de la {@link Image} */
        private final User owner;

        /** Información requerida del {@link ImageContentType} de la {@link Image} */
        private final String imageContentType;

        /** Información requerida del tamaño del contenido de la {@link Image} */
        private final Long size;

    // -------------------------------------------------------------------------------------------------------------- \\

        /** Información opcional de la clave de la {@link Image} */
        private String key;

    // -------------------------------------------------------------------------------------------------------------- \\

        /**
         * Permite asignar el key de la {@link Image}.
         *
         * @param key {@link UserSurname} que representa el apellido del {@link User}.
         *
         * @return {@link UserFactory.Factory} con el {@link UserSurname} asignado.
         */
        public Factory key( final String key ){
            this.key = key;
            return this;
        }

    // -------------------------------------------------------------------------------------------------------------- \\

        /**
         * Permite crear un objeto de tipo {@link Image} a partir de los datos proporcionados en el {@link Factory}.
         *
         * @return {@link Image} creado a partir de los datos proporcionados en el {@link Factory}.
         */
        public Image build(){
            return this.build( ImageId.random() );
        }

        /**
         * Permite crear un objeto de tipo {@link Image} a partir de los datos proporcionados en el {@link Factory} y
         * con un determinado identificador.
         *
         * @param id {@link ImageId} que representa el identificador único con el que se va a crear la {@link Image}.
         *
         * @return {@link Image} creado a partir de los datos proporcionados en el {@link Factory} y con el identificador
         * {@link ImageId} proporcionado.
         */
        public Image build( final ImageId id ){
            // Step 01: Create Image and return
            final Timestamp now = Timestamp.now();
            return Image.builder().id( id.value() ).owner( this.owner ).contentType( this.imageContentType )
                    .size( this.size ).key( this.key ).createdAt( now.value() ).updatedAt( now.value() ).build();
        }
    }

// ------------------------------------------------------------------------------------------------------------------ \\

}
