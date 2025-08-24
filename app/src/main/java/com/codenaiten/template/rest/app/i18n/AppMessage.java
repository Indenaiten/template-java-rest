package com.codenaiten.template.rest.app.i18n;

import com.codenaiten.template.rest.app.entity.Account;
import com.codenaiten.template.rest.app.entity.User;
import com.codenaiten.template.rest.app.policy.UserMinimumAgePolicy;
import com.codenaiten.template.rest.app.vo.Email;
import com.codenaiten.template.rest.app.vo.Timestamp;
import com.codenaiten.template.rest.app.vo.ValueObject;
import com.codenaiten.template.rest.app.vo.account.AccountId;
import com.codenaiten.template.rest.app.vo.account.AccountPassword;
import com.codenaiten.template.rest.app.vo.user.UserId;
import com.codenaiten.template.rest.app.vo.user.UserName;
import com.codenaiten.template.rest.app.vo.user.UserSurname;
import com.codenaiten.template.rest.app.vo.user.UserUsername;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Getter
@RequiredArgsConstructor
public enum AppMessage implements MessageI18n{

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| GENERIC |---------------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    /** Mensaje de error que se utiliza cuando ocurre un error inesperado en el sistema */
    ERROR_GENERIC( "app.error.generic", "An unexpected error has occurred" ),
    /** Mensaje de error que se utiliza cuando no se encuentra un elemento en el sistema */
    ERROR_NOT_FOUND( "app.error.generic.not.found", "Not found error has occurred" ),

    // Entity - Account
    /** Mensaje de error que se utiliza cuando no se encuentra una {@link Account} en el sistema a partir de su identificador {@link AccountId} */
    ERROR_ACCOUNT_NOT_FOUND_BY_ID( "app.error.account.not.found.by.id", "Account not found by ID: %s" ),

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| SECURITY |--------------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    /** Mensaje de error que se utiliza cuando ocurre un error de seguridad inesperado en el sistema */
    ERROR_SECURITY_GENERIC( "app.error.security.generic", "An unexpected security error has occurred" ),
    /** Mensaje de error que se utiliza cuando se deniega el acceso a un recurso */
    ERROR_SECURITY_ACCESS_DENIED( "app.error.security.access.denied", "Access denied"),

    // Authentication
    /** Mensaje de error que se utiliza cuando no se encuentra información de la autenticación en el contexto de seguridad */
    ERROR_SECURITY_AUTH_NOT_FOUND( "app.error.security.auth.not.found", "Authentication information not found" ),
    /** Mensaje de error que se utiliza cuando las credenciales de autenticación son incorrectas */
    ERROR_SECURITY_AUTH_BAD_CREDENTIALS( "app.error.security.auth.bad.credentials", "Bad credentials: %s" ),
    /** Mesnaje de error que se utiliza cuando el Access Token es inválido */
    ERROR_SECURITY_AUTH_INVALID_TOKEN( "app.error.security.auth.token.invalid", "Token is invalid: %s" ),
    /** Mesnaje de error que se utiliza cuando el Access Token es inválido */
    ERROR_SECURITY_AUTH_INVALID_ACCESS_TOKEN( "app.error.security.auth.token.access.invalid", "Access Token is invalid: %s" ),
    /** Mesnaje de error que se utiliza cuando el Refresh Token es inválido */
    ERROR_SECURITY_AUTH_INVALID_REFRESH_TOKEN( "app.error.security.auth.token.refresh.invalid", "Refresh Token is invalid: %s" ),

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| VALIDATION |------------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    /** Mensaje de error que se utiliza cuando ocurre un error de validación inesperado en el sistema */
    ERROR_VALIDATION_GENERIC( "app.error.validation.generic", "An unexpected validation error has occurred" ),

    // Entity - User
    /** Mensaje de error de validación que se utiliza cuando el rol de {@link User} es {@code null} */
    ERROR_VALIDATION_USER_ROLE_REQUIRED( "app.error.validation.user.role.required", "User Role is required" ),
    /** Mensaje de error de validación que se utiliza cuando el username de {@link User} es {@code null} */
    ERROR_VALIDATION_USER_USERNAME_REQUIRED( "app.error.validation.user.username.required", "User Username is required" ),
    /** Mensaje de error de validación que se utiliza cuando el nombre de {@link User} es {@code null} */
    ERROR_VALIDATION_USER_NAME_REQUIRED( "app.error.validation.user.name.required", "User Name is required" ),
    /** Mensaje de error de validación que se utiliza cuando la fecha de nacimiento de {@link User} es {@code null} */
    ERROR_VALIDATION_USER_BIRTHDATE_REQUIRED( "app.error.validation.user.birthdate.required", "User Birthdate is required" ),
    /** Mensaje de error de validación que se utiliza cuando el username de {@link User} ya existe en el sistema */
    ERROR_VALIDATION_USER_USERNAME_ALREADY_EXISTS( "app.error.validation.user.username.already.exists", "User Username already exists: %s" ),

