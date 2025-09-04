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
import com.codenaiten.template.rest.web.rest.dto.request.auth.LoginRequest;
import com.codenaiten.template.rest.web.rest.dto.request.auth.RegisterRequest;
import com.codenaiten.template.rest.web.rest.dto.response.AccountInfoResponse;
import com.codenaiten.template.rest.web.rest.dto.response.LoginResponse;
import com.codenaiten.template.rest.web.rest.mapper.CommandMapper;
import com.codenaiten.template.rest.web.rest.mapper.ResponseMapper;
import com.codenaiten.template.rest.web.rest.util.HttpRequestUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.boot.logging.LogLevel;
import org.springframework.boot.web.server.Cookie;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.LocaleResolver;

import java.util.Objects;

@Slf4j
@RestController
@AllArgsConstructor
public class AuthenticationRestController implements AuthenticationApiRest {

    /** Properties con información relacionada con los tokens de autenticación del sistema */
    private final TokenSecurityProperties tokenSecurityProperties;

    /** Manager de mensajes internacionalizados del sistema */
    private final MessageI18nManager messageI18nManager;

    /** Service de autenticación del sistema */
    private final AuthenticationService authenticationService;

    /** Mapper de objetos relacionados con los DTO de Request/Command */
    private final CommandMapper commandMapper;

    /** Mapper de objetos relacionados con los DTO de Result/Response */
    private final ResponseMapper responseMapper;

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
    @SneakyThrows
    public ResponseEntity<ApiRestResponse<AccountInfoResponse>> register( final RegisterRequest request, final MultipartFile image ){
        // Step 01: Create command
        final RegisterCommand command = this.commandMapper.toCommand( request, image );

        // Step 02: Run use case
        final AccountInfoResult result = this.authenticationService.register( command );

        // Step 03: Convert result to response
        final AccountInfoResponse response = this.responseMapper.toResponse( result );

        // Step 04: Get i18n success message
        final String message = this.messageI18nManager.getMessageAndLogger( RestMessage.SUCCESS_AUTH_REGISTER, LogLevel.INFO );

        // Step 05: Build body with wrapper response
        final ApiRestResponse<AccountInfoResponse> wrapper = ApiRestResponse.success().message( message ).build( response );

        // Step 06: Return response
        return ResponseEntity.status( HttpStatus.OK ).body( wrapper );
    }

// ------------------------------------------------------------------------------------------------------------------ \\

    @Override
    public ResponseEntity<ApiRestResponse<LoginResponse>> login( final LoginRequest request ){
        // Step 01: Get Data from request
        final String ip = HttpRequestUtil.getClientIp( this.httpServletRequest ).orElse( null );

        // Step 02: Create command
        final LoginCommand command = this.commandMapper.toCommand( request );

        // Step 03: Run use case
        final LoginResult result = this.authenticationService.login( command, ip );

        // Step 04: Convert result to response
        final LoginResponse response = this.responseMapper.toResponse( result );

        // Step 05: Get i18n success message
        final String message = this.messageI18nManager.getMessageAndLogger( RestMessage.SUCCESS_AUTH_LOGIN, LogLevel.INFO );

        // Step 06: Build body with wrapper response
        final ApiRestResponse<LoginResponse> wrapper = ApiRestResponse.success().message( message ).build( response );

        // Step 06: Resolve locale with current locale
        this.localeResolver.setLocale( this.httpServletRequest, this.httpServletResponse, LocaleContextHolder.getLocale() );

        // Step 07: Return response with authentication info in to cookies & headers
        return this.responseWithTokens( wrapper, result.getTokenInfo() );
    }

// ------------------------------------------------------------------------------------------------------------------ \\

    @Override
    public ResponseEntity<ApiRestResponse<LoginResponse>> refresh( final String token ){
        // Step 01: Get Data from request
        final String ip = HttpRequestUtil.getClientIp( this.httpServletRequest ).orElse( null );

        // Step 02: Run use case
        final LoginResult result = this.authenticationService.refresh( token, ip );

        // Step 03: Convert result to response
        final LoginResponse response = this.responseMapper.toResponse( result );

        // Step 04: Get i18n success message
        final String message = this.messageI18nManager.getMessageAndLogger( RestMessage.SUCCESS_AUTH_REFRESH_TOKEN, LogLevel.INFO );

        // Step 05: Build body with wrapper response
        final ApiRestResponse<LoginResponse> wrapper = ApiRestResponse.success().message( message ).build( response );

        // Step 06: Return response with authentication info in to cookies & headers
        return this.responseWithTokens( wrapper, result.getTokenInfo() );
    }

// ------------------------------------------------------------------------------------------------------------------ \\

