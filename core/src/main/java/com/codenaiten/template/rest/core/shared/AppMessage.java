package com.codenaiten.template.rest.core.shared;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AppMessage {

//--------------------------------------------------------------------------------------------------------------------\\
//---| COMMON |-------------------------------------------------------------------------------------------------------\\
//--------------------------------------------------------------------------------------------------------------------\\

    ERROR_GENERIC( "error.generic", "Unexpected Error" ),
    ERROR_VALIDATION_GENERIC(  "error.validation.generic", "Validation Error" ),
    ERROR_CONSTRAINT_GENERIC(  "error.constraint.generic", "Constraint Violation Error" ),

//--------------------------------------------------------------------------------------------------------------------\\
//---| USER |---------------------------------------------------------------------------------------------------------\\
//--------------------------------------------------------------------------------------------------------------------\\

    // Constraints Errors
    ERROR_CONSTRAINT_USER_MINIMUM_AGE( "error.constraint.user.age.minimum", "User age must be at least %d years old: { \"user-age\": %d }" ),
    ERROR_CONSTRAINT_USER_USERNAME_UNIQUENESS( "error.constraint.user.username.uniqueness", "User Username already exists: { \"user-username\": \"%s\" }" ),
    ERROR_CONSTRAINT_USER_EMAIL_UNIQUENESS( "error.constraint.user.email.uniqueness", "User Email already exists: { \"user-email\": \"%s\" }" );

//--------------------------------------------------------------------------------------------------------------------\\

    private final String key;
    private final String message;

//--------------------------------------------------------------------------------------------------------------------\\

}
