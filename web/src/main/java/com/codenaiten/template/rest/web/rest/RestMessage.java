package com.codenaiten.template.rest.web.rest;

import com.codenaiten.template.rest.app.i18n.MessageI18n;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RestMessage implements MessageI18n {

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| CONFIGURATION |---------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    SUCCESS_CONFIG_LANG( "rest.success.config.lang", "Language successfully set" ),
    SUCCESS_CONFIG_RETRIEVE_LANG_CONFIGURED( "rest.success.config.retrieve.lang.configured", "Language configured successfully retrieved" ),
    SUCCESS_CONFIG_LIST_LANG_SUPPORTED( "rest.success.config.list.lang.supported", "List of supported languages successfully retrieved" ),

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| AUTHENTICATION |--------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    SUCCESS_AUTH_REGISTER( "rest.success.auth.register", "User successfully registered" ),
    SUCCESS_AUTH_LOGIN( "rest.success.auth.login", "User successfully logged in" ),
    SUCCESS_AUTH_REFRESH_TOKEN( "rest.success.auth.refresh.token", "User successfully refreshed token" ),
    SUCCESS_AUTH_LOGOUT( "rest.success.auth.logout", "User successfully logged out" ),
    SUCCESS_AUTH_INVALIDATE( "rest.success.auth.invalidate", "User successfully revoked all tokens" ),

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| ACCOUNT |---------------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    SUCCESS_ACCOUNT_NOT_DATA( "rest.success.account.not.data", "No accounts found" ),
    SUCCESS_ACCOUNT_QUERY_NOT_DATA( "rest.success.account.query.not.data", "No accounts found for query \"%s\"" ),
    SUCCESS_ACCOUNT_ROLE_SUPPORTED_LIST( "rest.success.account.role.supported.list", "List of supported Account roles successfully retrieved" ),
    SUCCESS_ACCOUNT_CREATE( "rest.success.account.create", "Account with ID \"%s\" successfully created" ),
    SUCCESS_ACCOUNT_UPDATE_BY_ID( "rest.success.account.update.by.id", "Account with ID \"%s\" successfully updated" ),
    SUCCESS_ACCOUNT_UPDATE( "rest.success.account.update", "Account successfully updated" ),
    SUCCESS_ACCOUNT_UPDATE_LANG( "rest.success.account.update.lang", "Account with ID \"%s\" successfully updated language to \"%s\"" ),
    SUCCESS_ACCOUNT_UPDATE_EMAIL( "rest.success.account.update.email", "Account with ID \"%s\" successfully updated email to \"%s\"" ),
    SUCCESS_ACCOUNT_UPDATE_PASSWORD( "rest.success.account.update.password", "Account with ID \"%s\" successfully updated password" ),
    SUCCESS_ACCOUNT_DELETE_BY_ID( "rest.success.account.delete.by.id", "Account with ID \"%s\" successfully deleted" ),
    SUCCESS_ACCOUNT_DELETE( "rest.success.account.delete", "Account successfully deleted" ),

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| USER |------------------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    SUCCESS_USER_NOT_DATA( "rest.success.user.not.data", "No users found" ),
    SUCCESS_USER_QUERY_NOT_DATA( "rest.success.user.query.not.data", "No users found for query \"%s\"" ),
    SUCCESS_USER_UPDATE_BY_ID( "rest.success.user.update.by.id", "User with ID \"%s\" successfully updated" ),
    SUCCESS_USER_UPDATE( "rest.success.user.update", "User successfully updated" );

// ------------------------------------------------------------------------------------------------------------------ \\

    private final String key;
    private final String loggerMessage;

// ------------------------------------------------------------------------------------------------------------------ \\

}
