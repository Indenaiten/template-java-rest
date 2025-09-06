package com.codenaiten.template.rest.web.rest.controller;

import com.codenaiten.template.rest.app.old.api.AccountService;
import com.codenaiten.template.rest.app.old.dto.command.PageCommand;
import com.codenaiten.template.rest.app.old.dto.command.account.CreateAccountCommand;
import com.codenaiten.template.rest.app.old.dto.command.account.FilterAccountCommand;
import com.codenaiten.template.rest.app.old.dto.command.account.UpdateAccountCommand;
import com.codenaiten.template.rest.app.old.dto.result.AccountInfoResult;
import com.codenaiten.template.rest.app.old.dto.result.PageResult;
import com.codenaiten.template.rest.app.old.entity.Account;
import com.codenaiten.template.rest.app.old.i18n.MessageI18nManager;
import com.codenaiten.template.rest.app.old.vo.Email;
import com.codenaiten.template.rest.app.old.vo.account.AccountId;
import com.codenaiten.template.rest.app.old.vo.account.AccountPassword;
import com.codenaiten.template.rest.web.rest.RestMessage;
import com.codenaiten.template.rest.web.rest.api.AccountApiRest;
import com.codenaiten.template.rest.web.rest.dto.ApiRestResponse;
import com.codenaiten.template.rest.web.rest.dto.request.account.*;
import com.codenaiten.template.rest.web.rest.dto.response.AccountInfoResponse;
import com.codenaiten.template.rest.web.rest.dto.response.AccountRoleInfoResponse;
import com.codenaiten.template.rest.web.rest.mapper.CommandMapper;
import com.codenaiten.template.rest.web.rest.mapper.ResponseMapper;
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

@Slf4j
@RestController
@AllArgsConstructor
public class AccountRestController implements AccountApiRest {

    /** Manager de las operaciones relacionado con los mensajes internacionalizados del sistema */
    private final MessageI18nManager messageI18nManager;

    /** Service con los casos de uso relacionados con la entidad {@link Account} */
    private final AccountService accountService;

    /** Mapper para convertir DTO Request a DTO Command */
    private final CommandMapper commandMapper;

    /** Mapper para convertir DTO Result a DTO Response */
    private final ResponseMapper responseMapper;

    /** Resolver para establecer las header/cookies de idioma en la respuesta HTTP */
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
        // Step 01: Run Use Case
        final AccountInfoResult result = this.accountService.me();

        // Step 02: Convert Result to Response
        final AccountInfoResponse response = this.responseMapper.toResponse( result );

        // Step 03: Build Body with Wrapper Response
        final ApiRestResponse<AccountInfoResponse> wrapper = ApiRestResponse.success().build( response );

        // Step 04: Return Response Entity
        return ResponseEntity.status( HttpStatus.OK ).body( wrapper );
    }

// ------------------------------------------------------------------------------------------------------------------ \\

    @Override
    public ResponseEntity<ApiRestResponse<AccountInfoResponse>> get( final AccountId id ){
        // Step 01: Run Use Case
        final AccountInfoResult result = this.accountService.get( id );

        // Step 02: Convert Result to Response
        final AccountInfoResponse response = this.responseMapper.toResponse( result );

        // Step 03: Build Body with Wrapper Response
        final ApiRestResponse<AccountInfoResponse> wrapper = ApiRestResponse.success().build( response );

        // Step 04: Return Response Entity
        return ResponseEntity.status( HttpStatus.OK ).body( wrapper );
    }

// ------------------------------------------------------------------------------------------------------------------ \\

    @Override
    public ResponseEntity<ApiRestResponse<List<AccountInfoResponse>>> search(
            final String search, final Integer page, final Integer size ){
        // Step 01: Create Command
        final PageCommand command = new PageCommand( page, size );

        // Step 02: Run Use Case
        final PageResult<AccountInfoResult> result = this.accountService.search( search, command );

        // Step 03: Convert Result to Response
        final List<AccountInfoResponse> content = result.content().stream().map( this.responseMapper::toResponse ).toList();

        // Step 04: Get i18n Info Message if Result is Empty
        final String message = !content.isEmpty() ? null : this.messageI18nManager.getMessageAndLogger( RestMessage.SUCCESS_ACCOUNT_NOT_DATA, LogLevel.INFO );

        // Step 05: Build Body with Wrapper Response
        final ApiRestResponse<List<AccountInfoResponse>> wrapper = ApiRestResponse.success().message( message ).page( result ).build( content );

        // Step 06: Return Response Entity
        return ResponseEntity.status( HttpStatus.OK ).body( wrapper );
    }

