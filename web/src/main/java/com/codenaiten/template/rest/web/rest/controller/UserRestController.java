package com.codenaiten.template.rest.web.rest.controller;

import com.codenaiten.template.rest.app.api.UserService;
import com.codenaiten.template.rest.app.dto.command.PageableCommand;
import com.codenaiten.template.rest.app.dto.command.user.FilterUserCommand;
import com.codenaiten.template.rest.app.dto.command.user.UpdateUserCommand;
import com.codenaiten.template.rest.app.dto.result.PageResult;
import com.codenaiten.template.rest.app.dto.result.UserInfoResult;
import com.codenaiten.template.rest.app.i18n.MessageI18nManager;
import com.codenaiten.template.rest.app.vo.user.UserId;
import com.codenaiten.template.rest.web.rest.RestMessage;
import com.codenaiten.template.rest.web.rest.api.UserApiRest;
import com.codenaiten.template.rest.web.rest.dto.ApiRestResponse;
import com.codenaiten.template.rest.web.rest.dto.request.user.FilterUserRequest;
import com.codenaiten.template.rest.web.rest.dto.request.user.UpdateUserRequest;
import com.codenaiten.template.rest.web.rest.dto.response.UserInfoResponse;
import com.codenaiten.template.rest.web.rest.mapper.CommandMapper;
import com.codenaiten.template.rest.web.rest.mapper.ResponseMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.logging.LogLevel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@RestController
@AllArgsConstructor
public class UserRestController implements UserApiRest{

    /** Manager de mensajes internacionalizados del sistema */
    private final MessageI18nManager messageI18nManager;

    /** Service de usuarios del sistema */
    private final UserService userService;

    /** Mapper de objetos relacionados con los DTO de Request/Command */
    private final CommandMapper commandMapper;

    /** Mapper de objetos relacionados con los DTO de Result/Response */
    private final ResponseMapper responseMapper;

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| IMPLEMENTED METHODS |---------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    @Override
    public ResponseEntity<ApiRestResponse<UserInfoResponse>> me(){
        // Step 01: Run use case
        final UserInfoResult result = this.userService.me();

        // Step 02: Convert result to response
        final UserInfoResponse response = this.responseMapper.toResponse( result );

        // Step 03: Build body with wrapper response
        final ApiRestResponse<UserInfoResponse> wrapper = ApiRestResponse.success().build( response );

        // Step 04: Return response
        return ResponseEntity.status( HttpStatus.OK ).body( wrapper );
    }

// ------------------------------------------------------------------------------------------------------------------ \\

    @Override
    public ResponseEntity<ApiRestResponse<UserInfoResponse>> get( final UserId id ){
        // Step 01: Run use case
        final UserInfoResult result = this.userService.get( id );

        // Step 02: Convert result to response
        final UserInfoResponse response = this.responseMapper.toResponse( result );

        // Step 03: Build body with wrapper response
        final ApiRestResponse<UserInfoResponse> wrapper = ApiRestResponse.success().build( response );

        // Step 04: Return response
        return ResponseEntity.status( HttpStatus.OK ).body( wrapper );
    }

// ------------------------------------------------------------------------------------------------------------------ \\

    @Override
    public ResponseEntity<ApiRestResponse<List<UserInfoResponse>>> search(
            final String search, final Integer page, final Integer size ){
        // Step 01: Create command
        final PageableCommand pageableCommand = PageableCommand.of( page, size );

        // Step 02: Run use case
        final PageResult<UserInfoResult> result = this.userService.search( search, pageableCommand );

        // Step 03: Convert result to response
        final List<UserInfoResponse> content = result.getContent().stream().map( this.responseMapper::toResponse ).toList();

        // Step 04: Get i18n info message if result is empty
        final String message = !content.isEmpty() ? null : this.messageI18nManager.getMessageAndLogger( RestMessage.SUCCESS_USER_NOT_DATA, LogLevel.INFO );

        // Step 05: Build body with wrapper response
        final ApiRestResponse<List<UserInfoResponse>> wrapper = ApiRestResponse.success().message( message ).page( result ).build( content );

        // Step 06: Return response
        return ResponseEntity.status( HttpStatus.OK ).body( wrapper );
    }

// ------------------------------------------------------------------------------------------------------------------ \\

    @Override
    public ResponseEntity<ApiRestResponse<List<UserInfoResponse>>> search(
            final FilterUserRequest filter, final Integer page, final Integer size ){
        // Step 01: Create command
        final PageableCommand pageableCommand = PageableCommand.of( page, size );
        final FilterUserCommand filterCommand = this.commandMapper.toCommand( filter );

        // Step 02: Run use case
        final PageResult<UserInfoResult> result = this.userService.search( filterCommand, pageableCommand );

        // Step 03: Convert result to response
        final List<UserInfoResponse> content = result.getContent().stream().map( this.responseMapper::toResponse ).toList();

        // Step 04: Get i18n info message if result is empty
        final String message = !content.isEmpty() ? null : this.messageI18nManager.getMessageAndLogger( RestMessage.SUCCESS_USER_NOT_DATA, LogLevel.INFO );

        // Step 05: Build body with wrapper response
        final ApiRestResponse<List<UserInfoResponse>> wrapper = ApiRestResponse.success().message( message ).page( result ).build( content );

        // Step 06: Return response
        return ResponseEntity.status( HttpStatus.OK ).body( wrapper );
    }

// ------------------------------------------------------------------------------------------------------------------ \\

    @Override
    public ResponseEntity<ApiRestResponse<UserInfoResponse>> update(
            final UserId id, final UpdateUserRequest request, final MultipartFile image ){
        // Step 01: Create command
        final UpdateUserCommand command = this.commandMapper.toCommand( request, image );

        // Step 02: Run use case
        final UserInfoResult result = this.userService.update( id, command );

        // Step 03: Convert result to response
        final UserInfoResponse response = this.responseMapper.toResponse( result );

        // Step 04: Get i18n info message
        final String message = this.messageI18nManager.getMessageAndLogger( RestMessage.SUCCESS_USER_UPDATE_BY_ID, LogLevel.INFO, id );

        // Step 05: Build body with wrapper response
        final ApiRestResponse<UserInfoResponse> wrapper = ApiRestResponse.success().message( message ).build( response );

        // Step 06: Return response
        return ResponseEntity.status( HttpStatus.OK ).body( wrapper );
    }

// ------------------------------------------------------------------------------------------------------------------ \\

    @Override
    public ResponseEntity<ApiRestResponse<UserInfoResponse>> update(
            final UpdateUserRequest request, final MultipartFile image ){
        // Step 01: Create command
        final UpdateUserCommand command = this.commandMapper.toCommand( request, image );

        // Step 02: Run use case
        final UserInfoResult result = this.userService.update( command );

        // Step 03: Convert result to response
        final UserInfoResponse response = this.responseMapper.toResponse( result );

        // Step 04: Get i18n info message
        final String message = this.messageI18nManager.getMessageAndLogger( RestMessage.SUCCESS_USER_UPDATE, LogLevel.INFO );

        // Step 05: Build body with wrapper response
        final ApiRestResponse<UserInfoResponse> wrapper = ApiRestResponse.success().message( message ).build( response );

        // Step 06: Return response
        return ResponseEntity.status( HttpStatus.OK ).body( wrapper );
    }

// ------------------------------------------------------------------------------------------------------------------ \\

}
