package com.codenaiten.template.rest.app.api;

import com.codenaiten.template.rest.app.dto.command.auth.LoginCommand;
import com.codenaiten.template.rest.app.dto.command.auth.RegisterCommand;
import com.codenaiten.template.rest.app.dto.result.AccountInfoResult;
import com.codenaiten.template.rest.app.dto.result.LoginResult;

import java.util.List;

/**
 * Service con los casos de uso relacionados con la autenticación de usuarios en el sistema.
 */
public interface AuthenticationService {

// ------------------------------------------------------------------------------------------------------------------ \\

    /**
     * Registra un nuevo usuario en el sistema.
     *
     * @param command {@link RegisterCommand} con los datos del nuevo usuario a registrar.
     *
     * @return {@link AccountInfoResult} con los datos de la cuenta de usuario que ha sido creada.
     */
    AccountInfoResult register( RegisterCommand command );

// ------------------------------------------------------------------------------------------------------------------ \\

    /**
     * Autentica un usuario en el sistema.
     *
     * @param command {@link LoginCommand} con los datos del usuario a autenticar.
     * @param ip {@link String} que representa la IP del cliente, puede ser {@code null} pero hará falta la misma IP
     *           con la que se haya autenticado ({@code null} o un {@link String}) para validar correctamente los tokens
     *           de autenticación.
     *
     * @return {@link LoginResult} con los datos de la respuesta de un usuario autenticado con éxito.
     */
    LoginResult login( LoginCommand command, String ip );

// ------------------------------------------------------------------------------------------------------------------ \\

    /**
     * Actualiza los toknes de autenticación de un usuario mediante el refresh token del usuario.
     *
     * @param token {@link String} que representa el refresh token del usuario.
     * @param ip {@link String} que representa la IP del cliente, puede ser {@code null} pero para validar correctamente
     *           el token será necesaria la misma IP con la que se haya autenticado ({@code null} o un {@link String}).
     *
     * @return {@link LoginResult} con los datos de la respuesta de un usuario autenticado con éxito.
     */
    LoginResult refresh( String token, String ip );

// ------------------------------------------------------------------------------------------------------------------ \\

    /**
     * Elimina la autenticación del usuario autenticación.
     */
    void logout();

// ------------------------------------------------------------------------------------------------------------------ \\

    /**
     * Invalida todos los tokens de autenticación asociados a una lista de IPs.
     *
     * @param ips {@link List} con los {@link String} que representan las IPs correspondientes para invalidar los tokens
     *            de autenticación.
     */
    void invalidate( List<String> ips );

// ------------------------------------------------------------------------------------------------------------------ \\// ------------------------------------------------------------------------------------------------------------------ \\

    /**
     * Invalida todos los tokens de autenticación asociados al usuario autenticado.
     */
    void invalidate();

// ------------------------------------------------------------------------------------------------------------------ \\

}
