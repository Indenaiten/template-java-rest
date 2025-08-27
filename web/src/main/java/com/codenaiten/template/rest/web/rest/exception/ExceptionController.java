package com.codenaiten.template.rest.web.rest.exception;

import com.codenaiten.template.rest.app.AppMessage;
import com.codenaiten.template.rest.app.exception.*;
import com.codenaiten.template.rest.app.exception.data.DataException;
import com.codenaiten.template.rest.app.exception.data.found.NotFoundException;
import com.codenaiten.template.rest.app.exception.security.SecurityException;
import com.codenaiten.template.rest.app.exception.security.access.AccessException;
import com.codenaiten.template.rest.app.exception.validation.ValidationException;
import com.codenaiten.template.rest.app.i18n.MessageI18n;
import com.codenaiten.template.rest.app.i18n.MessageI18nManager;
import com.codenaiten.template.rest.web.rest.dto.ApiRestResponse;
import com.codenaiten.template.rest.web.rest.dto.Empty;
import com.fasterxml.jackson.databind.exc.ValueInstantiationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.logging.LogLevel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Objects;

@Slf4j
@ControllerAdvice
@RequiredArgsConstructor
public class ExceptionController{

    private final MessageI18nManager messageI18nManager;

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| EXCEPTIONS HANDLERS |---------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    @ExceptionHandler( ValueInstantiationException.class )
    public ResponseEntity<ApiRestResponse<Empty>> handleValueInstantiation( final ValueInstantiationException e ){
        final ValidationException ve = findCause( e, ValidationException.class );
        if( Objects.nonNull( ve )) return this.handler( ve, ve.getMessageI18n(), ve.getArgs() );
        final MessageI18n message = AppMessage.ERROR_VALIDATION_GENERIC;
        log.error( message.getLoggerMessage() );
        log.debug( e.getMessage(), e );
        return this.handler( e, message );
    }

    @ExceptionHandler( HttpMessageNotReadableException.class )
    public ResponseEntity<ApiRestResponse<Empty>> handleHttpMessageNotReadable( final HttpMessageNotReadableException e ){
        final ValidationException ve = this.findCause( e, ValidationException.class );
        if( Objects.nonNull( ve )) return this.handler( ve, ve.getMessageI18n(), ve.getArgs() );
        final MessageI18n message = AppMessage.ERROR_VALIDATION_GENERIC;
        log.error( message.getLoggerMessage() );
        log.debug( e.getMessage(), e );
        return this.handler( e, message );
    }

    @ExceptionHandler( BadCredentialsException.class )
    public ResponseEntity<ApiRestResponse<Empty>> handleBadCredentialsException( final BadCredentialsException e ) {
        final MessageI18n messageI18n = AppMessage.ERROR_SECURITY_AUTH_BAD_CREDENTIALS;
        final String message = this.messageI18nManager.getMessageAndLogger( messageI18n, LogLevel.ERROR );
        ApiRestResponse<Empty> response = ApiRestResponse.validationError().message( message ).build();
        log.error( messageI18n.getLoggerMessage() );
        log.debug( e.getMessage(), e );
        return ResponseEntity.status( HttpStatus.BAD_REQUEST ).body( response );
    }

    @ExceptionHandler( InvalidTokenException.class )
    public ResponseEntity<ApiRestResponse<Empty>> handleInvalidTokenException( final InvalidTokenException e ) {
        log.error( e.getMessage() );
        final String message = this.messageI18nManager.getMessage( e.getMessageI18n(), LogLevel.ERROR, e.getArgs() );
        ApiRestResponse<Empty> response = ApiRestResponse.accessTokenError().message( message ).build();
        if( e instanceof InvalidAccessTokenException ){
            response = ApiRestResponse.accessTokenError().message( message ).build();
        }
        else if( e instanceof InvalidRefreshTokenException ){
            response = ApiRestResponse.refreshTokenError().message( message ).build();
        }
        return ResponseEntity.status( HttpStatus.UNAUTHORIZED ).body( response );
    }

    @ExceptionHandler({ LanguageNotSupportedException.class, AccountEmailAlreadyException.class,
    UserUsernameAlreadyException.class, UserMinAgeException.class })
    public ResponseEntity<ApiRestResponse<Empty>> handleInvalidAccessTokenException( final AppException e ) {
        log.error( e.getMessage() );
        final String message = this.messageI18nManager.getMessageAndLogger( e.getMessageI18n(), LogLevel.ERROR, e.getArgs() );
        ApiRestResponse<Empty> response = ApiRestResponse.validationError().message( message ).build();
        return ResponseEntity.status( HttpStatus.BAD_REQUEST ).body( response );
    }

    @ExceptionHandler({ AppException.class })
    public ResponseEntity<ApiRestResponse<Empty>> handleAppException( final AppException e ) {
        final MessageI18n message = e.getMessageI18n();
        final Object[] args = e.getArgs();
        log.error( e.getMessage() );
        return this.handler( e, message, args );
    }

    @ExceptionHandler( Exception.class )
    public ResponseEntity<ApiRestResponse<Empty>> handlerException( final Exception e ) {
        log.error( e.getMessage() );
        return this.handler( e, AppMessage.ERROR_GENERIC );
    }

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| HELPER METHODS |--------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    private ResponseEntity<ApiRestResponse<Empty>> handler( final Exception exception, final MessageI18n messageI18n, final Object... args ){
        final String message = this.messageI18nManager.getMessage( messageI18n, LogLevel.ERROR, args );
        ApiRestResponse<Empty> response = ApiRestResponse.error().message( message ).build();
        ResponseEntity<ApiRestResponse<Empty>> result = ResponseEntity.status( HttpStatus.INTERNAL_SERVER_ERROR ).body( response );
        if( exception instanceof ValidationException ) {
            response = ApiRestResponse.validationError().message( message ).build();
            result = ResponseEntity.status( HttpStatus.BAD_REQUEST ).body( response );
        }
        else if( exception instanceof NotFoundException ) {
            response = ApiRestResponse.dataError().message( message ).build();
            result = ResponseEntity.status( HttpStatus.NOT_FOUND ).body( response );
        }
        else if( exception instanceof DataException ) {
            response = ApiRestResponse.dataError().message( message ).build();
            result = ResponseEntity.status( HttpStatus.BAD_REQUEST ).body( response );
        }
        else if( exception instanceof AccessException ) {
            response = ApiRestResponse.accessError().message( message ).build();
            result = ResponseEntity.status( HttpStatus.FORBIDDEN ).body( response );
        }
        else if( exception instanceof SecurityException ) {
            response = ApiRestResponse.securityError().message(message).build();
            result = ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
        return result;
    }

    private <T extends Throwable> T findCause( Throwable ex, final Class<T> type ){
        while( Objects.nonNull( ex ) && ex.getCause() != ex ){
            if( type.isInstance( ex )) return type.cast( ex );
            ex = ex.getCause();
        }
        return null;
    }

// ------------------------------------------------------------------------------------------------------------------ \\

}
