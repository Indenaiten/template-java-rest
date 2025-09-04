package com.codenaiten.template.rest.web.rest;

import com.codenaiten.template.rest.app.entity.Account;
import com.codenaiten.template.rest.app.entity.Image;
import com.codenaiten.template.rest.app.entity.User;
import com.codenaiten.template.rest.app.i18n.MessageI18n;
import com.codenaiten.template.rest.app.vo.account.AccountId;
import com.codenaiten.template.rest.app.vo.user.UserId;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Claves I18n del módulo Web para los mensajes del sistema
 *
 * @see MessageI18n
 */
@Getter
@RequiredArgsConstructor
public enum RestMessage implements MessageI18n {

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| CONFIGURATION |---------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    /** Mensaje de éxito que se utiliza cuando se actualiza el idioma del sistema correctamente. */
    SUCCESS_CONFIG_LANG( "rest.success.config.lang", "Language successfully set" ),
    /** Mensaje de éxito que se utiliza cuando se recupera el idioma del sistema correctamente. */
    SUCCESS_CONFIG_RETRIEVE_LANG_CONFIGURED( "rest.success.config.retrieve.lang.configured", "Language configured successfully retrieved" ),
    /** Mensaje de éxito que se utiliza cuando se recupera la lista de idiomas soportados del sistema correctamente. */
    SUCCESS_CONFIG_LIST_LANG_SUPPORTED( "rest.success.config.list.lang.supported", "List of supported languages successfully retrieved" ),

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| AUTHENTICATION |--------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    /** Mensaje de éxito que se utiliza cuando se ha realizado el registro de un nuevo usuario correctamente. */
    SUCCESS_AUTH_REGISTER( "rest.success.auth.register", "User successfully registered" ),
    /** Mensaje de éxito que se utiliza cuando se ha autenticado un usuario correctamente. */
    SUCCESS_AUTH_LOGIN( "rest.success.auth.login", "User successfully logged in" ),
    /** Mensaje de éxito que se utiliza cuando se ha actualizado correctamente el token de autenticación mediante el refresh token. */
    SUCCESS_AUTH_REFRESH_TOKEN( "rest.success.auth.refresh.token", "User successfully refreshed token" ),
    /** Mensaje de éxito que se utiliza cuando se ha desconectado un usuario correctamente. */
    SUCCESS_AUTH_LOGOUT( "rest.success.auth.logout", "User successfully logged out" ),
    /** Mensaje de éxito que se utiliza cuando se han invalidado todos los token de acceso de un usuario correctamente. */
    SUCCESS_AUTH_INVALIDATE( "rest.success.auth.invalidate", "User successfully revoked all tokens" ),

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| IMAGE |------------------------------------------------------------------------------------------------------ \\
// ------------------------------------------------------------------------------------------------------------------ \\

    /** Mensaje de éxito que se utiliza cuando se crea una nueva {@link Image} correctamente. */
    SUCCESS_IMAGE_CREATE( "rest.success.image.create", "Image successfully created" ),

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| ACCOUNT |---------------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    /** Mensaje de éxito que se utiliza cuando no se encuentran {@link Account} en una consulta. */
    SUCCESS_ACCOUNT_NOT_DATA( "rest.success.account.query.not.data", "No accounts found" ),
    /** Mensaje de éxito que se utiliza cuando se recupera correctamente la lista de roles soportados de una {@link Account}. */
    SUCCESS_ACCOUNT_ROLE_SUPPORTED_LIST( "rest.success.account.role.supported.list", "List of supported Account roles successfully retrieved" ),
    /** Mensaje de éxito que se utiliza cuando se crea correctamente una {@link Account}. */
    SUCCESS_ACCOUNT_CREATE( "rest.success.account.create", "Account with ID \"%s\" successfully created" ),
    /** Mensaje de éxito que se utiliza cuando se actualiza correctamente una {@link Account} mediante su {@link AccountId}. */
    SUCCESS_ACCOUNT_UPDATE_BY_ID( "rest.success.account.update.by.id", "Account with ID \"%s\" successfully updated" ),
    /** Mensaje de éxito que se utiliza cuando se actualiza correctamente la {@link Account} del usuario autenticado. */
    SUCCESS_ACCOUNT_UPDATE( "rest.success.account.update", "Account successfully updated" ),
    /** Mensaje de éxito que se utiliza cuando se actualiza correctamente el idioma de una {@link Account}. */
    SUCCESS_ACCOUNT_UPDATE_LANG( "rest.success.account.update.lang", "Account with ID \"%s\" successfully updated language to \"%s\"" ),
    /** Mensaje de éxito que se utiliza cuando se actualiza correctamente el correo electrónico de una {@link Account}. */
    SUCCESS_ACCOUNT_UPDATE_EMAIL( "rest.success.account.update.email", "Account with ID \"%s\" successfully updated email to \"%s\"" ),
    /** Mensaje de éxito que se utiliza cuando se actualiza correctamente la contraseña de una {@link Account}. */
    SUCCESS_ACCOUNT_UPDATE_PASSWORD( "rest.success.account.update.password", "Account with ID \"%s\" successfully updated password" ),
    /** Mensaje de éxito que se utiliza cuando se elimina correctamente una {@link Account} mediante su {@link AccountId}. */
    SUCCESS_ACCOUNT_DELETE_BY_ID( "rest.success.account.delete.by.id", "Account with ID \"%s\" successfully deleted" ),
    /** Mensaje de éxito que se utiliza cuando se elimina correctamente la {@link Account} del usuario autenticado. */
    SUCCESS_ACCOUNT_DELETE( "rest.success.account.delete", "Account successfully deleted" ),

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| USER |------------------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    /** Mensaje de éxito que se utiliza cuando no se encuentran {@link User} en una consulta. */
    SUCCESS_USER_NOT_DATA( "rest.success.user.query.not.data", "No users found" ),
    /** Mensaje de éxito que se utiliza cuando se actualiza correctamente una {@link User} mediante su {@link UserId}. */
    SUCCESS_USER_UPDATE_BY_ID( "rest.success.user.update.by.id", "User with ID \"%s\" successfully updated" ),
    /** Mensaje de éxito que se utiliza cuando se actualiza correctamente la {@link User} del usuario autenticado. */
    SUCCESS_USER_UPDATE( "rest.success.user.update", "User successfully updated" );

// ------------------------------------------------------------------------------------------------------------------ \\

    /** La clave i18n del mensaje de error */
    private final String key;

    /** El mensaje de error que se puede mostrar en el log */
    private final String loggerMessage;

// ------------------------------------------------------------------------------------------------------------------ \\

}
