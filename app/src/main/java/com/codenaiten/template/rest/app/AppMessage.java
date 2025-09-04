package com.codenaiten.template.rest.app;

import com.codenaiten.template.rest.app.entity.Account;
import com.codenaiten.template.rest.app.entity.Image;
import com.codenaiten.template.rest.app.entity.User;
import com.codenaiten.template.rest.app.i18n.MessageI18n;
import com.codenaiten.template.rest.app.vo.BaseValueObject;
import com.codenaiten.template.rest.app.vo.Email;
import com.codenaiten.template.rest.app.vo.Timestamp;
import com.codenaiten.template.rest.app.vo.account.AccountId;
import com.codenaiten.template.rest.app.vo.account.AccountPassword;
import com.codenaiten.template.rest.app.vo.account.AccountRole;
import com.codenaiten.template.rest.app.vo.image.ImageContentType;
import com.codenaiten.template.rest.app.vo.image.ImageId;
import com.codenaiten.template.rest.app.vo.user.UserId;
import com.codenaiten.template.rest.app.vo.user.UserName;
import com.codenaiten.template.rest.app.vo.user.UserSurname;
import com.codenaiten.template.rest.app.vo.user.UserUsername;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

/**
 * Claves I18n para los mensajes del sistema
 *
 * @see MessageI18n
 */
@Getter
@RequiredArgsConstructor
public enum AppMessage implements MessageI18n {

    // Error
    /** Mensaje de error que se utiliza cuando ocurre un error inesperado en el sistema */
    ERROR_GENERIC( "app.error.generic", "An unexpected error has occurred" ),
    /** Mensaje de error que se utiliza cuando el valor del lenguaje no está soportado */
    ERROR_LANGUAGE_VALUE_INVALID( "app.error.lang.invalid", "Language \"%s\" is invalid" ),

    // Data Error
    /** Mensaje de error que se utiliza cuando ocurre un error inesperado en el sistema relacionado con los datos */
    ERROR_DATA_GENERIC( "app.error.data.generic", "An unexpected data error has occurred" ),
    /** Mensaje de error de datos que se utiliza cuando no se encuentra un elemento en el sistema */
    ERROR_DATA_NOT_FOUND( "app.error.data.not.found", "Not found error has occurred" ),

    // Security Error
    /** Mensaje de error que se utiliza cuando ocurre un error de seguridad inesperado en el sistema */
    ERROR_SECURITY_GENERIC( "app.error.security.generic", "An unexpected security error has occurred" ),
    /** Mensaje de error de seguridad que se utiliza cuando ocurre un error de acceso inesperado en el sistema */
    ERROR_SECURITY_ACCESS_DENIED( "app.error.security.access.denied", "Access denied"),
    /** Mensaje de error de seguridad que se utiliza cuando no se dispone de acceso de consulta sobre unos recursos */
    ERROR_SECURITY_QUERY_NOT_ALLOWED( "app.error.security.query.not.allowed", "Query operation not allowed" ),
    /** Mensaje de error de seguridad que se utiliza cuando no se dispone de acceso de lectura a un recurso */
    ERROR_SECURITY_READ_NOT_ALLOWED( "app.error.security.read.not.allowed", "Read operation not allowed" ),
    /** Mensaje de error de seguridad que se utiliza cuando no se dispone de acceso para crear a un recurso */
    ERROR_SECURITY_CREATE_NOT_ALLOWED( "app.error.security.create.not.allowed", "Create operation not allowed" ),
    /** Mensaje de error de seguridad que se utiliza cuando no se dispone de acceso para actualizar a un recurso */
    ERROR_SECURITY_WRITE_NOT_ALLOWED( "app.error.security.write.not.allowed", "Write operation not allowed" ),
    /** Mensaje de error de seguridad que se utiliza cuando no se dispone de acceso para eliminar a un recurso */
    ERROR_SECURITY_DELETE_NOT_ALLOWED( "app.error.security.delete.not.allowed", "Delete operation not allowed" ),

    // Validation Error
    /** Mensaje de error de validación que se utiliza cuando ocurre un error de validación inesperado en el sistema */
    ERROR_VALIDATION_GENERIC( "app.error.validation.generic", "An unexpected validation error has occurred" ),

    // Validation Error - ValueObject
    /** Mensaje de error de validación que se utiliza cuando el valor de un {@link BaseValueObject} es {@code null} */
    ERROR_VALIDATION_VO_VALUE_REQUIRED( "app.error.validation.vo.value.required", "The value for the Value Object is required" ),

