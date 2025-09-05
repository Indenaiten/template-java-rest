package com.codenaiten.template.rest.app.api;

import com.codenaiten.template.rest.app.dto.command.PageCommand;
import com.codenaiten.template.rest.app.dto.command.account.CreateAccountCommand;
import com.codenaiten.template.rest.app.dto.command.account.FilterAccountCommand;
import com.codenaiten.template.rest.app.dto.command.account.UpdateAccountCommand;
import com.codenaiten.template.rest.app.dto.result.AccountInfoResult;
import com.codenaiten.template.rest.app.dto.result.PageResult;
import com.codenaiten.template.rest.app.entity.Account;
import com.codenaiten.template.rest.app.vo.Email;
import com.codenaiten.template.rest.app.vo.account.AccountId;
import com.codenaiten.template.rest.app.vo.account.AccountPassword;
import com.codenaiten.template.rest.app.vo.account.AccountRole;

import java.util.List;
import java.util.Locale;

/**
 * Service con los casos de uso relacionados con las entidades {@link Account}.
 */
public interface AccountService {

// ------------------------------------------------------------------------------------------------------------------ \\

    /**
     * Obtiene la información de la entidad {@link Account} a partir de su identificador único.
     *
     * @param id {@link AccountId} que representa el identificador único de la entidad {@link Account} de la cual se van
     *           a consultar los datos.
     *
     * @return {@link AccountInfoResult} con la información de la entidad {@link Account} encontrada.
     */
    AccountInfoResult get( AccountId id );

// ------------------------------------------------------------------------------------------------------------------ \\

    /**
     * Obtiene la información de la entidad {@link Account} del usuario autenticado.
     *
     * @return {@link AccountInfoResult} con la información de la entidad {@link Account} encontrada.
     */
    AccountInfoResult me();

// ------------------------------------------------------------------------------------------------------------------ \\

    /**
     * Busca la información de las entidades {@link Account} a partir de un término de búsqueda.
     *
     * @param search {@link String} que representa el término de búsqueda por el cual se van a recuperar los datos de
     *        las entidades {@link Account} que hay registradas en el sistema.
     * @param pageableCommand {@link PageCommand} que representa los datos de paginación que se van a utilizar para
     *        recuperar los datos de las entidades {@link Account} que hay registradas de forma paginada.
     *
     * @return {@link PageResult} de {@link AccountInfoResult} con la información de las entidades {@link Account} que
     *         se han encontrado.
     */
    PageResult<AccountInfoResult> search( String search, PageCommand pageableCommand );

// ------------------------------------------------------------------------------------------------------------------ \\

    /**
     * Busca la información de las entidades {@link Account} a partir de un filtro de búsqueda.
     *
     * @param filter {@link FilterAccountCommand} que representa el filtro de búsqueda por el cual se va a utilizar para
     *        recuperar los datos de las entidades {@link Account} que hay registradas en el sistema.
     * @param pageableCommand {@link PageCommand} que representa los datos de paginación que se van a utilizar para
     *        recuperar los datos de las entidades {@link Account} que hay registradas de forma paginada.
     *
     * @return {@link PageResult} de {@link AccountInfoResult} con la información de las entidades {@link Account} que
     *         se han encontrado.
     */
    PageResult<AccountInfoResult> search( FilterAccountCommand filter, PageCommand pageableCommand );

// ------------------------------------------------------------------------------------------------------------------ \\

    /**
     * Crea una nueva entidad {@link Account} en el sistema.
     *
     * @param command {@link CreateAccountCommand} con los datos de la nueva entidad {@link Account} a crear.
     *
     * @return {@link AccountInfoResult} con la información de la entidad {@link Account} creada.
     */
    AccountInfoResult create( CreateAccountCommand command );

// ------------------------------------------------------------------------------------------------------------------ \\

    /**
     * Actualiza los datos de una entidad {@link Account} registrada en el sistema a partir de su identificador único.
     *
     * @param id {@link AccountId} que representa el identificador único de la entidad {@link Account} que se requiere
     *        actualizar los datos.
     * @param command {@link UpdateAccountCommand} con los nuevos datos con los que se van a actualizar los datos de la
     *        entidad {@link Account} registrada en el sistema.
     *
     * @return {@link AccountInfoResult} con la nueva información de la entidad {@link Account} actualizada.
     */
    AccountInfoResult update( AccountId id, UpdateAccountCommand command );

// ------------------------------------------------------------------------------------------------------------------ \\

    /**
     * Actualiza el idioma de la entidad {@link Account} del usuario autenticado.
     *
     * @param lang {@link Locale} que representa el idioma a establecer en la entidad {@link Account} del usuario
     *        autenticado.
     *
     * @return {@link AccountInfoResult} con la nueva información de la entidad {@link Account} actualizada.
     */
    AccountInfoResult updateLang( Locale lang );

// ------------------------------------------------------------------------------------------------------------------ \\

    /**
     * Actualiza el correo electrónico de la entidad {@link Account} del usuario autenticado.
     *
     * @param password {@link AccountPassword} de la entidad {@link Account} del usuario autenticado para validar que es
     *        el usuario propietario de la cuenta quien esta realizando la acción.
     * @param newEmail {@link Email} de la entidad {@link Account} que se va a establecer como nuevo correo electrónico.
     *
     * @return {@link AccountInfoResult} con la nueva información de la entidad {@link Account} actualizada.
     */
    AccountInfoResult updateEmail( AccountPassword password, Email newEmail );

// ------------------------------------------------------------------------------------------------------------------ \\

    /**
     * Actualiza la contraseña de la entidad {@link Account} del usuario autenticado.
     *
     * @param password {@link AccountPassword} de la entidad {@link Account} del usuario autenticado para validar que es
     *        el usuario propietario de la cuenta quien esta realizando la acción.
     * @param password {@link AccountPassword} de la entidad {@link Account} que se va a establecer como nueva contraseña.
     *
     * @return {@link AccountInfoResult} con la nueva información de la entidad {@link Account} actualizada.
     */
    AccountInfoResult updatePassword( AccountPassword password, AccountPassword newPassword );

// ------------------------------------------------------------------------------------------------------------------ \\

    /**
     * Elimina la entidad {@link Account} registrada en el sistema mediante su identificador único.
     *
     * @param id {@link AccountId} que representa el identificador único de la entidad {@link Account} que se requiere
     *        eliminar del sistema.
     *
     * @return {@link AccountInfoResult} con la información de la entidad {@link Account} eliminada.
     */
    AccountInfoResult delete( AccountId id );

// ------------------------------------------------------------------------------------------------------------------ \\

    /**
     * Elimina la entidad {@link Account} del usuario autenticado.
     *
     * @param password {@link AccountPassword} de la entidad {@link Account} del usuario autenticado para validar que es
     *        el usuario propietario de la cuenta quien esta realizando la acción.
     *
     * @return {@link AccountInfoResult} con la información de la entidad {@link Account} eliminada.
     */
    AccountInfoResult delete( AccountPassword password );

// ------------------------------------------------------------------------------------------------------------------ \\

    /**
     * Obtiene la lista con los roles que se pueden asignar a una entidad {@link Account}.
     *
     * @return {@link List} de {@link AccountRole} que se pueden asignar a una entidad {@link Account}.
     */
    List<AccountRole> getSupportedRoles();

// ------------------------------------------------------------------------------------------------------------------ \\

}