// ------------------------------------------------------------------------------------------------------------------ \\

    @Override
    public ResponseEntity<ApiRestResponse<List<AccountInfoResponse>>> search(
            final FilterAccountRequest filter, final Integer page, final Integer size ){
        // Step 01: Create Command
        final PageCommand pageCommand = new PageCommand( page, size );
        final FilterAccountCommand filterCommand = this.commandMapper.toCommand( filter );

        // Step 02: Run Use Case
        final PageResult<AccountInfoResult> result = this.accountService.search( filterCommand, pageCommand );

        // Step 03: Convert Result to Response
        final List<AccountInfoResponse> content = result.content().stream().map( this.responseMapper::toResponse ).toList();

        // Step 04: Get i18n Info Message if Result is Empty
        final String message = !content.isEmpty() ? null : this.messageI18nManager.getMessageAndLogger( RestMessage.SUCCESS_ACCOUNT_NOT_DATA, LogLevel.INFO );

        // Step 05: Build Body with Wrapper Response
        final ApiRestResponse<List<AccountInfoResponse>> wrapper = ApiRestResponse.success().message( message ).page( result ).build( content );

        // Step 06: Return Response Entity
        return ResponseEntity.status( HttpStatus.OK ).body( wrapper );
    }

// ------------------------------------------------------------------------------------------------------------------ \\

    @Override
    public ResponseEntity<ApiRestResponse<List<AccountRoleInfoResponse>>> getSupportedAccountRoles(){
        // Step 01: Run Use Case
        final List<AccountRole> result = this.accountService.getSupportedRoles();

        // Step 02: Convert Result to Response
        final List<AccountRoleInfoResponse> data = result.stream().map( AccountRoleInfoResponse::of ).toList();

        // Step 03: Get i18n Success Message
        final String message = this.messageI18nManager.getMessageAndLogger( RestMessage.SUCCESS_ACCOUNT_ROLE_SUPPORTED_LIST, LogLevel.INFO );

        // Step 04: Build Body with Wrapper Response
        final ApiRestResponse<List<AccountRoleInfoResponse>> wrapper = ApiRestResponse.success().message( message ).build( data );

        // Step 05: Return Response Entity
        return ResponseEntity.status( HttpStatus.OK ).body( wrapper );
    }

// ------------------------------------------------------------------------------------------------------------------ \\

    @Override
    public ResponseEntity<ApiRestResponse<AccountInfoResponse>> create( final CreateAccountRequest request ){
        // Step 01: Create Command
        final CreateAccountCommand command = this.commandMapper.toCommand( request );

        // Step 02: Run Use Case
        final AccountInfoResult result = this.accountService.create( command );

        // Step 03: Convert Result to Response
        final AccountInfoResponse response = this.responseMapper.toResponse( result );

        // Step 04: Get i18n Success Message
        final String message = this.messageI18nManager.getMessageAndLogger( RestMessage.SUCCESS_ACCOUNT_CREATE, LogLevel.INFO, result.id() );

        // Step 05: Build Body with Wrapper Response
        final ApiRestResponse<AccountInfoResponse> wrapper = ApiRestResponse.success().message( message ).build( response );

        // Step 06: Return Response Entity
        return ResponseEntity.status( HttpStatus.OK ).body( wrapper );
    }

// ------------------------------------------------------------------------------------------------------------------ \\

    @Override
    public ResponseEntity<ApiRestResponse<AccountInfoResponse>> update(
            final AccountId id, final UpdateAccountRequest request ){
        // Step 01: Create Command
        final UpdateAccountCommand command = this.commandMapper.toCommand( request );

        // Step 02: Run Use Case
        final AccountInfoResult result = this.accountService.update( id, command );

        // Step 03: Convert Result to Response
        final AccountInfoResponse response = this.responseMapper.toResponse( result );

        // Step 04: Get i18n Success Message
        final String message = this.messageI18nManager.getMessageAndLogger( RestMessage.SUCCESS_ACCOUNT_UPDATE, LogLevel.INFO, id );

        // Step 05: Build Body with Wrapper Response
        final ApiRestResponse<AccountInfoResponse> wrapper = ApiRestResponse.success().message( message ).build( response );

        // Step 06: Return Response Entity
        return ResponseEntity.status( HttpStatus.OK ).body( wrapper );
    }

