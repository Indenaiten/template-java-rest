package com.codenaiten.template.rest.app.api;

import com.codenaiten.template.rest.app.dto.command.account.CreateImageCommand;
import com.codenaiten.template.rest.app.dto.result.ImageContentResult;
import com.codenaiten.template.rest.app.dto.result.ImageInfoResult;
import com.codenaiten.template.rest.app.entity.Image;
import com.codenaiten.template.rest.app.vo.image.ImageId;

/**
 * Service con los casos de uso relacionados con las {@link Image} en el sistema.
 */
public interface ImageService {

// ------------------------------------------------------------------------------------------------------------------ \\

    /**
     * Obtiene la el contenido de la {@link Image} solicitada a través de su identificador {@link ImageId}.
     *
     * @param id {@link ImageId} que representa el identificador de la {@link Image} solicitada.
     *
     * @return {@link ImageContentResult} con la información del contenido de la {@link Image} solicitada.
     */
    ImageContentResult getContent( ImageId id );

// ------------------------------------------------------------------------------------------------------------------ \\
    /**
     * Crea una nueva {@link Image} en el sistema.
     *
     * @param command {@link CreateImageCommand} con los datos de la nueva {@link Image} a crear.
     *
     * @return {@link ImageInfoResult} con los datos de la nueva {@link Image} que se ha creado.
     */
    ImageInfoResult create( CreateImageCommand command );

// ------------------------------------------------------------------------------------------------------------------ \\

}