    // Validation Error - Timestamp
    /** Mensaje de error de validación que se utiliza cuando el valor de un {@link Timestamp} es futuro */
    ERROR_VALIDATION_VO_TIMESTAMP_VALUE_FUTURE( "app.error.validation.vo.timestamp.value.future", "The value for the Timestamp is in the future: %s" ),

    // Validation Error - Email
    /** Mensaje de error de validación que se utiliza cuando el valor de un {@link Email} tiene una longitud inferior a {@link Email#MIN_SIZE} */
    ERROR_VALIDATION_VO_EMAIL_VALUE_MIN_SIZE( "app.error.validation.vo.email.value.min.size", "Email with value \"%s\" must be at least %d characters long" ),
    /** Mensaje de error de validación que se utiliza cuando el valor de un {@link Email} tiene una longitud superior a {@link Email#MAX_SIZE} */
    ERROR_VALIDATION_VO_EMAIL_VALUE_MAX_SIZE( "app.error.validation.vo.email.value.max.size", "Email with value \"%s\" must be at most %d characters long" ),
    /** Mensaje de error de validación que se utiliza cuando el valor de un {@link Email} no se corresponde con el formato {@link Email#FORMAT} */
    ERROR_VALIDATION_VO_EMAIL_VALUE_INVALID( "app.error.validation.vo.email.value.invalid", "Email with value \"%s\" must have the format \"%s\"" ),

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| AUTHENTICATION |--------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    // Error
    /** Mensaje de error de seguridad que se utiliza cuando el Token de autenticación es inválido */
    ERROR_SECURITY_AUTH_INVALID_TOKEN( "app.error.security.auth.token.invalid", "Authentication Token is invalid: %s" ),
    /** Mensaje de error de seguridad que se utiliza cuando el Access Token de autenticación es inválido */
    ERROR_SECURITY_AUTH_INVALID_ACCESS_TOKEN( "app.error.security.auth.token.access.invalid", "Authentication Access Token is invalid: %s" ),
    /** Mensaje de error de seguridad que se utiliza cuando el Refresh Token de autenticación es inválido */
    ERROR_SECURITY_AUTH_INVALID_REFRESH_TOKEN( "app.error.security.auth.token.refresh.invalid", "Authentication Refresh Token is invalid: %s" ),

    // Security Error
    /** Mensaje de error de seguridad que se utiliza cuando no se encuentra información de la autenticación en el contexto de seguridad */
    ERROR_SECURITY_AUTH_NOT_FOUND( "app.error.security.auth.not.found", "Authentication information not found" ),
    /** Mensaje de error de seguridad que se utiliza cuando las credenciales de autenticación son incorrectas */
    ERROR_SECURITY_AUTH_BAD_CREDENTIALS( "app.error.security.auth.bad.credentials", "Bad credentials" ),
    /** Mensaje de error de seguridad que se utiliza cuando la {@link AccountPassword} es incorrecta */
    ERROR_SECURITY_AUTH_PASSWORD_INCORRECT( "app.error.security.auth.password.incorrect", "The password account is incorrect" ),

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| IMAGE |------------------------------------------------------------------------------------------------------ \\
// ------------------------------------------------------------------------------------------------------------------ \\

    // Data Error
    /** Mensaje de error de datos que se utiliza cuando no se encuentra una {@link Image} en el sistema a partir de su identificador {@link ImageId} */
    ERROR_DATA_IMAGE_NOT_FOUND_BY_ID( "app.error.data.image.not.found.by.id", "Image not found by ID: %s" ),


    // Validation Error
    /** Mensaje de error de validación que se utiliza cuando el {@link User} propietario de la {@link Image} es {@code null} */
    ERROR_VALIDATION_IMAGE_OWNER_REQUIRED( "app.error.validation.image.owner.required", "Image Owner is required" ),
    /** Mensaje de error de validación que se utiliza cuando el {@link ImageContentType} de la {@link Image} es {@code null} */
    ERROR_VALIDATION_IMAGE_CONTENT_TYPE_REQUIRED( "app.error.validation.image.content-type.required", "Image Content Type is required" ),
    /** Mensaje de error de validación que se utiliza cuando el tamaño del contenido de la {@link Image} es {@code null} */
    ERROR_VALIDATION_IMAGE_SIZE_REQUIRED( "app.error.validation.image.size.required", "Image Content Size is required" ),

