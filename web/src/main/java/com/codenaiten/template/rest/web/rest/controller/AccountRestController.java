package com.codenaiten.template.rest.web.rest.controller;

import com.codenaiten.template.rest.app.api.AccountService;
import com.codenaiten.template.rest.app.dto.command.PageableCommand;
import com.codenaiten.template.rest.app.dto.command.account.CreateAccountCommand;
import com.codenaiten.template.rest.app.dto.command.account.FilterAccountCommand;
import com.codenaiten.template.rest.app.dto.command.account.UpdateAccountCommand;
import com.codenaiten.template.rest.app.dto.result.AccountInfoResult;
import com.codenaiten.template.rest.app.dto.result.PageResult;
import com.codenaiten.template.rest.app.i18n.MessageI18nManager;
import com.codenaiten.template.rest.app.vo.Email;
import com.codenaiten.template.rest.app.vo.account.AccountId;
import com.codenaiten.template.rest.app.vo.account.AccountPassword;
import com.codenaiten.template.rest.app.vo.account.AccountRole;
import com.codenaiten.template.rest.web.rest.RestMessage;
import com.codenaiten.template.rest.web.rest.api.AccountApiRest;
import com.codenaiten.template.rest.web.rest.dto.ApiRestResponse;
import com.codenaiten.template.rest.web.rest.dto.request.account.*;
import com.codenaiten.template.rest.web.rest.dto.response.AccountInfoResponse;
import com.codenaiten.template.rest.web.rest.dto.response.AccountRoleInfoResponse;
import com.codenaiten.template.rest.web.rest.mapper.AccountWebMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.logging.LogLevel;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.LocaleResolver;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Slf4j
@RestController
@AllArgsConstructor
public class AccountRestController implements AccountApiRest {

    /** Manager de mensajes internacionalizados del sistema */
    private final MessageI18nManager messageI18nManager;

    /** Service de cuentas de usuario del sistema */
    private final AccountService accountService;

    /** Mapper del módulo Web de objetos relacionados con las cuentas de usuario */
    private final AccountWebMapper accountMapper;

    /** Resolver de idioma para la respuesta HTTP */
    private final LocaleResolver localeResolver;

    /** Petición HTTP actual */
    private final HttpServletRequest httpServletRequest;

    /** Respuesta HTTP actual */
    private final HttpServletResponse httpServletResponse;

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| IMPLEMENTED METHODS |---------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    @Override
    public ResponseEntity<ApiRestResponse<AccountInfoResponse>> me(){
        // Step 01: Run use case
        final AccountInfoResult result = this.accountService.me();

        // Step 02: Convert result to response
        final AccountInfoResponse response = this.accountMapper.toResponse( result );

        // Step 03: Build body with wrapper response
        final ApiRestResponse<AccountInfoResponse> wrapper = ApiRestResponse.success().build( response );

        // Step 04: Return response
        return ResponseEntity.status( HttpStatus.OK ).body( wrapper );
    }

// ------------------------------------------------------------------------------------------------------------------ \\

    @Override
    public ResponseEntity<ApiRestResponse<AccountInfoResponse>> get( final AccountId id ){
        // Step 01: Run use case
        final AccountInfoResult result = this.accountService.get( id );

        // Step 02: Convert result to response
        final AccountInfoResponse response = this.accountMapper.toResponse( result );

        // Step 03: Build body with wrapper response
        final ApiRestResponse<AccountInfoResponse> wrapper = ApiRestResponse.success().build( response );

        // Step 04: Return response
        return ResponseEntity.status( HttpStatus.OK ).body( wrapper );
    }

// ------------------------------------------------------------------------------------------------------------------ \\

    @Override
    public ResponseEntity<ApiRestResponse<List<AccountInfoResponse>>> search(
            final String search, final Integer page, final Integer size ){
        // Step 01: Create command
        final PageableCommand command = PageableCommand.of( page, size );

        // Step 02: Run use case
        final PageResult<AccountInfoResult> result = this.accountService.search( search, command );

        // Step 03: Convert result to response
        final List<AccountInfoResponse> content = result.getContent().stream().map( this.accountMapper::toResponse ).toList();

        // Step 04: Get i18n info message if result is empty
        final String message = !content.isEmpty() ? null : this.messageI18nManager.getMessageAndLogger( RestMessage.SUCCESS_ACCOUNT_NOT_DATA, LogLevel.INFO );

        // Step 05: Build body with wrapper response
        final ApiRestResponse<List<AccountInfoResponse>> wrapper = ApiRestResponse.success().message( message ).page( result ).build( content );

        // Step 06: Return response
        return ResponseEntity.status( HttpStatus.OK ).body( wrapper );
    }

// ------------------------------------------------------------------------------------------------------------------ \\

