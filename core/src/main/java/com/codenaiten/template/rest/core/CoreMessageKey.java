package com.codenaiten.template.rest.core;

import com.codenaiten.template.rest.core.shared.i18n.MessageKey;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CoreMessageKey implements MessageKey {

//--------------------------------------------------------------------------------------------------------------------\\
//---| COMMON |-------------------------------------------------------------------------------------------------------\\
//--------------------------------------------------------------------------------------------------------------------\\

    // Errors
    ERROR_GENERIC( "error.generic", "Unexpected Error" ),

    // Validation Errors
    ERROR_VALIDATION_GENERIC(  "error.validation.generic", "Validation Error" ),

    // Fields Entity - Validation Errors
    ERROR_VALIDATION_ENTITY_ID_REQUIRED( "error.validation.entity.id.required", "Entity ID is required" ),
    ERROR_VALIDATION_ENTITY_CREATED_AT_REQUIRED( "error.validation.entity.created-at.required", "Entity Created At is required" ),
    ERROR_VALIDATION_ENTITY_UPDATED_AT_REQUIRED( "error.validation.entity.updated-at.required", "Entity Updated At is required" ),

    // Value Object - Validation Errors
    ERROR_VALIDATION_VALUE_OBJECT_VALUE_NULL( "error.validation.value-object.value.null", "Value Object value cannot be null" ),

    // Value Object Email - Validation Errors
    ERROR_VALIDATION_VO_EMAIL_MIN_SIZE( "error.validation.vo.email.min-size", "Email Value Object minimum size is %d: { \"value\": \"%s\" }" ),
    ERROR_VALIDATION_VO_EMAIL_MAX_SIZE( "error.validation.vo.email.max-size", "Email Value Object maximum size is %d: { \"value\": \"%s\" }" ),
    ERROR_VALIDATION_VO_EMAIL_FORMAT_INVALID( "error.validation.vo.email.format.invalid", "Email Value Object not matched with pattern %s: { \"value\": \"%s\" }" ),

    // Value Object EncodedPassword - Validation Errors
    ERROR_VALIDATION_VO_ENCODED_PASSWORD_EMPTY( "error.validation.vo.encoded-password.empty", "Encoded Password Value Object cannot be empty" ),

    // Value Object Language - Validation Errors
    ERROR_VALIDATION_VO_LANGUAGE_INVALID( "error.validation.vo.language.invalid", "Language invalid: { \"value\": \"%s\" }" ),
    ERROR_VALIDATION_VO_LANGUAGE_UNSUPPORTED( "error.validation.vo.language.unsupported", "Language not supported: { \"language\": \"%s\" }" ),

    // Value Object Timestamp - Validation Errors
    ERROR_VALIDATION_VO_TIMESTAMP_DATE_FUTURE( "error.validation.vo.timestamp.date.future", "Timestamp Value Object cannot be in the future: { \"value\": \"%s\" }" ),

    // Constraint Errors
    ERROR_CONSTRAINT_GENERIC(  "error.constraint.generic", "Constraint Violation Error" ),

//--------------------------------------------------------------------------------------------------------------------\\
//---| USER |---------------------------------------------------------------------------------------------------------\\
//--------------------------------------------------------------------------------------------------------------------\\

    // Errors
    ERROR_USER_NOT_FOUND( "error.user.not.found", "User not found" ),
    ERROR_USER_NOT_FOUND_BY_ID( "error.user.not.found.by.id", "User not found by ID: { \"user-id\": \"%s\" }" ),

    // Fields Entity User - Validation Errors
    ERROR_VALIDATION_USER_EMAIL_REQUIRED( "error.validation.user.email.required", "User Email is required" ),
    ERROR_VALIDATION_USER_USERNAME_REQUIRED( "error.validation.user.username.required", "User Username is required" ),
    ERROR_VALIDATION_USER_ROLE_REQUIRED( "error.validation.user.role.required", "User Role is required" ),
    ERROR_VALIDATION_USER_NAME_REQUIRED( "error.validation.user.name.required", "User Name is required" ),
    ERROR_VALIDATION_USER_BIRTHDATE_REQUIRED( "error.validation.user.birthdate.required", "User Birthdate is required" ),
    ERROR_VALIDATION_USER_PASSWORD_REQUIRED( "error.validation.user.password.required", "User Password is required" ),

    // Value Object UserId - Validation Errors
    ERROR_VALIDATION_USER_VO_USER_ID_INVALID( "error.validation.user.vo.user-id.invalid", "UserId Value Object is not a valid UUID: { \"value\": \"%s\" }" ),

    // Value Object UserRole - Validation Errors
    ERROR_VALIDATION_USER_VO_USER_ROLE_INVALID( "error.validation.user.vo.user-role.invalid", "UserRole Value Object invalid: { \"value\": \"%s\" }" ),

    // Value Object UserUsername - Validation Errors
    ERROR_VALIDATION_USER_VO_USER_USERNAME_MIN_SIZE( "error.validation.user.vo.user-username.min-size", "UserUsername Value Object minimum size is %d: { \"value\": \"%s\" }" ),
    ERROR_VALIDATION_USER_VO_USER_USERNAME_MAX_SIZE( "error.validation.user.vo.user-username.max-size", "UserUsername Value Object maximum size is %d: { \"value\": \"%s\" }" ),
    ERROR_VALIDATION_USER_VO_USER_USERNAME_FORMAT_INVALID( "error.validation.user.vo.user-username.format.invalid", "UserUsername Value Object not matched with pattern %s: { \"value\": \"%s\" }" ),

    // Value Object UserName - Validation Errors
    ERROR_VALIDATION_USER_VO_USER_NAME_MIN_SIZE( "error.validation.user.vo.user-name.min-size", "UserName Value Object minimum size is %d: { \"value\": \"%s\" }" ),
    ERROR_VALIDATION_USER_VO_USER_NAME_MAX_SIZE( "error.validation.user.vo.user-name.max-size", "UserName Value Object maximum size is %d: { \"value\": \"%s\" }" ),
    ERROR_VALIDATION_USER_VO_USER_NAME_FORMAT_INVALID( "error.validation.user.vo.user-name.format.invalid", "UserName Value Object not matched with pattern %s: { \"value\": \"%s\" }" ),

    // Value Object UserSurname - Validation Errors
    ERROR_VALIDATION_USER_VO_USER_SURNAME_MIN_SIZE( "error.validation.user.vo.user-surname.min-size", "UserSurname Value Object minimum size is %d: { \"value\": \"%s\" }" ),
    ERROR_VALIDATION_USER_VO_USER_SURNAME_MAX_SIZE( "error.validation.user.vo.user-surname.max-size", "UserSurname Value Object maximum size is %d: { \"value\": \"%s\" }" ),
    ERROR_VALIDATION_USER_VO_USER_SURNAME_FORMAT_INVALID( "error.validation.user.vo.user-surname.format.invalid", "UserSurname Value Object not matched with pattern %s: { \"value\": \"%s\" }" ),

    // Value Object UserPassword - Validation Errors
    ERROR_VALIDATION_USER_VO_USER_PASSWORD_MIN_SIZE( "error.validation.user.vo.user-password.min-size", "UserPassword Value Object minimum size is %d: { \"value\": \"%s\" }" ),
    ERROR_VALIDATION_USER_VO_USER_PASSWORD_MAX_SIZE( "error.validation.user.vo.user-password.max-size", "UserPassword Value Object maximum size is %d: { \"value\": \"%s\" }" ),
    ERROR_VALIDATION_USER_VO_USER_PASSWORD_FORMAT_INVALID( "error.validation.user.vo.user-password.format.invalid", "UserPassword Value Object not matched with pattern %s: { \"value\": \"%s\" }" ),

    // Field ID Entity User - Constraints Errors
    ERROR_CONSTRAINT_USER_ID_ALREADY_EXISTS( "error.constraint.user.id.already.exists", "User ID already exists: { \"user-id\": \"%s\" }" ),

    // Field Image Entity User - Constraints Errors
    ERROR_CONSTRAINT_USER_IMAGE_NOT_FOUND( "error.constraint.user.image.not.found", "User Image not found: { \"media-id\": \"%s\" }" ),
    ERROR_CONSTRAINT_USER_IMAGE_OWNER_INVALID( "error.constraint.user.image.owner.invalid", "User Image owner invalid: { \"user-id\": \"%s\", \"media-id\": \"%s\" }" ),
    ERROR_CONSTRAINT_USER_IMAGE_CONTENT_TYPE_INVALID( "error.constraint.user.image.content-type.invalid", "User Image Content Type invalid: { \"media-id\": \"%s\", \"content-type\": \"%s\" }" ),

    // Field Birthdate Entity User - Constraints Errors
    ERROR_CONSTRAINT_USER_BIRTHDATE_MINIMUM_AGE( "error.constraint.user.birthdate.minimum.age", "User birthdate does not meet the minimum age requirement: { \"user-birthdate\": \"%s\", \"minimum-age\": %d }" ),

    // Field Username Entity User - Constraints Errors
    ERROR_CONSTRAINT_USER_USERNAME_UNIQUENESS( "error.constraint.user.username.uniqueness", "User Username already exists: { \"user-username\": \"%s\" }" ),

    // Field Email Entity User - Constraints Errors
    ERROR_CONSTRAINT_USER_EMAIL_UNIQUENESS( "error.constraint.user.email.uniqueness", "User Email already exists: { \"user-email\": \"%s\" }" ),

//--------------------------------------------------------------------------------------------------------------------\\
//---| ACCOUNT |------------------------------------------------------------------------------------------------------\\
//--------------------------------------------------------------------------------------------------------------------\\

    // Errors
    ERROR_ACCOUNT_NOT_FOUND( "error.account.not.found", "Account not found" ),
    ERROR_ACCOUNT_NOT_FOUND_BY_ID( "error.account.not.found.by.id", "Account not found by ID: { \"account-id\": \"%s\" }" ),

    // Fields Entity Account - Validation Errors
    ERROR_VALIDATION_ACCOUNT_OWNER_REQUIRED( "error.validation.account.owner.required", "Account Owner is required" ),

    // Value Object AccountId - Validation Errors
    ERROR_VALIDATION_ACCOUNT_VO_ACCOUNT_ID_INVALID( "error.validation.account.vo.account-id.invalid", "AccountId Value Object is not a valid UUID: { \"value\": \"%s\" }" ),

    // Field ID Entity Account - Constraints Errors
    ERROR_CONSTRAINT_ACCOUNT_ID_ALREADY_EXISTS( "error.constraint.account.id.already.exists", "Account ID already exists: { \"account-id\": \"%s\" }" ),

    // Field Owner Entity Account - Constraints Errors
    ERROR_CONSTRAINT_ACCOUNT_OWNER_NOT_FOUND( "error.constraint.account.owner.not.found", "Account Owner not found: { \"user-id\": \"%s\" }" ),
    ERROR_CONSTRAINT_ACCOUNT_OWNER_INVALID( "error.constraint.account.owner.invalid", "User already has an Account: { \"user-id\": \"%s\" }" ),

//--------------------------------------------------------------------------------------------------------------------\\
//---| MEDIA |--------------------------------------------------------------------------------------------------------\\
//--------------------------------------------------------------------------------------------------------------------\\

    // Errors
    ERROR_MEDIA_NOT_FOUND( "error.media.not.found", "Media not found" ),
    ERROR_MEDIA_NOT_FOUND_BY_ID( "error.media.not.found.by.id", "Media not found by ID: { \"media-id\": \"%s\" }" ),

    // Fields Entity Media - Validation Errors
    ERROR_VALIDATION_MEDIA_OWNER_REQUIRED( "error.validation.media.owner.required", "Media Owner is required" ),
    ERROR_VALIDATION_MEDIA_CONTENT_TYPE_REQUIRED( "error.validation.media.content-type.required", "Media Content Type is required" ),
    ERROR_VALIDATION_MEDIA_CONTENT_SIZE_REQUIRED( "error.validation.media.content-size.required", "Media Content Size is required" ),

    // Value Object MediaId - Validation Errors
    ERROR_VALIDATION_MEDIA_VO_MEDIA_ID_INVALID( "error.validation.media.vo.media-id.invalid", "MediaId Value Object is not a valid UUID: { \"value\": \"%s\" }" ),

    // Value Object MediaContentType - Validation Errors
    ERROR_VALIDATION_MEDIA_VO_MEDIA_CONTENT_TYPE_INVALID( "error.validation.media.vo.media-content-type.invalid", "MediaContentType Value Object invalid: { \"value\": \"%s\" }" ),

    // Value Object MediaContentSize - Validation Errors
    ERROR_VALIDATION_MEDIA_VO_MEDIA_CONTENT_SIZE_INVALID( "error.validation.media.vo.media-content-size.invalid", "MediaContentSize Value Object must be greater than zero: { \"value\": %d }" ),

    // Field ID Entity Media - Constraints Errors
    ERROR_CONSTRAINT_MEDIA_ID_ALREADY_EXISTS( "error.constraint.media.id.already.exists", "Media ID already exists: { \"media-id\": \"%s\" }" ),

    // Field Owner Entity Media - Constraints Errors
    ERROR_CONSTRAINT_MEDIA_OWNER_NOT_FOUND( "error.constraint.media.owner.not.found", "Media Owner not found: { \"user-id\": \"%s\" }" );

//--------------------------------------------------------------------------------------------------------------------\\

    private final String key;
    private final String message;

//--------------------------------------------------------------------------------------------------------------------\\

}
