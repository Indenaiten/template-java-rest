package com.codenaiten.template.rest.core.shared;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AppMessageKey {

//--------------------------------------------------------------------------------------------------------------------\\
//---| COMMON |-------------------------------------------------------------------------------------------------------\\
//--------------------------------------------------------------------------------------------------------------------\\

    ERROR_GENERIC( "error.generic", "Unexpected Error" ),
    ERROR_VALIDATION_GENERIC(  "error.validation.generic", "Validation Error" ),
    ERROR_CONSTRAINT_GENERIC(  "error.constraint.generic", "Constraint Violation Error" ),

//--------------------------------------------------------------------------------------------------------------------\\
//---| USER |---------------------------------------------------------------------------------------------------------\\
//--------------------------------------------------------------------------------------------------------------------\\

    // Errors
    ERROR_USER_NOT_FOUND( "error.user.not.found", "User not found" ),
    ERROR_USER_NOT_FOUND_BY_ID( "error.user.not.found.by.id", "User not found by ID: { \"user-id\": \"%s\" }" ),

    // Constraints Errors
    ERROR_CONSTRAINT_USER_ID_ALREADY_EXISTS( "error.constraint.user.id.already.exists", "User ID already exists: { \"user-id\": \"%s\" }" ),
    ERROR_CONSTRAINT_USER_IMAGE_NOT_FOUND( "error.constraint.user.image.not.found", "User Image not found: { \"media-id\": \"%s\" }" ),
    ERROR_CONSTRAINT_USER_IMAGE_INVALID( "error.constraint.user.image.invalid", "User Image invalid: { \"media-id\": \"%s\", \"content-type\": \"%s\" }" ),
    ERROR_CONSTRAINT_USER_BIRTHDATE_MINIMUM_AGE( "error.constraint.user.birthdate.minimum.age", "User birthdate does not meet the minimum age requirement: { \"user-birthdate\": \"%s\", \"minimum-age\": %d }" ),
    ERROR_CONSTRAINT_USER_USERNAME_UNIQUENESS( "error.constraint.user.username.uniqueness", "User Username already exists: { \"user-username\": \"%s\" }" ),
    ERROR_CONSTRAINT_USER_EMAIL_UNIQUENESS( "error.constraint.user.email.uniqueness", "User Email already exists: { \"user-email\": \"%s\" }" ),

//--------------------------------------------------------------------------------------------------------------------\\
//---| USER |---------------------------------------------------------------------------------------------------------\\
//--------------------------------------------------------------------------------------------------------------------\\

    // Constraints Errors
    ERROR_CONSTRAINT_ACCOUNT_ID_ALREADY_EXISTS( "error.constraint.account.id.already.exists", "Account ID already exists: { \"account-id\": \"%s\" }" ),
    ERROR_CONSTRAINT_ACCOUNT_OWNER_NOT_FOUND( "error.constraint.account.owner.not.found", "Account Owner not found: { \"user-id\": \"%s\" }" ),
    ERROR_CONSTRAINT_ACCOUNT_OWNER_INVALID( "error.constraint.account.owner.invalid", "User already has an Account: { \"user-id\": \"%s\" }" );

//--------------------------------------------------------------------------------------------------------------------\\

    private final String key;
    private final String message;

//--------------------------------------------------------------------------------------------------------------------\\

}
