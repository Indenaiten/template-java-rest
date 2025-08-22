package com.codenaiten.template.rest.web.rest.controller;

import com.codenaiten.template.rest.app.api.AuthenticationService;
import com.codenaiten.template.rest.app.authentication.TokenInfo;
import com.codenaiten.template.rest.app.dto.command.LoginCommand;
import com.codenaiten.template.rest.app.dto.command.RegisterCommand;
import com.codenaiten.template.rest.app.dto.result.AccountInfoResult;
import com.codenaiten.template.rest.app.dto.result.LoginResult;
import com.codenaiten.template.rest.app.properties.TokenProperties;
import com.codenaiten.template.rest.web.rest.api.AuthenticationApiRest;
import com.codenaiten.template.rest.web.rest.dto.request.LoginRequest;
import com.codenaiten.template.rest.web.rest.dto.request.RegisterRequest;
import com.codenaiten.template.rest.web.rest.dto.response.AccountInfoResponse;
import com.codenaiten.template.rest.web.rest.dto.response.LoginResponse;
import com.codenaiten.template.rest.web.rest.mapper.AccountWebMapper;
import com.codenaiten.template.rest.web.rest.mapper.AuthenticationWebMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@Slf4j
@RestController
@AllArgsConstructor
public class AuthenticationRestController implements AuthenticationApiRest {

    private final TokenProperties tokenProperties;
    private final AuthenticationService authenticationService;
    private final AuthenticationWebMapper authenticationMapper;
    private final AccountWebMapper accountMapper;

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| IMPLEMENTED METHODS |---------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    @Override
    public ResponseEntity<AccountInfoResponse> register( final RegisterRequest request ){
        final RegisterCommand command = this.authenticationMapper.toCommand( request );
        final AccountInfoResult result = this.authenticationService.register( command );
        final AccountInfoResponse response = this.accountMapper.toResponse( result );
        return ResponseEntity.status( HttpStatus.OK ).body( response );
    }

    @Override
    public ResponseEntity<LoginResponse> login( final LoginRequest request ){
        final LoginCommand command = this.authenticationMapper.toCommand( request );
        final LoginResult result = this.authenticationService.login( command );
        final LoginResponse response = this.authenticationMapper.toResponse( result );
        return this.response( response, result.tokenInfo() );
    }

    @Override
    public ResponseEntity<LoginResponse> refresh( final String token ){
        final LoginResult result = this.authenticationService.refresh( token );
        final LoginResponse response = this.authenticationMapper.toResponse( result );
        return this.response( response, result.tokenInfo() );
    }

    @Override
    public ResponseEntity<Void> logout(){
        this.authenticationService.logout();
        return this.response( null );
    }

    @Override
    public ResponseEntity<Void> invalidate() {
        this.authenticationService.invalidate();
        return this.response( null );
    }

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| HELPER METHODS |--------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    public <T> ResponseEntity<T> response( final T body, final TokenInfo info ){
        final ResponseEntity.BodyBuilder builder = ResponseEntity.status( HttpStatus.OK );
        final String accessTokenName = this.tokenProperties.getAccessTokenName().toLowerCase();
        final String refreshTokenName = this.tokenProperties.getRefreshTokenName().toLowerCase();
        String accessToken = Strings.EMPTY;
        Long accessTokenExpiresIn = 0L;
        String refreshToken = Strings.EMPTY;
        Long refreshTokenExpiresIn = 0L;

        if( Objects.nonNull( info )){
            accessToken = info.getToken();
            accessTokenExpiresIn = this.tokenProperties.getAccessTokenExpiration();
            refreshToken = info.getRefreshToken();
            refreshTokenExpiresIn = this.tokenProperties.getRefreshTokenExpiration();
            builder.header( accessTokenName, accessToken );
        }

        final ResponseCookie accessTokenCookie = ResponseCookie.from( accessTokenName, accessToken )
                .httpOnly( true ).secure( true ).path( "/" ).maxAge( accessTokenExpiresIn ).sameSite( "Strict" ).build();
        final ResponseCookie refreshTokenCookie = ResponseCookie.from( refreshTokenName, refreshToken )
                .httpOnly( true ).secure( true ).path( "/" ).maxAge( refreshTokenExpiresIn ).sameSite( "Strict" ).build();
        builder.header( HttpHeaders.SET_COOKIE, accessTokenCookie.toString() );
        builder.header( HttpHeaders.SET_COOKIE, refreshTokenCookie.toString() );

        if( Objects.nonNull( body )) return builder.body( body );
        else return builder.build();
    }

    public <T> ResponseEntity<T> response( final T body ){
        return response( body, null );
    }

// ------------------------------------------------------------------------------------------------------------------ \\

}
