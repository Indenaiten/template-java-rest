package com.codenaiten.template.rest.web.rest.controller;

import com.codenaiten.template.rest.app.api.AccountService;
import com.codenaiten.template.rest.app.dto.command.PageCommand;
import com.codenaiten.template.rest.app.dto.command.account.AccountCreateCommand;
import com.codenaiten.template.rest.app.dto.command.account.AccountUpdateCommand;
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
import com.codenaiten.template.rest.web.rest.dto.request.*;
import com.codenaiten.template.rest.web.rest.dto.response.AccountInfoResponse;
import com.codenaiten.template.rest.web.rest.dto.response.AccountRoleInfoResponse;
import com.codenaiten.template.rest.web.rest.dto.response.PageResponse;
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

@Slf4j
@RestController
@AllArgsConstructor
public class AccountRestController implements AccountApiRest {

    private final AccountService accountService;
    private final AccountWebMapper accountMapper;
    private final MessageI18nManager messageI18nManager;
    private final LocaleResolver localeResolver;
    private final HttpServletRequest httpServletRequest;
    private final HttpServletResponse httpServletResponse;

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| IMPLEMENTED METHODS |---------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    @Override
    public ResponseEntity<ApiRestResponse<AccountInfoResponse>> me() {
        final AccountInfoResult result = this.accountService.me();
        final AccountInfoResponse response = this.accountMapper.toResponse( result );
        final ApiRestResponse<AccountInfoResponse> wrapper = ApiRestResponse.success().data( response );
        return ResponseEntity.status( HttpStatus.OK ).body( wrapper );
    }

    @Override
    public ResponseEntity<ApiRestResponse<AccountInfoResponse>> get( final AccountId id ){
        final AccountInfoResult result = this.accountService.get( id );
        final AccountInfoResponse response = this.accountMapper.toResponse( result );
        final ApiRestResponse<AccountInfoResponse> wrapper = ApiRestResponse.success().data( response );
        return ResponseEntity.status( HttpStatus.OK ).body( wrapper );
    }

    @Override
    public ResponseEntity<ApiRestResponse<PageResponse<AccountInfoResponse>>> getAll( final Integer page, final Integer size ){
        final PageCommand command = PageCommand.of( page, size );
        final PageResult<AccountInfoResult> result = this.accountService.getAll( command);
        final List<AccountInfoResponse> content = result.getContent().stream().map( this.accountMapper::toResponse ).toList();
        final PageResponse<AccountInfoResponse> response = new PageResponse<>( result.getPage(), result.getSize(), result.getTotal(), result.getTotalPages(), content );
        final String message = !content.isEmpty() ? null : this.messageI18nManager.getMessageAndLogger( RestMessage.SUCCESS_ACCOUNT_NOT_DATA, LogLevel.INFO );
        final ApiRestResponse<PageResponse<AccountInfoResponse>> wrapper = ApiRestResponse.success().message( message ).data( response );
        return ResponseEntity.status( HttpStatus.OK ).body( wrapper );
    }

    @Override
    public ResponseEntity<ApiRestResponse<PageResponse<AccountInfoResponse>>> search( final String search, final Integer page, final Integer size ){
        final PageCommand command = PageCommand.of( page, size );
        final PageResult<AccountInfoResult> result = this.accountService.search( search, command);
        final List<AccountInfoResponse> content = result.getContent().stream().map( this.accountMapper::toResponse ).toList();
        final PageResponse<AccountInfoResponse> response = new PageResponse<>( result.getPage(), result.getSize(), result.getTotal(), result.getTotalPages(), content );
        final String message = !content.isEmpty() ? null : this.messageI18nManager.getMessageAndLogger( RestMessage.SUCCESS_ACCOUNT_QUERY_NOT_DATA, LogLevel.INFO, search );
        final ApiRestResponse<PageResponse<AccountInfoResponse>> wrapper = ApiRestResponse.success().message( message ).data( response );
        return ResponseEntity.status( HttpStatus.OK ).body( wrapper );
    }

    @Override
    public ResponseEntity<ApiRestResponse<List<AccountRoleInfoResponse>>> getSupportedAccountRoles(){
        final List<AccountRole> result = this.accountService.getSupportedRoles();
        final List<AccountRoleInfoResponse> data = result.stream().map( AccountRoleInfoResponse::new ).toList();
        final String message = this.messageI18nManager.getMessageAndLogger( RestMessage.SUCCESS_ACCOUNT_ROLE_SUPPORTED_LIST, LogLevel.INFO );
        final ApiRestResponse<List<AccountRoleInfoResponse>> wrapper = ApiRestResponse.success().message( message ).data( data );
        return ResponseEntity.status( HttpStatus.OK ).body( wrapper );
    }

