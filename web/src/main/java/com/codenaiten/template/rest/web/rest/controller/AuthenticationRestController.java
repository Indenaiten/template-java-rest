package com.codenaiten.template.rest.web.rest.controller;

import com.codenaiten.template.rest.app.api.AuthenticationService;
import com.codenaiten.template.rest.app.dto.command.LoginCommand;
import com.codenaiten.template.rest.app.dto.command.RegisterCommand;
import com.codenaiten.template.rest.app.dto.result.AccountInfoResult;
import com.codenaiten.template.rest.app.dto.result.LoginResult;
import com.codenaiten.template.rest.web.rest.api.AuthenticationApiRest;
import com.codenaiten.template.rest.web.rest.dto.request.LoginRequest;
import com.codenaiten.template.rest.web.rest.dto.request.RegisterRequest;
import com.codenaiten.template.rest.web.rest.dto.response.AccountInfoResponse;
import com.codenaiten.template.rest.web.rest.dto.response.LoginResponse;
import com.codenaiten.template.rest.web.rest.mapper.AccountWebMapper;
import com.codenaiten.template.rest.web.rest.mapper.AuthenticationWebMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@AllArgsConstructor
public class AuthenticationRestController implements AuthenticationApiRest {

    private final AuthenticationService authenticationService;
    private final AuthenticationWebMapper authenticationMapper;
    private final AccountWebMapper accountMapper;

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| IMPLEMENTED METHODS |---------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    @Override
    public ResponseEntity<LoginResponse> login( final LoginRequest request ){
        final LoginCommand command = this.authenticationMapper.toCommand( request );
        final LoginResult result = this.authenticationService.login( command );
        final LoginResponse response = this.authenticationMapper.toResponse( result );
        return ResponseEntity.status( HttpStatus.OK ).body( response );
    }

    @Override
    public ResponseEntity<AccountInfoResponse> register( final RegisterRequest request ){
        final RegisterCommand command = this.authenticationMapper.toCommand( request );
        final AccountInfoResult result = this.authenticationService.register( command );
        final AccountInfoResponse response = this.accountMapper.toResponse( result );
        return ResponseEntity.status( HttpStatus.OK ).body( response );
    }

// ------------------------------------------------------------------------------------------------------------------ \\

}
