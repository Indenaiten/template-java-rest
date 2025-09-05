package com.codenaiten.template.rest.app.api;

import com.codenaiten.template.rest.app.dto.command.PageableCommand;
import com.codenaiten.template.rest.app.dto.command.user.FilterUserCommand;
import com.codenaiten.template.rest.app.dto.command.user.UpdateUserCommand;
import com.codenaiten.template.rest.app.dto.result.ImageContentResult;
import com.codenaiten.template.rest.app.dto.result.PageResult;
import com.codenaiten.template.rest.app.dto.result.UserInfoResult;
import com.codenaiten.template.rest.app.entity.Image;
import com.codenaiten.template.rest.app.entity.User;
import com.codenaiten.template.rest.app.vo.user.UserId;

/**
 * Service con los casos de uso relacionados con las entidades {@link User}.
 */
public interface UserService {

// ------------------------------------------------------------------------------------------------------------------ \\

    /**
     * Obtiene la información de la entidad {@link User} a partir de su identificador único.
     *
     * @param id {@link UserId} que representa el identificador único de la entidad {@link User} de la cual se van
     *           a consultar los datos.
     *
     * @return {@link UserInfoResult} con la información de la entidad {@link User} encontrada.
     */
    UserInfoResult get( UserId id );

// ------------------------------------------------------------------------------------------------------------------ \\

    /**
     * Obtiene la información de la entidad {@link User} del usuario autenticado.
     *
     * @return {@link UserInfoResult} con la información de la entidad {@link User} encontrada.
     */
    UserInfoResult me();

// ------------------------------------------------------------------------------------------------------------------ \\

    /**
     * Busca la información de las entidades {@link User} a partir de un término de búsqueda.
     *
     * @param search {@link String} que representa el término de búsqueda por el cual se van a recuperar los datos de
     *        las entidades {@link User} que hay registradas en el sistema.
     * @param pageable {@link PageableCommand} que representa los datos de paginación que se van a utilizar para
     *        recuperar los datos de las entidades {@link User} que hay registradas de forma paginada.
     *
     * @return {@link PageResult} de {@link UserInfoResult} con la información de las entidades {@link User} que se han
     *         encontrado.
     */
    PageResult<UserInfoResult> search( String search, PageableCommand pageable );

// ------------------------------------------------------------------------------------------------------------------ \\

    /**
     * Busca la información de las entidades {@link User} a partir de un filtro de búsqueda.
     *
     * @param filter {@link FilterUserCommand} que representa el filtro de búsqueda por el cual se va a utilizar para
     *        recuperar los datos de las entidades {@link User} que hay registradas en el sistema.
     * @param pageable {@link PageableCommand} que representa los datos de paginación que se van a utilizar para
     *        recuperar los datos de las entidades {@link User} que hay registradas de forma paginada.
     *
     * @return {@link PageResult} de {@link UserInfoResult} con la información de las entidades {@link User} que se han
     *         encontrado.
     */
    PageResult<UserInfoResult> search( FilterUserCommand filter, PageableCommand pageable );

// ------------------------------------------------------------------------------------------------------------------ \\

    /**
     * Actualiza los datos de una entidad {@link User} registrada en el sistema a partir de su identificador único.
     *
     * @param id {@link UserId} que representa el identificador único de la entidad {@link User} que se requiere
     *        actualizar los datos.
     * @param command {@link UpdateUserCommand} con los nuevos datos con los que se van a actualizar los datos de la
     *        entidad {@link User} registrada en el sistema.
     *
     * @return {@link UserInfoResult} con la nueva información de la entidad {@link User} actualizada.
     */
    UserInfoResult update( UserId id, UpdateUserCommand command );

// ------------------------------------------------------------------------------------------------------------------ \\

    /**
     * Actualiza los datos de la entidad {@link User} del usuario autenticado.
     *
     * @param command {@link UpdateUserCommand} con los nuevos datos con los que se van a actualizar los datos de la
     *        entidad {@link User} del usuario autenticado.
     *
     * @return {@link UserInfoResult} con la nueva información de la entidad {@link User} actualizada.
     */
    UserInfoResult update( UpdateUserCommand command );

// ------------------------------------------------------------------------------------------------------------------ \\

    /**
     * Obtiene la información y el contenido de la entidad {@link Image} que representa la imagen de perfil de una
     * entidad {@link User} a partir del identificador único del usuario a consultar.
     *
     * @param id {@link UserId} que representa el identificador único de la entidad {@link User} de la cual se van a
     *        recuperar los datos de la {@link Image} que representa su imagen de perfil.
     *
     * @return {@link ImageContentResult} con la información y el contenido de la entidad {@link Image} que representa
     *         la imagen de perfil de la entidad {@link User} consultada.
     */
    ImageContentResult getImageProfile( UserId id );

// ------------------------------------------------------------------------------------------------------------------ \\

    /**
     * Obtiene la información y el contenido de la entidad {@link Image} que representa la imagen de perfil de una
     * entidad {@link User} del usuario autenticado.
     *
     * @return {@link ImageContentResult} con la información y el contenido de la entidad {@link Image} que representa
     *         la imagen de perfil de la entidad {@link User} consultada.
     */
    ImageContentResult getImageProfile();

// ------------------------------------------------------------------------------------------------------------------ \\

}
