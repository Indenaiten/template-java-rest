package com.codenaiten.template.rest.web.rest.controller;

import com.codenaiten.template.rest.app.api.UserService;
import com.codenaiten.template.rest.app.dto.command.PageCommand;
import com.codenaiten.template.rest.app.dto.command.user.UserUpdateCommand;
import com.codenaiten.template.rest.app.dto.result.PageResult;
import com.codenaiten.template.rest.app.dto.result.UserInfoResult;
import com.codenaiten.template.rest.app.i18n.MessageI18nManager;
import com.codenaiten.template.rest.app.vo.user.UserId;
import com.codenaiten.template.rest.web.rest.RestMessage;
import com.codenaiten.template.rest.web.rest.api.UserApiRest;
import com.codenaiten.template.rest.web.rest.dto.ApiRestResponse;
import com.codenaiten.template.rest.web.rest.dto.request.UserUpdateRequest;
import com.codenaiten.template.rest.web.rest.dto.response.PageResponse;
import com.codenaiten.template.rest.web.rest.dto.response.UserInfoResponse;
import com.codenaiten.template.rest.web.rest.mapper.UserWebMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.logging.LogLevel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@AllArgsConstructor
public class UserRestController implements UserApiRest{

    private final UserService userService;
    private final UserWebMapper userMapper;
    private final MessageI18nManager messageI18nManager;

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| IMPLEMENTED METHODS |---------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    @Override
    public ResponseEntity<ApiRestResponse<UserInfoResponse>> me(){
        final UserInfoResult result = this.userService.me();
        final UserInfoResponse response = this.userMapper.toResponse( result );
        final ApiRestResponse<UserInfoResponse> wrapper = ApiRestResponse.success().data( response );
        return ResponseEntity.status( HttpStatus.OK ).body( wrapper );
    }

    @Override
    public ResponseEntity<ApiRestResponse<UserInfoResponse>> get( final UserId id ){
        final UserInfoResult result = this.userService.get( id );
        final UserInfoResponse response = this.userMapper.toResponse( result );
        final ApiRestResponse<UserInfoResponse> wrapper = ApiRestResponse.success().data( response );
        return ResponseEntity.status( HttpStatus.OK ).body( wrapper );
    }

    @Override
    public ResponseEntity<ApiRestResponse<PageResponse<UserInfoResponse>>> getAll( final Integer page, final Integer size ){
        final PageCommand command = PageCommand.of( page, size );
        final PageResult<UserInfoResult> result = this.userService.getAll( command);
        final List<UserInfoResponse> content = result.getContent().stream().map( this.userMapper::toResponse ).toList();
        final PageResponse<UserInfoResponse> response = new PageResponse<>( result.getPage(), result.getSize(), result.getTotal(), result.getTotalPages(), content );
        final String message = !content.isEmpty() ? null : this.messageI18nManager.getMessageAndLogger( RestMessage.SUCCESS_USER_NOT_DATA, LogLevel.INFO );
        final ApiRestResponse<PageResponse<UserInfoResponse>> wrapper = ApiRestResponse.success().message( message ).data( response );
        return ResponseEntity.status( HttpStatus.OK ).body( wrapper );
    }

    @Override
    public ResponseEntity<ApiRestResponse<PageResponse<UserInfoResponse>>> search( final String search, final Integer page, final Integer size ){
        final PageCommand command = PageCommand.of( page, size );
        final PageResult<UserInfoResult> result = this.userService.search( search, command);
        final List<UserInfoResponse> content = result.getContent().stream().map( this.userMapper::toResponse ).toList();
        final PageResponse<UserInfoResponse> response = new PageResponse<>( result.getPage(), result.getSize(), result.getTotal(), result.getTotalPages(), content );
        final String message = !content.isEmpty() ? null : this.messageI18nManager.getMessageAndLogger( RestMessage.SUCCESS_USER_QUERY_NOT_DATA, LogLevel.INFO, search );
        final ApiRestResponse<PageResponse<UserInfoResponse>> wrapper = ApiRestResponse.success().message( message ).data( response );
        return ResponseEntity.status( HttpStatus.OK ).body( wrapper );
    }

    @Override
    public ResponseEntity<ApiRestResponse<UserInfoResponse>> update( final UserId id, final UserUpdateRequest request ){
        final UserUpdateCommand command = this.userMapper.toCommand( request );
        final UserInfoResult result = this.userService.update( id, command );
        final UserInfoResponse response = this.userMapper.toResponse( result );
        final String message = this.messageI18nManager.getMessageAndLogger( RestMessage.SUCCESS_USER_UPDATE_BY_ID, LogLevel.INFO, id );
        final ApiRestResponse<UserInfoResponse> wrapper = ApiRestResponse.success().message( message ).data( response );
        return ResponseEntity.status( HttpStatus.OK ).body( wrapper );
    }

    @Override
    public ResponseEntity<ApiRestResponse<UserInfoResponse>> update( final UserUpdateRequest request ){
        final UserUpdateCommand command = this.userMapper.toCommand( request );
        final UserInfoResult result = this.userService.update( command );
        final UserInfoResponse response = this.userMapper.toResponse( result );
        final String message = this.messageI18nManager.getMessageAndLogger( RestMessage.SUCCESS_USER_UPDATE, LogLevel.INFO );
        final ApiRestResponse<UserInfoResponse> wrapper = ApiRestResponse.success().message( message ).data( response );
        return ResponseEntity.status( HttpStatus.OK ).body( wrapper );
    }

// ------------------------------------------------------------------------------------------------------------------ \\

}