// ------------------------------------------------------------------------------------------------------------------ \\

    @Override
    public ResponseEntity<ApiRestResponse<AccountInfoResponse>> updateLang( final Locale lang ){
        // Step 01: Run Use Case
        final AccountInfoResult result = this.accountService.updateLang( lang );

        // Step 02: Convert Result to Response
        final AccountInfoResponse response = this.responseMapper.toResponse( result );

        // Step 03: Get i18n Success Message
        final String message = this.messageI18nManager.getMessageAndLogger( RestMessage.SUCCESS_ACCOUNT_UPDATE_LANG, LogLevel.INFO, result.id(), lang );

        // Step 04: Build Body with Wrapper Response
        final ApiRestResponse<AccountInfoResponse> wrapper = ApiRestResponse.success().message( message ).build( response );

        // Step 05: Resolve Locale with Current Locale
        this.localeResolver.setLocale( this.httpServletRequest, this.httpServletResponse, LocaleContextHolder.getLocale() );

        // Step 06: Return Response Entity
        return ResponseEntity.status( HttpStatus.OK ).body( wrapper );
    }

// ------------------------------------------------------------------------------------------------------------------ \\

    @Override
    public ResponseEntity<ApiRestResponse<AccountInfoResponse>> updateEmail( final UpdateAccountEmailRequest request ){
        // Step 01: Get data from request
        final AccountPassword password = request.password();
        final Email email = request.newEmail();

        // Step 02: Run Use Case
        final AccountInfoResult result = this.accountService.updateEmail( password, email );

        // Step 03: Convert Result to Response
        final AccountInfoResponse response = this.responseMapper.toResponse( result );

        // Step 04: Get i18n Success Message
        final String message = this.messageI18nManager.getMessageAndLogger( RestMessage.SUCCESS_ACCOUNT_UPDATE_EMAIL, LogLevel.INFO, result.id(), email );

        // Step 05: Build Body with Wrapper Response
        final ApiRestResponse<AccountInfoResponse> wrapper = ApiRestResponse.success().message( message ).build( response );

        // Step 06: Return Response Entity
        return ResponseEntity.status( HttpStatus.OK ).body( wrapper );
    }

// ------------------------------------------------------------------------------------------------------------------ \\

    @Override
    public ResponseEntity<ApiRestResponse<AccountInfoResponse>> updatePassword( final UpdateAccountPasswordRequest request ){
        // Step 01: Get data from request
        final AccountPassword password = request.password();
        final AccountPassword newPassword = request.newPassword();

        // Step 02: Run Use Case
        final AccountInfoResult result = this.accountService.updatePassword( password, newPassword );

        // Step 03: Convert Result to Response
        final AccountInfoResponse response = this.responseMapper.toResponse( result );

        // Step 04: Get i18n Success Message
        final String message = this.messageI18nManager.getMessageAndLogger( RestMessage.SUCCESS_ACCOUNT_UPDATE_PASSWORD, LogLevel.INFO, result.id() );

        // Step 05: Build Body with Wrapper Response
        final ApiRestResponse<AccountInfoResponse> wrapper = ApiRestResponse.success().message( message ).build( response );

        // Step 06: Return Response Entity
        return ResponseEntity.status( HttpStatus.OK ).body( wrapper );
    }

// ------------------------------------------------------------------------------------------------------------------ \\

    @Override
    public ResponseEntity<ApiRestResponse<AccountInfoResponse>> delete( final AccountId id ){
        // Step 01: Run Use Case
        final AccountInfoResult result = this.accountService.delete( id );

        // Step 02: Convert Result to Response
        final AccountInfoResponse response = this.responseMapper.toResponse( result );

        // Step 03: Get i18n Success Message
        final String message = this.messageI18nManager.getMessageAndLogger( RestMessage.SUCCESS_ACCOUNT_DELETE_BY_ID, LogLevel.INFO, id );

        // Step 04: Build Body with Wrapper Response
        final ApiRestResponse<AccountInfoResponse> wrapper = ApiRestResponse.success().message( message ).build( response );

        // Step 05: Return Response Entity
        return ResponseEntity.status( HttpStatus.OK ).body( wrapper );
    }

// ------------------------------------------------------------------------------------------------------------------ \\

    @Override
    public ResponseEntity<ApiRestResponse<AccountInfoResponse>> delete( final DeleteAccountRequest request ){
        // Step 01: Get data from request
        final AccountPassword password = request.password();

        // Step 02: Run Use Case
        final AccountInfoResult result = this.accountService.delete( password );

        // Step 03: Convert Result to Response
        final AccountInfoResponse response = this.responseMapper.toResponse( result );

        // Step 04: Get i18n Success Message
        final String message = this.messageI18nManager.getMessageAndLogger( RestMessage.SUCCESS_ACCOUNT_DELETE, LogLevel.INFO );

        // Step 05: Build Body with Wrapper Response
        final ApiRestResponse<AccountInfoResponse> wrapper = ApiRestResponse.success().message( message ).build( response );

        // Step 06: Return Response Entity
        return ResponseEntity.status( HttpStatus.OK ).body( wrapper );
    }

// ------------------------------------------------------------------------------------------------------------------ \\

}
