package com.codenaiten.template.rest.app.api;

import com.codenaiten.template.rest.app.dto.command.PageableCommand;
import com.codenaiten.template.rest.app.dto.command.account.CreateAccountCommand;
import com.codenaiten.template.rest.app.dto.command.account.FilterAccountCommand;
import com.codenaiten.template.rest.app.dto.command.account.UpdateAccountCommand;
import com.codenaiten.template.rest.app.dto.result.AccountInfoResult;
import com.codenaiten.template.rest.app.dto.result.PageResult;
import com.codenaiten.template.rest.app.entity.Account;
import com.codenaiten.template.rest.app.entity.User;
import com.codenaiten.template.rest.app.vo.Email;
import com.codenaiten.template.rest.app.vo.account.AccountId;
import com.codenaiten.template.rest.app.vo.account.AccountPassword;
import com.codenaiten.template.rest.app.vo.account.AccountRole;

import java.util.List;
import java.util.Locale;

/**
 * Service con los casos de uso relacionados con los {@link Account} en el sistema.
 */
public interface AccountService {

// ------------------------------------------------------------------------------------------------------------------ \\

    /**
     * Obtiene la información de la {@link Account} del {@link User} autenticado en el sistema.
     *
     * @return {@link AccountInfoResult} con la información de la {@link Account} del {@link User} autenticado.
     */
    AccountInfoResult me();

// ------------------------------------------------------------------------------------------------------------------ \\

    /**
     * Obtiene la información de la {@link Account} a partir de su identificador.
     *
     * @param id {@link AccountId} que representa el identificador de la {@link Account} de la cual se van a consultar
     *           los datos.
     *
     * @return {@link AccountInfoResult} con la información de la {@link Account} consultada.
     */
    AccountInfoResult get( AccountId id );

// ------------------------------------------------------------------------------------------------------------------ \\

    /**
     * Busca {@link Account} en el sistema mediante un término de búsqueda.
     *
     * @param search {@link String} con el término de búsqueda.
     * @param pageableCommand {@link PageableCommand} con los datos relacionados con la paginación.
     *
     * @return {@link PageResult} con la lista de {@link AccountInfoResult} resultante de la búsqueda a partir del
     *         término de búsqueda.
     */
    PageResult<AccountInfoResult> search( String search, PageableCommand pageableCommand );

// ------------------------------------------------------------------------------------------------------------------ \\

    /**
     * Busca {@link Account} en el sistema mediante un filtro de búsqueda.
     *
     * @param filterCommand {@link FilterAccountCommand} con los datos relacionados con el filtro de la búsqueda.
     * @param pageableCommand {@link PageableCommand} con los datos relacionados con la paginación.
     *
     * @return {@link PageResult} con la lista de {@link AccountInfoResult} resultante de la búsqueda a partir del
     *         filtro de búsqueda.
     */
    PageResult<AccountInfoResult> search( FilterAccountCommand filterCommand, PageableCommand pageableCommand );

// ------------------------------------------------------------------------------------------------------------------ \\

    /**
     * Obtiene la lista de {@link AccountRole} que se pueden asignar a un {@link Account}.
     *
     * @return {@link List} con la lista de {@link AccountRole} que se pueden asignar a una {@link Account}.
     */
    List<AccountRole> getSupportedRoles();

// ------------------------------------------------------------------------------------------------------------------ \\

    /**
     * Crea un nuevo {@link Account} en el sistema.
     *
     * @param command {@link CreateAccountCommand} con los datos del nuevo {@link Account} a crear.
     *
     * @return {@link AccountInfoResult} con los datos del nuevo {@link Account} que se ha creado.
     */
    AccountInfoResult create( CreateAccountCommand command );

// ------------------------------------------------------------------------------------------------------------------ \\

    /**
     * Actualiza la información de una {@link Account} a partir de su identificador.
     *
     * @param id {@link AccountId} que representa el identificador de la {@link Account} a actualizar.
     * @param command {@link UpdateAccountCommand} con los nuevos datos con los que se van actualizar los datos del
     *                {@link Account}.
     *
     * @return {@link AccountInfoResult} con la información actualizada del {@link Account}.
     */
    AccountInfoResult update( AccountId id, UpdateAccountCommand command );

// ------------------------------------------------------------------------------------------------------------------ \\

    /**
     * Actualiza el {@link Locale} de la {@link Account} del {@link User} autenticado en el sistema.
     *
     * @param lang {@link Locale} que representa el nuevo {@link Locale} que se va a establecer en la {@link Account}.
     *
     * @return {@link AccountInfoResult} con la información actualizada del {@link Account}.
     */
    AccountInfoResult updateLang( Locale lang );

// ------------------------------------------------------------------------------------------------------------------ \\

    /**
     * Actualiza el {@link Email} de la {@link Account} del {@link User} autenticado en el sistema.
     *
     * @param password {@link AccountPassword} que representa la contraseña de la {@link Account} para confirmar la
     *                 acción.
     * @param newEmail {@link Email} que representa el nuevo {@link Email} que se va a establecer en la {@link Account}.
     *
     * @return {@link AccountInfoResult} con la información actualizada del {@link Account}.
     */
    AccountInfoResult updateEmail( AccountPassword password, Email newEmail );

// ------------------------------------------------------------------------------------------------------------------ \\

    /**
     * Actualiza el {@link AccountPassword} de la {@link Account} del {@link User} autenticado en el sistema.
     *
     * @param password {@link AccountPassword} que representa la contraseña de la {@link Account} para confirmar la
     *                 acción.
     * @param newPassword {@link AccountPassword} que representa el nuevo {@link AccountPassword} que se va a establecer
     *                    en la {@link Account}.
     *
     * @return {@link AccountInfoResult} con la información actualizada del {@link Account}.
     */
    AccountInfoResult updatePassword( AccountPassword password, AccountPassword newPassword );

// ------------------------------------------------------------------------------------------------------------------ \\

    /**
     * Elimina la {@link Account} a partir de su identificador.
     *
     * @param id {@link AccountId} que representa el identificador de la {@link Account} a eliminar.
     *
     * @return {@link AccountInfoResult} con la información de la {@link Account} eliminada.
     */
    AccountInfoResult delete( AccountId id );

// ------------------------------------------------------------------------------------------------------------------ \\

    /**
     * Elimina la {@link Account} del {@link User} autenticado en el sistema.
     *
     * @param password {@link AccountPassword} que representa la contraseña de la {@link Account} para confirmar la
     *                 acción.
     *
     * @return {@link AccountInfoResult} con la información de la {@link Account} eliminada.
     */
    AccountInfoResult delete( AccountPassword password );

// ------------------------------------------------------------------------------------------------------------------ \\

}
