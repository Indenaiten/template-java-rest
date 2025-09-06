package com.codenaiten.template.rest.app.old.api;

import com.codenaiten.template.rest.app.old.dto.command.account.CreateImageCommand;
import com.codenaiten.template.rest.app.old.dto.result.ImageContentResult;
import com.codenaiten.template.rest.app.old.dto.result.ImageInfoResult;
import com.codenaiten.template.rest.app.old.entity.Image;
import com.codenaiten.template.rest.app.old.vo.image.ImageId;

/**
 * Service con los casos de uso relacionados con las entidades {@link Image}.
 */
public interface ImageService {

// ------------------------------------------------------------------------------------------------------------------ \\

    /**
     * Obtiene la información y el contenido del fichero de la entidad {@link Image} a partir de su identificador único.
     *
     * @param id {@link ImageId} que representa el identificador único de la entidad {@link Image} de la cual se van
     *        a consultar los datos.
     *
     * @return {@link ImageContentResult} con la información y el contenido del fichero de la entidad {@link Image}
     *         encontrada.
     */
    ImageContentResult getContent( ImageId id );

// ------------------------------------------------------------------------------------------------------------------ \\

    /**
     * Crea una nueva entidad {@link Image} en el sistema.
     *
     * @param command {@link CreateImageCommand} con los datos de la nueva entidad {@link Image} a crear.
     *
     * @return {@link ImageInfoResult} con la información de la entidad {@link Image} creada.
     */
    ImageInfoResult create( CreateImageCommand command );

// ------------------------------------------------------------------------------------------------------------------ \\

}