    @Override
    public ResponseEntity<ApiRestResponse<List<AccountInfoResponse>>> search(
            final FilterAccountRequest filter, final Integer page, final Integer size ){
        // Step 01: Create commands
        final PageableCommand pageableCommand = PageableCommand.of( page, size );
        final FilterAccountCommand filterCommand = Optional.ofNullable( filter ).map( this.accountMapper::toCommand ).orElse( null );

        // Step 02: Run use case
        final PageResult<AccountInfoResult> result = this.accountService.search( filterCommand, pageableCommand );

        // Step 03: Convert result to response
        final List<AccountInfoResponse> content = result.getContent().stream().map( this.accountMapper::toResponse ).toList();

        // Step 04: Get i18n info message if result is empty
        final String message = !content.isEmpty() ? null : this.messageI18nManager.getMessageAndLogger( RestMessage.SUCCESS_ACCOUNT_NOT_DATA, LogLevel.INFO );

        // Step 05: Build body with wrapper response
        final ApiRestResponse<List<AccountInfoResponse>> wrapper = ApiRestResponse.success().message( message ).page( result ).build( content );

        // Step 06: Return response
        return ResponseEntity.status( HttpStatus.OK ).body( wrapper );
    }

// ------------------------------------------------------------------------------------------------------------------ \\

    @Override
    public ResponseEntity<ApiRestResponse<List<AccountRoleInfoResponse>>> getSupportedAccountRoles(){
        // Step 01: Run use case
        final List<AccountRole> result = this.accountService.getSupportedRoles();

        // Step 02: Convert result to response
        final List<AccountRoleInfoResponse> data = result.stream().map( AccountRoleInfoResponse::new ).toList();

        // Step 03: Get i18n success message
        final String message = this.messageI18nManager.getMessageAndLogger( RestMessage.SUCCESS_ACCOUNT_ROLE_SUPPORTED_LIST, LogLevel.INFO );

        // Step 04: Build body with wrapper response
        final ApiRestResponse<List<AccountRoleInfoResponse>> wrapper = ApiRestResponse.success().message( message ).build( data );

        // Step 05: Return response
        return ResponseEntity.status( HttpStatus.OK ).body( wrapper );
    }

// ------------------------------------------------------------------------------------------------------------------ \\

    @Override
    public ResponseEntity<ApiRestResponse<AccountInfoResponse>> create( final CreateAccountRequest request ){
        // Step 01: Create command
        final CreateAccountCommand command = this.accountMapper.toCommand( request );

        // Step 02: Run use case
        final AccountInfoResult result = this.accountService.create( command );

        // Step 03: Convert result to response
        final AccountInfoResponse response = this.accountMapper.toResponse( result );

        // Step 04: Get i18n success message
        final String message = this.messageI18nManager.getMessageAndLogger( RestMessage.SUCCESS_ACCOUNT_CREATE, LogLevel.INFO, result.getId() );

        // Step 05: Build body with wrapper response
        final ApiRestResponse<AccountInfoResponse> wrapper = ApiRestResponse.success().message( message ).build( response );

        // Step 06: Return response
        return ResponseEntity.status( HttpStatus.OK ).body( wrapper );
    }

// ------------------------------------------------------------------------------------------------------------------ \\

    @Override
    public ResponseEntity<ApiRestResponse<AccountInfoResponse>> update( final AccountId id, final UpdateAccountRequest request ){
        // Step 01: Create command
        final UpdateAccountCommand command = this.accountMapper.toCommand( request );

        // Step 02: Run use case
        final AccountInfoResult result = this.accountService.update( id, command );

        // Step 03: Convert result to response
        final AccountInfoResponse response = this.accountMapper.toResponse( result );

        // Step 04: Get i18n success message
        final String message = this.messageI18nManager.getMessageAndLogger( RestMessage.SUCCESS_ACCOUNT_UPDATE, LogLevel.INFO, id );

        // Step 05: Build body with wrapper response
        final ApiRestResponse<AccountInfoResponse> wrapper = ApiRestResponse.success().message( message ).build( response );

        // Step 06: Return response
        return ResponseEntity.status( HttpStatus.OK ).body( wrapper );
    }

// ------------------------------------------------------------------------------------------------------------------ \\