    // Validation Error - ImageId
    /** Mensaje de error de validación que se utiliza cuando el valor de un {@link ImageId} no se corresponde con el formato de un {@link UUID} */
    ERROR_VALIDATION_VO_IMAGE_ID_VALUE_INVALID( "app.error.validation.vo.image-id.value.invalid", "Value \"%s\" for ImageId is invalid UUID" ),

    // Validation Error - ImageContentType
    /** Mensaje de error de validación que se utiliza cuando el valor de un {@link ImageContentType} no se corresponde con un valor soportado. */
    ERROR_VALIDATION_VO_IMAGE_CONTENT_TYPE_VALUE_INVALID( "app.error.validation.vo.image-content-type.value.invalid", "Value \"%s\" for ImageContentType is not invalid or not supported" ),

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| ACCOUNT |---------------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    // Error
    /** Mensaje de error de validación que se utiliza cuando el {@link Email} de {@link Account} ya existe en el sistema */
    ERROR_ACCOUNT_EMAIL_ALREADY_EXISTS("app.error.account.email.already.exists", "Account Email already exists: %s" ),

    // Security Error
    /** Mensaje de error de seguridad que se utiliza cuando un {@link Account} no tiene acceso de consulta de las {@link Account} */
    ERROR_SECURITY_ACCOUNT_QUERY_NOT_ALLOWED( "app.error.security.account.query.not.allowed", "Query operation for Accounts not allowed: { AccountId: %s }" ),
    /** Mensaje de error de seguridad que se utiliza cuando un {@link Account} no tiene acceso de lectura a una {@link Account}*/
    ERROR_SECURITY_ACCOUNT_READ_NOT_ALLOWED( "app.error.security.account.read.not.allowed", "Read operation for Account ID \"%s\" not allowed: { AccountId: %s }" ),
    /** Mensaje de error de seguridad que se utiliza cuando un {@link Account} no tiene acceso de creación de {@link Account} */
    ERROR_SECURITY_ACCOUNT_CREATE_NOT_ALLOWED( "app.error.security.account.create.not.allowed", "Create operation for Accounts not allowed: { AccountId: %s }" ),
    /** Mensaje de error de seguridad que se utiliza cuando un {@link Account} no tiene acceso de escritura a una {@link Account} */
    ERROR_SECURITY_ACCOUNT_WRITE_NOT_ALLOWED( "app.error.security.account.write.not.allowed", "Write operation for Account ID \"%s\" not allowed: { AccountId: %s }" ),
    /** Mensaje de error de seguridad que se utiliza cuando un {@link Account} no tiene acceso de borrado a una {@link Account} */
    ERROR_SECURITY_ACCOUNT_DELETE_NOT_ALLOWED( "app.error.security.account.delete.not.allowed", "Delete operation for Account ID \"%s\" not allowed { AccountId: %s }" ),

    // Data Error
    /** Mensaje de error de datos que se utiliza cuando no se encuentra una {@link Account} en el sistema a partir de su identificador {@link AccountId} */
    ERROR_DATA_ACCOUNT_NOT_FOUND_BY_ID( "app.error.data.account.not.found.by.id", "Account not found by ID: %s" ),
    /** Mensaje de error de datos que se utiliza cuando no se encuentra una {@link Account} en el sistema a partir del identificador {@link UserId} propietario de la cuenta */
    ERROR_DATA_ACCOUNT_NOT_FOUND_BY_OWNER_ID( "app.error.data.account.not.found.by.owner.id", "Account not found by owner ID: %s" ),

    // Validation Error
    /** Mensaje de error de validación que se utiliza cuando el owner de {@link Account} es {@code null} */
    ERROR_VALIDATION_ACCOUNT_OWNER_REQUIRED( "app.error.validation.account.owner.required", "Account Owner is required" ),
    /** Mensaje de error de validación que se utiliza cuando el {@link AccountRole} es {@code null} */
    ERROR_VALIDATION_ACCOUNT_ROLE_REQUIRED("app.error.validation.account.role.required", "Account Role is required" ),
    /** Mensaje de error de validación que se utiliza cuando el {@link Email} de {@link Account} es {@code null} */
    ERROR_VALIDATION_ACCOUNT_EMAIL_REQUIRED( "app.error.validation.account.email.required", "Account Email is required" ),
    /** Mensaje de error de validación que se utiliza cuando la {@link AccountPassword} de {@link Account} es {@code null} */
    ERROR_VALIDATION_ACCOUNT_PASSWORD_REQUIRED( "app.error.validation.account.password.required", "Account Password is required" ),