    @Override
    public ResponseEntity<ApiRestResponse<Empty>> logout(){
        // Step 01: Run use case
        this.authenticationService.logout();

        // Step 02: Get i18n success message
        final String message = this.messageI18nManager.getMessageAndLogger( RestMessage.SUCCESS_AUTH_LOGOUT, LogLevel.INFO );

        // Step 03: Build body with wrapper response
        final ApiRestResponse<Empty> wrapper = ApiRestResponse.success().message( message ).build();

        // Step 04: Return response with remove authentication info from cookies
        return this.responseRemoveTokens( wrapper );
    }

// ------------------------------------------------------------------------------------------------------------------ \\

    @Override
    public ResponseEntity<ApiRestResponse<Empty>> invalidate(){
        // Step 01: Run use case
        this.authenticationService.invalidate();

        // Step 02: Get i18n success message
        final String message = this.messageI18nManager.getMessageAndLogger( RestMessage.SUCCESS_AUTH_INVALIDATE, LogLevel.INFO );

        // Step 03: Build body with wrapper response
        final ApiRestResponse<Empty> wrapper = ApiRestResponse.success().message( message ).build();

        // Step 04: Return response with remove authentication info from cookies
        return this.responseRemoveTokens( wrapper );
    }

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| HELPER METHODS |--------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    /**
     * Devuelve una respuesta HTTP con el body indicado y establece la información de los tokens de autenticación en los
     * headers y las cookies en la respuesta HTTP.
     *
     * @param body {@link T} como el body de la respuesta.
     * @param info {@link TokenInfo} donde se encuentra la información de los tokens de autenticación.
     *
     * @return {@link ResponseEntity} - La respuesta HTTP con el body y los tokens en headers y cookies.
     *
     * @param <T> Tipo del body de la respuesta que tiene que ser del tipo {@link ApiRestResponse}.
     */
    private <T extends ApiRestResponse<?>> ResponseEntity<T> responseWithTokens( final T body, final TokenInfo info ){
        // Step 01: Initialize response builder
        final ResponseEntity.BodyBuilder builder = ResponseEntity.status( HttpStatus.OK );

        // Step 02: Get tokens names to headers & cookies
        final String accessTokenName = this.tokenSecurityProperties.getAccessTokenName().toLowerCase();
        final String refreshTokenName = this.tokenSecurityProperties.getRefreshTokenName().toLowerCase();

        // Step 03: Initialize tokens info as empty
        String accessToken = Strings.EMPTY;
        Long accessTokenExpiresIn = 0L;
        String refreshToken = Strings.EMPTY;
        Long refreshTokenExpiresIn = 0L;

        // Step 04: Check if token info exists
        if( Objects.nonNull( info )){ //If token info exists
            // Set tokens info
            accessToken = info.getToken();
            accessTokenExpiresIn = this.tokenSecurityProperties.getAccessTokenExpiration();
            refreshToken = info.getRefreshToken();
            refreshTokenExpiresIn = this.tokenSecurityProperties.getRefreshTokenExpiration();

            // Set Access-Token in response HTTP as header
            builder.header( accessTokenName, accessToken );
        }

        // Step 05: Set Cookies properties
        final boolean httpOnly = true;
        final boolean secure = true;
        final String path = "/";
        final String sameSite = Cookie.SameSite.LAX.name();

        // Step 06: Create Cookies with tokens info
        final ResponseCookie accessTokenCookie = ResponseCookie.from( accessTokenName, accessToken )
                .httpOnly( httpOnly ).secure( secure ).path( path ).maxAge( accessTokenExpiresIn ).sameSite( sameSite ).build();
        final ResponseCookie refreshTokenCookie = ResponseCookie.from( refreshTokenName, refreshToken )
                .httpOnly( httpOnly ).secure( secure ).path( path ).maxAge( refreshTokenExpiresIn ).sameSite( sameSite ).build();

        // Step 07: Set tokens info in response HTTP as cookies
        builder.header( HttpHeaders.SET_COOKIE, accessTokenCookie.toString() );
        builder.header( HttpHeaders.SET_COOKIE, refreshTokenCookie.toString() );

        // Step 08: Return response HTTP with body or empty response if body exists
        if( Objects.nonNull( body )) return builder.body( body );
        else return builder.build();
    }

    /**
     * Devuelve una respuesta HTTP con el body indicado y establece las cookies con la información de los tokens de
     * autenticación vacías u caducadas en la respuesta HTTP para forzar su eliminación en el cliente donde se
     * establecieron.
     *
     * @param body @param body {@link T} como el body de la respuesta.
     *
     * @return {@link ResponseEntity} - La respuesta HTTP con el body y las cookies de autenticación caducadas y sin
     * información.
     *
     * @param <T> Tipo del body de la respuesta que tiene que ser del tipo {@link ApiRestResponse}.
     */
    private <T extends ApiRestResponse<?>> ResponseEntity<T> responseRemoveTokens( final T body ){
        return responseWithTokens( body, null );
    }

// ------------------------------------------------------------------------------------------------------------------ \\

}
