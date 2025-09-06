package com.codenaiten.template.rest.web.rest.controller;

import com.codenaiten.template.rest.app.old.api.UserService;
import com.codenaiten.template.rest.app.old.dto.command.PageCommand;
import com.codenaiten.template.rest.app.old.dto.command.user.FilterUserCommand;
import com.codenaiten.template.rest.app.old.dto.command.user.UpdateUserCommand;
import com.codenaiten.template.rest.app.old.dto.result.ImageContentResult;
import com.codenaiten.template.rest.app.old.dto.result.ImageInfoResult;
import com.codenaiten.template.rest.app.old.dto.result.PageResult;
import com.codenaiten.template.rest.app.old.dto.result.UserInfoResult;
import com.codenaiten.template.rest.app.old.entity.User;
import com.codenaiten.template.rest.app.old.i18n.MessageI18nManager;
import com.codenaiten.template.rest.app.old.vo.user.UserId;
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
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@AllArgsConstructor
public class UserRestController implements UserApiRest{

    /** Manager de las operaciones relacionado con los mensajes internacionalizados del sistema */
    private final MessageI18nManager messageI18nManager;

    /** Service con los casos de uso relacionados con la entidad {@link User} */
    private final UserService userService;

    /** Mapper para convertir DTO Request a DTO Command */
    private final CommandMapper commandMapper;

    /** Mapper para convertir DTO Result a DTO Response */
    private final ResponseMapper responseMapper;

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| IMPLEMENTED METHODS |---------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    @Override
    public ResponseEntity<ApiRestResponse<UserInfoResponse>> me(){
        // Step 01: Run Use Case
        final UserInfoResult result = this.userService.me();

        // Step 02: Convert Result to Response
        final UserInfoResponse response = this.responseMapper.toResponse( result );

        // Step 03: Build Body with Wrapper Response
        final ApiRestResponse<UserInfoResponse> wrapper = ApiRestResponse.success().build( response );

        // Step 04: Return Response Entity
        return ResponseEntity.status( HttpStatus.OK ).body( wrapper );
    }

// ------------------------------------------------------------------------------------------------------------------ \\

    @Override
    public ResponseEntity<ApiRestResponse<UserInfoResponse>> get( final UserId id ){
        // Step 01: Run Use Case
        final UserInfoResult result = this.userService.get( id );

        // Step 02: Convert Result to Response
        final UserInfoResponse response = this.responseMapper.toResponse( result );

        // Step 03: Build Body with Wrapper Response
        final ApiRestResponse<UserInfoResponse> wrapper = ApiRestResponse.success().build( response );

        // Step 04: Return Response Entity
        return ResponseEntity.status( HttpStatus.OK ).body( wrapper );
    }

// ------------------------------------------------------------------------------------------------------------------ \\

    @Override
    public ResponseEntity<ApiRestResponse<List<UserInfoResponse>>> search(
            final String search, final Integer page, final Integer size ){
        // Step 01: Create Command
        final PageCommand pageCommand = new PageCommand( page, size );

        // Step 02: Run Use Case
        final PageResult<UserInfoResult> result = this.userService.search( search, pageCommand );

        // Step 03: Convert Result to Response
        final List<UserInfoResponse> content = result.content().stream().map( this.responseMapper::toResponse ).toList();

        // Step 04: Get i18n Info Message if Result is Empty
        final String message = !content.isEmpty() ? null : this.messageI18nManager.getMessageAndLogger( RestMessage.SUCCESS_USER_NOT_DATA, LogLevel.INFO );

        // Step 05: Build Body with Wrapper Response
        final ApiRestResponse<List<UserInfoResponse>> wrapper = ApiRestResponse.success().message( message ).page( result ).build( content );

        // Step 06: Return Response Entity
        return ResponseEntity.status( HttpStatus.OK ).body( wrapper );
    }

// ------------------------------------------------------------------------------------------------------------------ \\

