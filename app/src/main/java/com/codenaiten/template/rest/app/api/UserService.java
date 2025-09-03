package com.codenaiten.template.rest.app.api;

import com.codenaiten.template.rest.app.dto.command.PageableCommand;
import com.codenaiten.template.rest.app.dto.command.user.FilterUserCommand;
import com.codenaiten.template.rest.app.dto.command.user.UpdateUserCommand;
import com.codenaiten.template.rest.app.dto.result.PageResult;
import com.codenaiten.template.rest.app.dto.result.UserInfoResult;
import com.codenaiten.template.rest.app.entity.User;
import com.codenaiten.template.rest.app.vo.user.UserId;

/**
 * Service con los casos de uso relacionados con los {@link User} en el sistema.
 */
public interface UserService {

// ------------------------------------------------------------------------------------------------------------------ \\

    /**
     * Obtiene la información del {@link User} autenticado en el sistema.
     *
     * @return {@link UserInfoResult} con la información del {@link User} autenticado.
     */
    UserInfoResult me();

// ------------------------------------------------------------------------------------------------------------------ \\

    /**
     * Obtiene la información de un {@link User} a partir de su identificador.
     *
     * @param id {@link UserId} que representa el identificador del {@link User} del cual se van a consultar los datos.
     *
     * @return {@link UserInfoResult} con la información del {@link User} consultado.
     */
    UserInfoResult get( UserId id );

// ------------------------------------------------------------------------------------------------------------------ \\

    /**
     * Busca {@link User} en el sistema mediante un término de búsqueda.
     *
     * @param search {@link String} con el término de búsqueda.
     * @param pageableCommand {@link PageableCommand} con los datos relacionados con la paginación.
     *
     * @return {@link PageResult} con la lista de {@link UserInfoResult} resultante de la búsqueda a partir del término
     *         de búsqueda.
     */
    PageResult<UserInfoResult> search( String search, PageableCommand pageableCommand );

// ------------------------------------------------------------------------------------------------------------------ \\

    /**
     * Busca {@link User} en el sistema mediante un filtro de búsqueda.
     *
     * @param filterCommand {@link FilterUserCommand} con los datos relacionados con el filtro de la búsqueda.
     * @param pageableCommand {@link PageableCommand} con los datos relacionados con la paginación.
     *
     * @return {@link PageResult} con la lista de {@link UserInfoResult} resultante de la búsqueda a partir del filtro
     *         de búsqueda.
     */
    PageResult<UserInfoResult> search( FilterUserCommand filterCommand, PageableCommand pageableCommand );

// ------------------------------------------------------------------------------------------------------------------ \\

    /**
     * Actualiza la información de un {@link User} a partir de su identificador.
     *
     * @param id {@link UserId} que representa el identificador del {@link User} a actualizar.
     * @param command {@link UpdateUserCommand} con los nuevos datos con los que se van actualizar los datos del {@link User}.
     *
     * @return {@link UserInfoResult} con la información actualizada del {@link User}.
     */
    UserInfoResult update( UserId id, UpdateUserCommand command );

// ------------------------------------------------------------------------------------------------------------------ \\

    /**
     * Actualiza la información del {@link User} autenticado en el sistema.
     *
     * @param command {@link UpdateUserCommand} con los nuevos datos con los que se van actualizar los datos del {@link User}.
     *
     * @return {@link UserInfoResult} con la información actualizada del {@link User}.
     */
    UserInfoResult update( UpdateUserCommand command );

// ------------------------------------------------------------------------------------------------------------------ \\

}
