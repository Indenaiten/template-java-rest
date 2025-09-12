package com.codenaiten.template.rest.core.shared;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AppMessageKey {

//--------------------------------------------------------------------------------------------------------------------\\
//---| COMMON |-------------------------------------------------------------------------------------------------------\\
//--------------------------------------------------------------------------------------------------------------------\\

    // Errors
    ERROR_GENERIC( "error.generic", "Unexpected Error" ),

    // Validation Errors
    ERROR_VALIDATION_GENERIC(  "error.validation.generic", "Validation Error" ),

    ERROR_VALIDATION_ENTITY_ID_REQUIRED( "error.validation.entity.id.required", "Entity ID is required" ),
    ERROR_VALIDATION_ENTITY_CREATED_AT_REQUIRED( "error.validation.entity.created-at.required", "Entity Created At is required" ),
    ERROR_VALIDATION_ENTITY_UPDATED_AT_REQUIRED( "error.validation.entity.updated-at.required", "Entity Updated At is required" ),

    // Constraint Errors
    ERROR_CONSTRAINT_GENERIC(  "error.constraint.generic", "Constraint Violation Error" ),

//--------------------------------------------------------------------------------------------------------------------\\
//---| USER |---------------------------------------------------------------------------------------------------------\\
//--------------------------------------------------------------------------------------------------------------------\\

    // Errors
    ERROR_USER_NOT_FOUND( "error.user.not.found", "User not found" ),
    ERROR_USER_NOT_FOUND_BY_ID( "error.user.not.found.by.id", "User not found by ID: { \"user-id\": \"%s\" }" ),

    // Validation Errors
    ERROR_VALIDATION_USER_EMAIL_REQUIRED( "error.validation.user.email.required", "User Email is required" ),
    ERROR_VALIDATION_USER_USERNAME_REQUIRED( "error.validation.user.username.required", "User Username is required" ),
    ERROR_VALIDATION_USER_ROLE_REQUIRED( "error.validation.user.role.required", "User Role is required" ),
    ERROR_VALIDATION_USER_NAME_REQUIRED( "error.validation.user.name.required", "User Name is required" ),
    ERROR_VALIDATION_USER_BIRTHDATE_REQUIRED( "error.validation.user.birthdate.required", "User Birthdate is required" ),
    ERROR_VALIDATION_USER_PASSWORD_REQUIRED( "error.validation.user.password.required", "User Password is required" ),

    // Constraints Errors
    ERROR_CONSTRAINT_USER_ID_ALREADY_EXISTS( "error.constraint.user.id.already.exists", "User ID already exists: { \"user-id\": \"%s\" }" ),
    ERROR_CONSTRAINT_USER_IMAGE_NOT_FOUND( "error.constraint.user.image.not.found", "User Image not found: { \"media-id\": \"%s\" }" ),
    ERROR_CONSTRAINT_USER_IMAGE_OWNER_INVALID( "error.constraint.user.image.owner.invalid", "User Image owner invalid: { \"user-id\": \"%s\", \"media-id\": \"%s\" }" ),
    ERROR_CONSTRAINT_USER_IMAGE_CONTENT_TYPE_INVALID( "error.constraint.user.image.content-type.invalid", "User Image Content Type invalid: { \"media-id\": \"%s\", \"content-type\": \"%s\" }" ),
    ERROR_CONSTRAINT_USER_BIRTHDATE_MINIMUM_AGE( "error.constraint.user.birthdate.minimum.age", "User birthdate does not meet the minimum age requirement: { \"user-birthdate\": \"%s\", \"minimum-age\": %d }" ),
    ERROR_CONSTRAINT_USER_USERNAME_UNIQUENESS( "error.constraint.user.username.uniqueness", "User Username already exists: { \"user-username\": \"%s\" }" ),
    ERROR_CONSTRAINT_USER_EMAIL_UNIQUENESS( "error.constraint.user.email.uniqueness", "User Email already exists: { \"user-email\": \"%s\" }" ),

//--------------------------------------------------------------------------------------------------------------------\\
//---| ACCOUNT |------------------------------------------------------------------------------------------------------\\
//--------------------------------------------------------------------------------------------------------------------\\

    // Validation Errors
    ERROR_VALIDATION_ACCOUNT_OWNER_REQUIRED( "error.validation.account.owner.required", "Account Owner is required" ),

    // Constraints Errors
    ERROR_CONSTRAINT_ACCOUNT_ID_ALREADY_EXISTS( "error.constraint.account.id.already.exists", "Account ID already exists: { \"account-id\": \"%s\" }" ),
    ERROR_CONSTRAINT_ACCOUNT_OWNER_NOT_FOUND( "error.constraint.account.owner.not.found", "Account Owner not found: { \"user-id\": \"%s\" }" ),
    ERROR_CONSTRAINT_ACCOUNT_OWNER_INVALID( "error.constraint.account.owner.invalid", "User already has an Account: { \"user-id\": \"%s\" }" ),

//--------------------------------------------------------------------------------------------------------------------\\
//---| MEDIA |--------------------------------------------------------------------------------------------------------\\
//--------------------------------------------------------------------------------------------------------------------\\

    // Validation Errors
    ERROR_VALIDATION_MEDIA_OWNER_REQUIRED( "error.validation.media.owner.required", "Media Owner is required" ),
    ERROR_VALIDATION_MEDIA_CONTENT_TYPE_REQUIRED( "error.validation.media.content-type.required", "Media Content Type is required" ),
    ERROR_VALIDATION_MEDIA_CONTENT_SIZE_REQUIRED( "error.validation.media.content-size.required", "Media Content Size is required" ),

    // Constraints Errors
    ERROR_CONSTRAINT_MEDIA_ID_ALREADY_EXISTS( "error.constraint.media.id.already.exists", "Media ID already exists: { \"media-id\": \"%s\" }" ),
    ERROR_CONSTRAINT_MEDIA_OWNER_NOT_FOUND( "error.constraint.media.owner.not.found", "Media Owner not found: { \"user-id\": \"%s\" }" );

//--------------------------------------------------------------------------------------------------------------------\\

    private final String key;
    private final String message;

//--------------------------------------------------------------------------------------------------------------------\\

}