    // Entity - Account
    /** Mensaje de error de validación que se utiliza cuando el owner de {@link Account} es {@code null} */
    ERROR_VALIDATION_ACCOUNT_OWNER_REQUIRED( "app.error.validation.account.owner.required", "Account Owner is required" ),
    /** Mensaje de error de validación que se utiliza cuando el email de {@link Account} es {@code null} */
    ERROR_VALIDATION_ACCOUNT_EMAIL_REQUIRED( "app.error.validation.account.email.required", "Account Email is required" ),
    /** Mensaje de error de validación que se utiliza cuando la contraseña de {@link Account} es {@code null} */
    ERROR_VALIDATION_ACCOUNT_PASSWORD_REQUIRED( "app.error.validation.account.password.required", "Account Password is required" ),
    /** Mensaje de error de validación que se utiliza cuando la constraseña de {@link Account} ya existe en el sistema */
    ERROR_VALIDATION_ACCOUNT_EMAIL_ALREADY_EXISTS( "app.error.validation.account.email.already.exists", "Account Email already exists: %s" ),

    // Policy - User
    /** Mensaje de error de validación que se utiliza cuando la edad de {@link User} es inferior a {@link UserMinimumAgePolicy#MINIMUM_AGE} */
    ERROR_VALIDATION_POLICY_USER_MIN_AGE( "app.error.validation.policy.user.min.age", "User must be at least %d years old: %s" ),

    // Value Object
    /** Mensaje de error de validación que se utiliza cuando el valor de un {@link ValueObject} es {@code null} */
    ERROR_VALIDATION_VO_VALUE_REQUIRED( "app.error.validation.vo.value.required", "The value is required" ),

    // Value Object - Timestamp
    /** Mensaje de error de validación que se utiliza cuando el valor de un {@link Timestamp} es futuro */
    ERROR_VALIDATION_VO_TIMESTAMP_VALUE_FUTURE( "app.error.validation.vo.timestamp.value.future", "The value cannot be in the future: %s" ),

    // Value Object - Email
    /** Mensaje de error de validación que se utiliza cuando el valor de un {@link Email} tiene una longitud inferior a {@link Email#MIN_SIZE} */
    ERROR_VALIDATION_VO_EMAIL_VALUE_MIN_SIZE( "app.error.validation.vo.email.value.min.size", "Email must be at least %d characters long: %s" ),
    /** Mensaje de error de validación que se utiliza cuando el valor de un {@link Email} tiene una longitud superior a {@link Email#MAX_SIZE} */
    ERROR_VALIDATION_VO_EMAIL_VALUE_MAX_SIZE( "app.error.validation.vo.email.value.max.size", "Email must be at most %d characters long: %s" ),
    /** Mensaje de error de validación que se utiliza cuando el valor de un {@link Email} no se corresponde con el formato {@link Email#FORMAT} */
    ERROR_VALIDATION_VO_EMAIL_VALUE_INVALID( "app.error.validation.vo.email.value.invalid", "Email must have the format '%s': %s" ),

    // Value Object - UserId
    /** Mensaje de error de validación que se utiliza cuando el valor de un {@link UserId} no se corresponde con el formato de un {@link UUID} */
    ERROR_VALIDATION_VO_USER_ID_VALUE_INVALID( "app.error.validation.vo.user-id.value.invalid", "Invalid UUID for User ID: %s" ),

    // Value Object - UserUsername
    /** Mensaje de error de validación que se utiliza cuando el valor de un {@link UserUsername} tiene una longitud inferior a {@link UserUsername#MIN_SIZE} */
    ERROR_VALIDATION_VO_USER_USERNAME_VALUE_MIN_SIZE( "app.error.validation.vo.user-username.value.min.size", "User Username must be at least %d characters long: %s" ),
    /** Mensaje de error de validación que se utiliza cuando el valor de un {@link UserUsername} tiene una longitud superior a {@link UserUsername#MAX_SIZE} */
    ERROR_VALIDATION_VO_USER_USERNAME_VALUE_MAX_SIZE( "app.error.validation.vo.user-username.value.max.size", "User Username be at most %d characters long: %s" ),
    /** Mensaje de error de validación que se utiliza cuando el valor de un {@link UserUsername} no se corresponde con el formato {@link UserUsername#FORMAT} */
    ERROR_VALIDATION_VO_USER_USERNAME_VALUE_INVALID( "app.error.validation.vo.user-username.value.invalid", "User Username must have the format '%s': %s" ),