    @Override
    public ResponseEntity<ApiRestResponse<List<UserInfoResponse>>> search(
            final FilterUserRequest filter, final Integer page, final Integer size ){
        // Step 01: Create Command
        final PageCommand pageCommand = new PageCommand( page, size );
        final FilterUserCommand filterCommand = this.commandMapper.toCommand( filter );

        // Step 02: Run Use Case
        final PageResult<UserInfoResult> result = this.userService.search( filterCommand, pageCommand );

        // Step 03: Convert Result to Response
        final List<UserInfoResponse> content = result.content().stream().map( this.responseMapper::toResponse ).toList();

        // Step 04: Get i18n Info Message if Result is Empty
        final String message = !content.isEmpty() ? null : this.messageI18nManager.getMessageAndLogger( RestMessage.SUCCESS_USER_NOT_DATA, LogLevel.INFO );

        // Step 05: Build Body with Wrapper Response
        final ApiRestResponse<List<UserInfoResponse>> wrapper = ApiRestResponse.success().message( message ).page( result ).build( content );

        // Step 06: Return Response Entity
        return ResponseEntity.status( HttpStatus.OK ).body( wrapper );
    }

// ------------------------------------------------------------------------------------------------------------------ \\

    @Override
    public ResponseEntity<ApiRestResponse<UserInfoResponse>> update( final UserId id, final UpdateUserRequest request ){
        // Step 01: Create Command
        final UpdateUserCommand command = this.commandMapper.toCommand( request );

        // Step 02: Run Use Case
        final UserInfoResult result = this.userService.update( id, command );

        // Step 03: Convert Result to Response
        final UserInfoResponse response = this.responseMapper.toResponse( result );

        // Step 04: Get i18n info message
        final String message = this.messageI18nManager.getMessageAndLogger( RestMessage.SUCCESS_USER_UPDATE_BY_ID, LogLevel.INFO, id );

        // Step 05: Build Body with Wrapper Response
        final ApiRestResponse<UserInfoResponse> wrapper = ApiRestResponse.success().message( message ).build( response );

        // Step 06: Return Response Entity
        return ResponseEntity.status( HttpStatus.OK ).body( wrapper );
    }

// ------------------------------------------------------------------------------------------------------------------ \\

    @Override
    public ResponseEntity<ApiRestResponse<UserInfoResponse>> update( final UpdateUserRequest request ){
        // Step 01: Create Command
        final UpdateUserCommand command = this.commandMapper.toCommand( request );

        // Step 02: Run Use Case
        final UserInfoResult result = this.userService.update( command );

        // Step 03: Convert Result to Response
        final UserInfoResponse response = this.responseMapper.toResponse( result );

        // Step 04: Get i18n info message
        final String message = this.messageI18nManager.getMessageAndLogger( RestMessage.SUCCESS_USER_UPDATE, LogLevel.INFO );

        // Step 05: Build Body with Wrapper Response
        final ApiRestResponse<UserInfoResponse> wrapper = ApiRestResponse.success().message( message ).build( response );

        // Step 06: Return Response Entity
        return ResponseEntity.status( HttpStatus.OK ).body( wrapper );
    }

// ------------------------------------------------------------------------------------------------------------------ \\

    @Override
    public ResponseEntity<byte[]> viewImageProfileById( final UserId id ){
        // Step 01: Run Use Case
        final ImageContentResult result = this.userService.getImageProfile( id );

        // Step 02: Return Response Entity
        final ImageInfoResult info = result.info();
        return ResponseEntity.status( HttpStatus.OK )
                .contentType( MediaType.parseMediaType( info.contentType() ))
                .contentLength( info.size() )
                .body( result.bytes() );
    }

// ------------------------------------------------------------------------------------------------------------------ \\

    @Override
    public ResponseEntity<byte[]> viewMyImageProfile(){
        // Step 01: Run Use Case
        final ImageContentResult result = this.userService.getImageProfile();

        // Step 02: Return Response Entity
        final ImageInfoResult info = result.info();
        return ResponseEntity.status( HttpStatus.OK )
                .contentType( MediaType.parseMediaType( info.contentType() ))
                .contentLength( info.size() )
                .body( result.bytes() );
    }

// ------------------------------------------------------------------------------------------------------------------ \\

}
