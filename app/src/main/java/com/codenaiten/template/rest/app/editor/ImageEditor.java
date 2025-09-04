package com.codenaiten.template.rest.app.editor;

import com.codenaiten.template.rest.app.entity.Image;
import com.codenaiten.template.rest.app.vo.Timestamp;
import com.codenaiten.template.rest.app.vo.image.ImageContentType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

/**
 * Editor que permite actualizar objetos de tipo {@link Image}.
 */
@Slf4j
@RequiredArgsConstructor
public class ImageEditor {

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| METHODS |---------------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    /**
     * Permite actualizar la información de un objeto de tipo {@link Image}.
     *
     * @param image {@link Image} que representa la información de la imagen que se va a actualizar.
     *
     * @return {@link Editor} con el {@link Image} proporcionado para ser actualizado.
     */
    public Editor update( final Image image ) {
        return new Editor( image );
    }

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| EDITOR |----------------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    /**
     * Clase que permite actualizar un objeto de tipo {@link Image} con un patrón Builder.
     */
    public class Editor {

        /** Instancia del {@link Image} que se va a actualizar */
        private final Image image;

    // -------------------------------------------------------------------------------------------------------------- \\

        /** Información del {@link ImageContentType} que se va a actualizar en la instancia del {@link Image} */
        private String contentType;

    // -------------------------------------------------------------------------------------------------------------- \\

        /**
         * Constructor que recibe un objeto de tipo {@link Image} para ser actualizado y establece a partir de este los
         * valores actuales de la imagen.
         *
         * @param image {@link Image} que representa la imagen que se va a actualizar.
         */
        public Editor( final Image image ){
            this.image = image;
            this.contentType = image.getContentType();
        }

    // -------------------------------------------------------------------------------------------------------------- \\

        /**
         * Permite asignar un nuevo {@link ImageContentType} en el {@link Image} proporcionado en el {@link Editor}.
         *
         * @param contentType {@link ImageContentType} que representa el nuevo content type del {@link Image}.
         *
         * @return {@link Editor} con el nuevo {@link ImageContentType} asignado.
         */
        public Editor contentType( final ImageContentType contentType ){
            this.contentType = contentType.value();
            return this;
        }

    // -------------------------------------------------------------------------------------------------------------- \\

        /**
         * Permite determinar si hay datos en el {@link Editor} diferentes de los que ya tiene la {@link Image}
         * proporcionada.
         *
         * @return {@code true} si hay cambios, {@code false} en caso contrario.
         */
        public boolean hasChanges() {
            return !Objects.equals( this.contentType, this.image.getContentType() );
        }

    // -------------------------------------------------------------------------------------------------------------- \\

        /**
         * Permite aplicar los cambios proporcionados en el {@link Editor} a la instancia del {@link Image}.
         */
        public void apply(){
            if( this.hasChanges() ){
                this.image.setContentType( this.contentType );
                this.image.setUpdatedAt( Timestamp.now().value() );
            }
        }
    }

// ------------------------------------------------------------------------------------------------------------------ \\

}