    @Override
    public ResponseEntity<ApiRestResponse<AccountInfoResponse>> updateLang( final Locale lang ){
        // Step 01: Run use case
        final AccountInfoResult result = this.accountService.updateLang( lang );

        // Step 02: Convert result to response
        final AccountInfoResponse response = this.accountMapper.toResponse( result );

        // Step 03: Get i18n success message
        final String message = this.messageI18nManager.getMessageAndLogger( RestMessage.SUCCESS_ACCOUNT_UPDATE_LANG, LogLevel.INFO, result.getId(), lang );

        // Step 04: Build body with wrapper response
        final ApiRestResponse<AccountInfoResponse> wrapper = ApiRestResponse.success().message( message ).build( response );

        // Step 05: Resolve locale with current locale
        this.localeResolver.setLocale( this.httpServletRequest, this.httpServletResponse, LocaleContextHolder.getLocale() );

        // Step 06: Return response
        return ResponseEntity.status( HttpStatus.OK ).body( wrapper );
    }

// ------------------------------------------------------------------------------------------------------------------ \\

    @Override
    public ResponseEntity<ApiRestResponse<AccountInfoResponse>> updateEmail( final UpdateAccountEmailRequest request ){
        // Step 01: Get data from request
        final AccountPassword password = request.password();
        final Email email = request.newEmail();

        // Step 02: Run use case
        final AccountInfoResult result = this.accountService.updateEmail( password, email );

        // Step 03: Convert result to response
        final AccountInfoResponse response = this.accountMapper.toResponse( result );

        // Step 04: Get i18n success message
        final String message = this.messageI18nManager.getMessageAndLogger( RestMessage.SUCCESS_ACCOUNT_UPDATE_EMAIL, LogLevel.INFO, result.getId(), email );

        // Step 05: Build body with wrapper response
        final ApiRestResponse<AccountInfoResponse> wrapper = ApiRestResponse.success().message( message ).build( response );

        // Step 06: Return response
        return ResponseEntity.status( HttpStatus.OK ).body( wrapper );
    }

// ------------------------------------------------------------------------------------------------------------------ \\

    @Override
    public ResponseEntity<ApiRestResponse<AccountInfoResponse>> updatePassword( final UpdateAccountPasswordRequest request ){
        // Step 01: Get data from request
        final AccountPassword password = request.password();
        final AccountPassword newPassword = request.newPassword();

        // Step 02: Run use case
        final AccountInfoResult result = this.accountService.updatePassword( password, newPassword );

        // Step 03: Convert result to response
        final AccountInfoResponse response = this.accountMapper.toResponse( result );

        // Step 04: Get i18n success message
        final String message = this.messageI18nManager.getMessageAndLogger( RestMessage.SUCCESS_ACCOUNT_UPDATE_PASSWORD, LogLevel.INFO, result.getId() );

        // Step 05: Build body with wrapper response
        final ApiRestResponse<AccountInfoResponse> wrapper = ApiRestResponse.success().message( message ).build( response );

        // Step 06: Return response
        return ResponseEntity.status( HttpStatus.OK ).body( wrapper );
    }

// ------------------------------------------------------------------------------------------------------------------ \\

    @Override
    public ResponseEntity<ApiRestResponse<AccountInfoResponse>> delete( final AccountId id ){
        // Step 01: Run use case
        final AccountInfoResult result = this.accountService.delete( id );

        // Step 02: Convert result to response
        final AccountInfoResponse response = this.accountMapper.toResponse( result );

        // Step 03: Get i18n success message
        final String message = this.messageI18nManager.getMessageAndLogger( RestMessage.SUCCESS_ACCOUNT_DELETE_BY_ID, LogLevel.INFO, id );

        // Step 04: Build body with wrapper response
        final ApiRestResponse<AccountInfoResponse> wrapper = ApiRestResponse.success().message( message ).build( response );

        // Step 05: Return response
        return ResponseEntity.status( HttpStatus.OK ).body( wrapper );
    }

// ------------------------------------------------------------------------------------------------------------------ \\

    @Override
    public ResponseEntity<ApiRestResponse<AccountInfoResponse>> delete( final DeleteAccountRequest request ){
        // Step 01: Get data from request
        final AccountPassword password = request.password();

        // Step 02: Run use case
        final AccountInfoResult result = this.accountService.delete( password );

        // Step 03: Convert result to response
        final AccountInfoResponse response = this.accountMapper.toResponse( result );

        // Step 04: Get i18n success message
        final String message = this.messageI18nManager.getMessageAndLogger( RestMessage.SUCCESS_ACCOUNT_DELETE, LogLevel.INFO );

        // Step 05: Build body with wrapper response
        final ApiRestResponse<AccountInfoResponse> wrapper = ApiRestResponse.success().message( message ).build( response );

        // Step 06: Return response
        return ResponseEntity.status( HttpStatus.OK ).body( wrapper );
    }

// ------------------------------------------------------------------------------------------------------------------ \\

}