    // Validation Error - AccountId
    /** Mensaje de error de validación que se utiliza cuando el valor de un {@link AccountId} no se corresponde con el formato de un {@link UUID} */
    ERROR_VALIDATION_VO_ACCOUNT_ID_VALUE_INVALID( "app.error.validation.vo.account-id.value.invalid", "Value \"%s\" for AccountId is invalid UUID" ),

    // Validation Error - AccountRole
    /** Mensaje de error de validación que se utiliza cuando el valor del Id de un {@link AccountRole} es inválido */
    ERROR_VALIDATION_VO_ACCOUNT_ROLE_VALUE_INVALID("app.error.validation.vo.account-role.value.invalid", "Account Role ID %s is invalid: { supported: %s }" ),

    // Validation Error - AccountPassword
    /** Mensaje de error de validación que se utiliza cuando el valor de un {@link AccountPassword} tiene una longitud inferior a {@link AccountPassword#MIN_SIZE} */
    ERROR_VALIDATION_VO_ACCOUNT_PASSWORD_VALUE_MIN_SIZE( "app.error.validation.vo.account-password.value.min.size", "Account Password with value \"%s\" must be at least %d characters long" ),
    /** Mensaje de error de validación que se utiliza cuando el valor de un {@link AccountPassword} tiene una longitud superior a {@link AccountPassword#MAX_SIZE} */
    ERROR_VALIDATION_VO_ACCOUNT_PASSWORD_VALUE_MAX_SIZE( "app.error.validation.vo.account-password.value.max.size", "Account Password with value \"%s\" must be at most %d characters long" ),
    /** Mensaje de error de validación que se utiliza cuando el valor de un {@link AccountPassword} no se corresponde con el formato {@link AccountPassword#FORMAT} */
    ERROR_VALIDATION_VO_ACCOUNT_PASSWORD_VALUE_INVALID( "app.error.validation.vo.account-password.value.invalid", "Account Password with value \"%s\" must have the format \"%s\"" ),

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| USER |------------------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    // Error
    /** Mensaje de error que se utiliza cuando el {@link UserUsername} de {@link User} ya existe en el sistema */
    ERROR_USER_USERNAME_ALREADY_EXISTS( "app.error.user.username.already.exists", "User Username already exists: %s" ),
    /** Mensaje de error  que se utiliza cuando la edad de {@link User} es inferior a la edad mínima permitida */
    ERROR_USER_MIN_AGE( "app.error.user.min.age", "User must be at least %d years old: %s" ),

    // Security Error
    /** Mensaje de error de seguridad que se utiliza cuando un {@link Account} no tiene acceso de consulta de los {@link User} */
    ERROR_SECURITY_USER_QUERY_NOT_ALLOWED( "app.error.security.user.query.not.allowed", "Query operation for Users not allowed: { AccountId: %s }" ),
    /** Mensaje de error de seguridad que se utiliza cuando un {@link Account} no tiene acceso de lectura a un {@link User} */
    ERROR_SECURITY_USER_READ_NOT_ALLOWED( "app.error.security.user.read.not.allowed", "Read operation for User ID \"%s\" not allowed: { AccountId: %s }" ),
    /** Mensaje de error de seguridad que se utiliza cuando un {@link Account} no tiene acceso de escritura a un {@link User} */
    ERROR_SECURITY_USER_WRITE_NOT_ALLOWED( "app.error.security.user.write.not.allowed", "Write operation for User ID \"%s\" not allowed: { AccountId: %s }" ),

    // Data Error
    /** Mensaje de error de datos que se utiliza cuando no se encuentra una {@link User} en el sistema a partir de su identificador {@link UserId} */
    ERROR_DATA_USER_NOT_FOUND_BY_ID( "app.error.data.user.not.found.by.id", "User not found by ID: %s" ),

    // Validation Error
    /** Mensaje de error de validación que se utiliza cuando el {@link UserUsername} de {@link User} es {@code null} */
    ERROR_VALIDATION_USER_USERNAME_REQUIRED( "app.error.validation.user.username.required", "User Username is required" ),
    /** Mensaje de error de validación que se utiliza cuando el {@link UserName} de {@link User} es {@code null} */
    ERROR_VALIDATION_USER_NAME_REQUIRED( "app.error.validation.user.name.required", "User Name is required" ),
    /** Mensaje de error de validación que se utiliza cuando la fecha de nacimiento de {@link User} es {@code null} */
    ERROR_VALIDATION_USER_BIRTHDATE_REQUIRED( "app.error.validation.user.birthdate.required", "User Birthdate is required" ),