    // Value Object - UserName
    /** Mensaje de error de validación que se utiliza cuando el valor de un {@link UserName} tiene una longitud inferior a {@link UserName#MIN_SIZE} */
    ERROR_VALIDATION_VO_USER_NAME_VALUE_MIN_SIZE( "app.error.validation.vo.user-name.value.min.size", "User Name must be at least %d characters long: %s" ),
    /** Mensaje de error de validación que se utiliza cuando el valor de un {@link UserName} tiene una longitud superior a {@link UserName#MAX_SIZE} */
    ERROR_VALIDATION_VO_USER_NAME_VALUE_MAX_SIZE( "app.error.validation.vo.user-name.value.max.size", "User Name be at most %d characters long: %s" ),
    /** Mensaje de error de validación que se utiliza cuando el valor de un {@link UserName} no se corresponde con el formato {@link UserName#FORMAT} */
    ERROR_VALIDATION_VO_USER_NAME_VALUE_INVALID( "app.error.validation.vo.user-name.value.invalid", "User Name must have the format '%s': %s" ),

    // Value Object - UserSurname
    /** Mensaje de error de validación que se utiliza cuando el valor de un {@link UserSurname} tiene una longitud inferior a {@link UserSurname#MIN_SIZE} */
    ERROR_VALIDATION_VO_USER_SURNAME_VALUE_MIN_SIZE( "app.error.validation.vo.user-surname.value.min.size", "User Surname must be at least %d characters long: %s" ),
    /** Mensaje de error de validación que se utiliza cuando el valor de un {@link UserSurname} tiene una longitud superior a {@link UserSurname#MAX_SIZE} */
    ERROR_VALIDATION_VO_USER_SURNAME_VALUE_MAX_SIZE( "app.error.validation.vo.user-surname.value.max.size", "User Surname be at most %d characters long: %s" ),
    /** Mensaje de error de validación que se utiliza cuando el valor de un {@link UserSurname} no se corresponde con el formato {@link UserSurname#FORMAT} */
    ERROR_VALIDATION_VO_USER_SURNAME_VALUE_INVALID( "app.error.validation.vo.user-surname.value.invalid", "User Surname must have the format '%s': %s" ),

    // Value Object - UserRole
    ERROR_VALIDATION_VO_USER_ROLE_VALUE_INVALID( "app.error.validation.vo.user-role.value.invalid", "Invalid User Role not is value from values %s: %d" ),

    // Value Object - AccountId
    /** Mensaje de error de validación que se utiliza cuando el valor de un {@link AccountId} no se corresponde con el formato de un {@link UUID} */
    ERROR_VALIDATION_VO_ACCOUNT_ID_VALUE_INVALID( "app.error.validation.vo.account-id.value.invalid", "Invalid UUID for Account ID: %s" ),

    // Value Object - AccountPassword
    /** Mensaje de error de validación que se utiliza cuando el valor de un {@link AccountPassword} tiene una longitud inferior a {@link AccountPassword#MIN_SIZE} */
    ERROR_VALIDATION_VO_ACCOUNT_PASSWORD_VALUE_MIN_SIZE( "app.error.validation.vo.account-password.value.min.size", "Account Password must be at least %d characters long: %s" ),
    /** Mensaje de error de validación que se utiliza cuando el valor de un {@link AccountPassword} tiene una longitud superior a {@link AccountPassword#MAX_SIZE} */
    ERROR_VALIDATION_VO_ACCOUNT_PASSWORD_VALUE_MAX_SIZE( "app.error.validation.vo.account-password.value.max.size", "Account Password be at most %d characters long: %s" ),
    /** Mensaje de error de validación que se utiliza cuando el valor de un {@link AccountPassword} no se corresponde con el formato {@link AccountPassword#FORMAT} */
    ERROR_VALIDATION_VO_ACCOUNT_PASSWORD_VALUE_INVALID( "app.error.validation.vo.account-password.value.invalid", "Account Password must have the format '%s': %s" );

// ------------------------------------------------------------------------------------------------------------------ \\

    private final String key;
    private final String loggerMessage;

// ------------------------------------------------------------------------------------------------------------------ \\

}
