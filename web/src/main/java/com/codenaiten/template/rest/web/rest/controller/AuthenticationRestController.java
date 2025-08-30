package com.codenaiten.template.rest.web.rest.controller;

import com.codenaiten.template.rest.app.api.AuthenticationService;
import com.codenaiten.template.rest.app.authentication.TokenInfo;
import com.codenaiten.template.rest.app.dto.command.auth.LoginCommand;
import com.codenaiten.template.rest.app.dto.command.auth.RegisterCommand;
import com.codenaiten.template.rest.app.dto.result.AccountInfoResult;
import com.codenaiten.template.rest.app.dto.result.LoginResult;
import com.codenaiten.template.rest.app.i18n.MessageI18nManager;
import com.codenaiten.template.rest.app.properties.TokenSecurityProperties;
import com.codenaiten.template.rest.web.rest.RestMessage;
import com.codenaiten.template.rest.web.rest.api.AuthenticationApiRest;
import com.codenaiten.template.rest.web.rest.dto.ApiRestResponse;
import com.codenaiten.template.rest.web.rest.dto.Empty;
import com.codenaiten.template.rest.web.rest.dto.request.LoginRequest;
import com.codenaiten.template.rest.web.rest.dto.request.RegisterRequest;
import com.codenaiten.template.rest.web.rest.dto.response.AccountInfoResponse;
import com.codenaiten.template.rest.web.rest.dto.response.LoginResponse;
import com.codenaiten.template.rest.web.rest.mapper.AccountWebMapper;
import com.codenaiten.template.rest.web.rest.mapper.AuthenticationWebMapper;
import com.codenaiten.template.rest.web.rest.util.HttpRequestUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.boot.logging.LogLevel;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.LocaleResolver;

import java.util.Objects;

@Slf4j
@RestController
@AllArgsConstructor
public class AuthenticationRestController implements AuthenticationApiRest {

    private final TokenSecurityProperties tokenSecurityProperties;
    private final AuthenticationService authenticationService;
    private final AuthenticationWebMapper authenticationMapper;
    private final AccountWebMapper accountMapper;
    private final MessageI18nManager messageI18nManager;
    private final LocaleResolver localeResolver;
    private final HttpServletRequest httpServletRequest;
    private final HttpServletResponse httpServletResponse;

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| IMPLEMENTED METHODS |---------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    @Override
    public ResponseEntity<ApiRestResponse<AccountInfoResponse>> register( final RegisterRequest request ){
        final RegisterCommand command = this.authenticationMapper.toCommand( request );
        final AccountInfoResult result = this.authenticationService.register( command );
        final AccountInfoResponse response = this.accountMapper.toResponse( result );
        final String message = this.messageI18nManager.getMessageAndLogger( RestMessage.SUCCESS_AUTH_REGISTER, LogLevel.INFO );
        final ApiRestResponse<AccountInfoResponse> wrapper = ApiRestResponse.success().message( message ).data( response );
        return ResponseEntity.status( HttpStatus.OK ).body( wrapper );
    }

    @Override
    public ResponseEntity<ApiRestResponse<LoginResponse>> login( final LoginRequest request ){
        final String ip = HttpRequestUtil.getClientIp( httpServletRequest ).orElse( null );
        final LoginCommand command = this.authenticationMapper.toCommand( request );
        final LoginResult result = this.authenticationService.login( command, ip );
        final LoginResponse response = this.authenticationMapper.toResponse( result );
        final String message = this.messageI18nManager.getMessageAndLogger( RestMessage.SUCCESS_AUTH_LOGIN, LogLevel.INFO );
        final ApiRestResponse<LoginResponse> wrapper = ApiRestResponse.success().message( message ).data( response );
        this.localeResolver.setLocale( this.httpServletRequest, this.httpServletResponse, LocaleContextHolder.getLocale() );
        return this.responseWithTokens( wrapper, result.getTokenInfo() );
    }

    @Override
    public ResponseEntity<ApiRestResponse<LoginResponse>> refresh( final String token ){
        final String ip = HttpRequestUtil.getClientIp( this.httpServletRequest ).orElse( null );
        final LoginResult result = this.authenticationService.refresh( token, ip );
        final LoginResponse response = this.authenticationMapper.toResponse( result );
        final String message = this.messageI18nManager.getMessageAndLogger( RestMessage.SUCCESS_AUTH_REFRESH_TOKEN, LogLevel.INFO );
        final ApiRestResponse<LoginResponse> wrapper = ApiRestResponse.success().message( message ).data( response );
        return this.responseWithTokens( wrapper, result.getTokenInfo() );
    }

    @Override
    public ResponseEntity<ApiRestResponse<Empty>> logout(){
        this.authenticationService.logout();
        final String message = this.messageI18nManager.getMessageAndLogger( RestMessage.SUCCESS_AUTH_LOGOUT, LogLevel.INFO );
        final ApiRestResponse<Empty> wrapper = ApiRestResponse.success().message( message ).build();
        return this.responseRemoveTokens( wrapper );
    }

    @Override
    public ResponseEntity<ApiRestResponse<Empty>> invalidate() {
        this.authenticationService.invalidate();
        final String message = this.messageI18nManager.getMessageAndLogger( RestMessage.SUCCESS_AUTH_INVALIDATE, LogLevel.INFO );
        final ApiRestResponse<Empty> wrapper = ApiRestResponse.success().message( message ).build();
        return this.responseRemoveTokens( wrapper );
    }

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| HELPER METHODS |--------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    private <T extends ApiRestResponse<?>> ResponseEntity<T> responseWithTokens( final T body, final TokenInfo info ){
        final ResponseEntity.BodyBuilder builder = ResponseEntity.status( HttpStatus.OK );
        final String accessTokenName = this.tokenSecurityProperties.getAccessTokenName().toLowerCase();
        final String refreshTokenName = this.tokenSecurityProperties.getRefreshTokenName().toLowerCase();
        String accessToken = Strings.EMPTY;
        Long accessTokenExpiresIn = 0L;
        String refreshToken = Strings.EMPTY;
        Long refreshTokenExpiresIn = 0L;

        if( Objects.nonNull( info )){
            accessToken = info.getToken();
            accessTokenExpiresIn = this.tokenSecurityProperties.getAccessTokenExpiration();
            refreshToken = info.getRefreshToken();
            refreshTokenExpiresIn = this.tokenSecurityProperties.getRefreshTokenExpiration();
            builder.header( accessTokenName, accessToken );
        }

        final ResponseCookie accessTokenCookie = ResponseCookie.from( accessTokenName, accessToken )
                .httpOnly( true ).secure( true ).path( "/" ).maxAge( accessTokenExpiresIn ).sameSite( "Lax" ).build();
        final ResponseCookie refreshTokenCookie = ResponseCookie.from( refreshTokenName, refreshToken )
                .httpOnly( true ).secure( true ).path( "/" ).maxAge( refreshTokenExpiresIn ).sameSite( "Lax" ).build();
        builder.header( HttpHeaders.SET_COOKIE, accessTokenCookie.toString() );
        builder.header( HttpHeaders.SET_COOKIE, refreshTokenCookie.toString() );

        if( Objects.nonNull( body )) return builder.body( body );
        else return builder.build();
    }

    private <T extends ApiRestResponse<?>> ResponseEntity<T> responseRemoveTokens( final T body ){
        return responseWithTokens( body, null );
    }

// ------------------------------------------------------------------------------------------------------------------ \\

}