    // Validation Error - UserId
    /** Mensaje de error de validación que se utiliza cuando el valor de un {@link UserId} no se corresponde con el formato de un {@link UUID} */
    ERROR_VALIDATION_VO_USER_ID_VALUE_INVALID( "app.error.validation.vo.user-id.value.invalid", "Value \"%s\" for UserId is invalid UUID" ),

    // Validation Error - UserUsername
    /** Mensaje de error de validación que se utiliza cuando el valor de un {@link UserUsername} tiene una longitud inferior a {@link UserUsername#MIN_SIZE} */
    ERROR_VALIDATION_VO_USER_USERNAME_VALUE_MIN_SIZE("app.error.validation.vo.user-username.value.min.size.regexp", "User Username with value \"%s\" must be at least %d characters long" ),
    /** Mensaje de error de validación que se utiliza cuando el valor de un {@link UserUsername} tiene una longitud superior a {@link UserUsername#MAX_SIZE} */
    ERROR_VALIDATION_VO_USER_USERNAME_VALUE_MAX_SIZE( "app.error.validation.vo.user-username.value.max.size", "User Username with value \"%s\" must be at most %d characters long" ),
    /** Mensaje de error de validación que se utiliza cuando el valor de un {@link UserUsername} no se corresponde con el formato {@link UserUsername#FORMAT} */
    ERROR_VALIDATION_VO_USER_USERNAME_VALUE_INVALID( "app.error.validation.vo.user-username.value.invalid", "User Username with value \"%s\" must have the format \"%s\"" ),

    // Validation Error - UserName
    /** Mensaje de error de validación que se utiliza cuando el valor de un {@link UserName} tiene una longitud inferior a {@link UserName#MIN_SIZE} */
    ERROR_VALIDATION_VO_USER_NAME_VALUE_MIN_SIZE( "app.error.validation.vo.user-name.value.min.size", "User Name with value \"%s\" must be at least %d characters long" ),
    /** Mensaje de error de validación que se utiliza cuando el valor de un {@link UserName} tiene una longitud superior a {@link UserName#MAX_SIZE} */
    ERROR_VALIDATION_VO_USER_NAME_VALUE_MAX_SIZE( "app.error.validation.vo.user-name.value.max.size", "User Name with value \"%s\" must be at most %d characters long" ),
    /** Mensaje de error de validación que se utiliza cuando el valor de un {@link UserName} no se corresponde con el formato {@link UserName#FORMAT} */
    ERROR_VALIDATION_VO_USER_NAME_VALUE_INVALID( "app.error.validation.vo.user-name.value.invalid", "User Name with value \"%s\" must have the format \"%s\"" ),

    // Validation Error - UserSurname
    /** Mensaje de error de validación que se utiliza cuando el valor de un {@link UserSurname} tiene una longitud inferior a {@link UserSurname#MIN_SIZE} */
    ERROR_VALIDATION_VO_USER_SURNAME_VALUE_MIN_SIZE( "app.error.validation.vo.user-surname.value.min.size", "User Surname with value \"%s\" must be at least %d characters long" ),
    /** Mensaje de error de validación que se utiliza cuando el valor de un {@link UserSurname} tiene una longitud superior a {@link UserSurname#MAX_SIZE} */
    ERROR_VALIDATION_VO_USER_SURNAME_VALUE_MAX_SIZE( "app.error.validation.vo.user-surname.value.max.size", "User Surname with value \"%s\" must be at most %d characters long" ),
    /** Mensaje de error de validación que se utiliza cuando el valor de un {@link UserSurname} no se corresponde con el formato {@link UserSurname#FORMAT} */
    ERROR_VALIDATION_VO_USER_SURNAME_VALUE_INVALID( "app.error.validation.vo.user-surname.value.invalid", "User Surname with value \"%s\" must have the format \"%s\"" );

// ------------------------------------------------------------------------------------------------------------------ \\

    /** La clave i18n del mensaje de error */
    private final String key;

    /** El mensaje de error que se puede mostrar en el log */
    private final String loggerMessage;

// ------------------------------------------------------------------------------------------------------------------ \\

}