    @Override
    public ResponseEntity<ApiRestResponse<AccountInfoResponse>> create( final AccountCreateRequest request ){
        final AccountCreateCommand command = this.accountMapper.toCommand( request );
        final AccountInfoResult result = this.accountService.create( command );
        final AccountInfoResponse response = this.accountMapper.toResponse( result );
        final String message = this.messageI18nManager.getMessageAndLogger( RestMessage.SUCCESS_ACCOUNT_CREATE, LogLevel.INFO, result.getId() );
        final ApiRestResponse<AccountInfoResponse> wrapper = ApiRestResponse.success().message( message ).data( response );
        return ResponseEntity.status( HttpStatus.OK ).body( wrapper );
    }

    @Override
    public ResponseEntity<ApiRestResponse<AccountInfoResponse>> update( final AccountId id, final AccountUpdateRequest request ){
        final AccountUpdateCommand command = this.accountMapper.toCommand( request );
        final AccountInfoResult result = this.accountService.update( id, command );
        final AccountInfoResponse response = this.accountMapper.toResponse( result );
        final String message = this.messageI18nManager.getMessageAndLogger( RestMessage.SUCCESS_ACCOUNT_UPDATE, LogLevel.INFO, id );
        final ApiRestResponse<AccountInfoResponse> wrapper = ApiRestResponse.success().message( message ).data( response );
        return ResponseEntity.status( HttpStatus.OK ).body( wrapper );
    }

    @Override
    public ResponseEntity<ApiRestResponse<AccountInfoResponse>> updateLang( final Locale lang ){
        final AccountInfoResult result = this.accountService.updateLang( lang );
        final AccountInfoResponse response = this.accountMapper.toResponse( result );
        final String message = this.messageI18nManager.getMessageAndLogger( RestMessage.SUCCESS_ACCOUNT_UPDATE_LANG, LogLevel.INFO, result.getId(), lang );
        final ApiRestResponse<AccountInfoResponse> wrapper = ApiRestResponse.success().message( message ).data( response );
        this.localeResolver.setLocale(this.httpServletRequest, this.httpServletResponse, LocaleContextHolder.getLocale() );
        return ResponseEntity.status( HttpStatus.OK ).body( wrapper );
    }

    @Override
    public ResponseEntity<ApiRestResponse<AccountInfoResponse>> updateEmail( final AccountEmailUpdateRequest request ){
        final AccountPassword password = request.password();
        final Email email = request.newEmail();
        final AccountInfoResult result = this.accountService.updateEmail( password, email );
        final AccountInfoResponse response = this.accountMapper.toResponse( result );
        final String message = this.messageI18nManager.getMessageAndLogger( RestMessage.SUCCESS_ACCOUNT_UPDATE_EMAIL, LogLevel.INFO, result.getId(), email );
        final ApiRestResponse<AccountInfoResponse> wrapper = ApiRestResponse.success().message( message ).data( response );
        return ResponseEntity.status( HttpStatus.OK ).body( wrapper );
    }

    @Override
    public ResponseEntity<ApiRestResponse<AccountInfoResponse>> updatePassword( final AccountPasswordUpdateRequest request ){
        final AccountPassword password = request.password();
        final AccountPassword newPassword = request.newPassword();
        final AccountInfoResult result = this.accountService.updatePassword( password, newPassword );
        final AccountInfoResponse response = this.accountMapper.toResponse( result );
        final String message = this.messageI18nManager.getMessageAndLogger( RestMessage.SUCCESS_ACCOUNT_UPDATE_PASSWORD, LogLevel.INFO, result.getId() );
        final ApiRestResponse<AccountInfoResponse> wrapper = ApiRestResponse.success().message( message ).data( response );
        return ResponseEntity.status( HttpStatus.OK ).body( wrapper );
    }

    @Override
    public ResponseEntity<ApiRestResponse<AccountInfoResponse>> delete( final AccountId id ){
        final AccountInfoResult result = this.accountService.delete( id );
        final AccountInfoResponse response = this.accountMapper.toResponse( result );
        final String message = this.messageI18nManager.getMessageAndLogger( RestMessage.SUCCESS_ACCOUNT_DELETE_BY_ID, LogLevel.INFO, id );
        final ApiRestResponse<AccountInfoResponse> wrapper = ApiRestResponse.success().message( message ).data( response );
        return ResponseEntity.status( HttpStatus.OK ).body( wrapper );
    }

    @Override
    public ResponseEntity<ApiRestResponse<AccountInfoResponse>> delete( final AccountDeleteRequest request ){
        final AccountPassword password = request.password();
        final AccountInfoResult result = this.accountService.delete( password );
        final AccountInfoResponse response = this.accountMapper.toResponse( result );
        final String message = this.messageI18nManager.getMessageAndLogger( RestMessage.SUCCESS_ACCOUNT_DELETE, LogLevel.INFO );
        final ApiRestResponse<AccountInfoResponse> wrapper = ApiRestResponse.success().message( message ).data( response );
        return ResponseEntity.status( HttpStatus.OK ).body( wrapper );
    }

// ------------------------------------------------------------------------------------------------------------------ \\

}
