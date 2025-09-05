package com.codenaiten.template.rest.app.api;

import com.codenaiten.template.rest.app.dto.command.auth.LoginCommand;
import com.codenaiten.template.rest.app.dto.command.auth.RegisterCommand;
import com.codenaiten.template.rest.app.dto.result.AccountInfoResult;
import com.codenaiten.template.rest.app.dto.result.LoginResult;
import com.codenaiten.template.rest.app.entity.Account;
import com.codenaiten.template.rest.app.entity.User;

import java.util.List;

/**
 * Service con los casos de uso relacionados con la autenticación de los usuarios en el sistema.
 */
public interface AuthenticationService {

// ------------------------------------------------------------------------------------------------------------------ \\

    /**
     * Registra una nueva entidad {@link Account} en el sistema.
     *
     * @param command {@link RegisterCommand} con los datos de la nueva entidad {@link Account} a registrar.
     *
     * @return {@link AccountInfoResult} con la información de la entidad {@link Account} registrada.
     */
    AccountInfoResult register( RegisterCommand command );

// ------------------------------------------------------------------------------------------------------------------ \\

    /**
     * Autentica un usuario en el sistema a partir de sus credenciales de acceso.
     * user: {@link User#getUsername()}, {@link Account#getEmail()} o {@link User#getId()} único del usuario
     * password: Contraseña de la {@link Account}
     *
     * @param command {@link LoginCommand} con los datos de acceso del usuario.
     * @param ip {@link String} que representa la información de la IP del cliente que realiza el acceso, puede ser
     *        {@code null} si no se conoce la IP del cliente.
     *
     * @return {@link LoginResult} con la información de respuesta del acceso al sistema.
     */
    LoginResult login( LoginCommand command, String ip );

// ------------------------------------------------------------------------------------------------------------------ \\

    /**
     * Actualiza los tokens de acceso de un usuario a partir de su token de refresco actual.
     *
     * @param token {@link String} que representa el token de refresco actual del usuario.
     * @param ip {@link String} que representa la información de la IP del cliente que realiza el acceso, puede ser
     *           {@code null} si no se conoce la IP del cliente.
     *
     * @return {@link LoginResult} con la información de respuesta del acceso al sistema.
     */
    LoginResult refresh( String token, String ip );

// ------------------------------------------------------------------------------------------------------------------ \\

    /**
     * Desloguea un usuario autenticado del sistema.
     */
    void logout();

// ------------------------------------------------------------------------------------------------------------------ \\// ------------------------------------------------------------------------------------------------------------------ \\

    /**
     * Invalida todos los tokens de acceso de un usuario autenticado del sistema.
     */
    void invalidate();

// ------------------------------------------------------------------------------------------------------------------ \\

    /**
     * Invalida los tokens de acceso de un usuario a partir de una lista de IPs.
     *
     * @param ips {@link List} de {@link String} que representan las IPs de los tokens de acceso a invalidar.
     */
    void invalidate( List<String> ips );

// ------------------------------------------------------------------------------------------------------------------ \\

}
