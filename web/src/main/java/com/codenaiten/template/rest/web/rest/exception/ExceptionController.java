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

/**
 * Rest contoller para manejar las excepciones producidas por el sistema.
 */
@Slf4j
@ControllerAdvice
@RequiredArgsConstructor
public class ExceptionController{

    /** Manager de mensajes internacionalizados del sistema */
    private final MessageI18nManager messageI18nManager;

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| EXCEPTIONS HANDLERS |---------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    /**
     * Handler para las excepciones de tipo {@link ValueInstantiationException} que se producen en el sistema.
     *
     * @param e {@link ValueInstantiationException} que se ha producido.
     *
     * @return {@link ResponseEntity} con la {@link ApiRestResponse} de tipo {@link Empty} con la información del error.
     */
    @ExceptionHandler( ValueInstantiationException.class )
    public ResponseEntity<ApiRestResponse<Empty>> handleValueInstantiation( final ValueInstantiationException e ){
        final ValidationException ve = findCause( e, ValidationException.class );
        if( Objects.nonNull( ve )) return this.handler( ve, ve.getMessageI18n(), ve.getArgs() );
        final MessageI18n message = AppMessage.ERROR_VALIDATION_GENERIC;
        log.error( message.getLoggerMessage() );
        log.debug( e.getMessage(), e );
        return this.handler( e, message );
    }

    /**
     * Handler para las excepciones de tipo {@link HttpMessageNotReadableException} que se producen en el sistema.
     *
     * @param e {@link HttpMessageNotReadableException} que se ha producido.
     *
     * @return {@link ResponseEntity} con la {@link ApiRestResponse} de tipo {@link Empty} con la información del error.
     */
    @ExceptionHandler( HttpMessageNotReadableException.class )
    public ResponseEntity<ApiRestResponse<Empty>> handleHttpMessageNotReadable( final HttpMessageNotReadableException e ){
        final ValidationException ve = this.findCause( e, ValidationException.class );
        if( Objects.nonNull( ve )) return this.handler( ve, ve.getMessageI18n(), ve.getArgs() );
        final MessageI18n message = AppMessage.ERROR_VALIDATION_GENERIC;
        log.error( message.getLoggerMessage() );
        log.debug( e.getMessage(), e );
        return this.handler( e, message );
    }

    /**
     * Handler para las excepciones de tipo {@link BadCredentialsException} que se producen en el sistema.
     *
     * @param e {@link BadCredentialsException} que se ha producido.
     *
     * @return {@link ResponseEntity} con la {@link ApiRestResponse} de tipo {@link Empty} con la información del error.
     */
    @ExceptionHandler( BadCredentialsException.class )
    public ResponseEntity<ApiRestResponse<Empty>> handleBadCredentialsException( final BadCredentialsException e ) {
        final MessageI18n messageI18n = AppMessage.ERROR_SECURITY_AUTH_BAD_CREDENTIALS;
        final String message = this.messageI18nManager.getMessageAndLogger( messageI18n, LogLevel.ERROR );
        ApiRestResponse<Empty> response = ApiRestResponse.validationError().message( message ).build();
        log.error( messageI18n.getLoggerMessage() );
        log.debug( e.getMessage(), e );
        return ResponseEntity.status( HttpStatus.BAD_REQUEST ).body( response );
    }

    /**
     * Handler para las excepciones de tipo {@link InvalidTokenException} que se producen en el sistema.
     *
     * @param e {@link InvalidTokenException} que se ha producido.
     *
     * @return {@link ResponseEntity} con la {@link ApiRestResponse} de tipo {@link Empty} con la información del error.
     */
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

    /**
     * Handler para las excepciones de tipo {@link LanguageNotSupportedException} que se producen en el sistema.
     *
     * @param e {@link LanguageNotSupportedException} que se ha producido.
     *
     * @return {@link ResponseEntity} con la {@link ApiRestResponse} de tipo {@link Empty} con la información del error.
     */
    @ExceptionHandler({ LanguageNotSupportedException.class, AccountEmailAlreadyException.class,
    UserUsernameAlreadyException.class, UserMinAgeException.class })
    public ResponseEntity<ApiRestResponse<Empty>> handleInvalidAccessTokenException( final AppException e ) {
        log.error( e.getMessage() );
        final String message = this.messageI18nManager.getMessageAndLogger( e.getMessageI18n(), LogLevel.ERROR, e.getArgs() );
        ApiRestResponse<Empty> response = ApiRestResponse.validationError().message( message ).build();
        return ResponseEntity.status( HttpStatus.BAD_REQUEST ).body( response );
    }

    /**
     * Handler para las excepciones de tipo {@link AppException} que se producen en el sistema.
     *
     * @param e {@link AppException} que se ha producido.
     *
     * @return {@link ResponseEntity} con la {@link ApiRestResponse} de tipo {@link Empty} con la información del error.
     */
    @ExceptionHandler({ AppException.class })
    public ResponseEntity<ApiRestResponse<Empty>> handleAppException( final AppException e ) {
        final MessageI18n message = e.getMessageI18n();
        final Object[] args = e.getArgs();
        log.error( e.getMessage() );
        return this.handler( e, message, args );
    }

    /**
     * Handler para las excepciones de tipo {@link Exception} que se producen en el sistema.
     *
     * @param e {@link Exception} que se ha producido.
     *
     * @return {@link ResponseEntity} con la {@link ApiRestResponse} de tipo {@link Empty} con la información del error.
     */
    @ExceptionHandler( Exception.class )
    public ResponseEntity<ApiRestResponse<Empty>> handlerException( final Exception e ) {
        log.error( e.getMessage() );
        return this.handler( e, AppMessage.ERROR_GENERIC );
    }

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| HELPER METHODS |--------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    /**
     * Handler para construir el {@link ResponseEntity} con la {@link ApiRestResponse} de tipo {@link Empty} con el
     * estado HTTP de respuesta correspondiente según el tipo de excepción que se haya producido.
     *
     * @param exception {@link Exception} que se ha producido.
     * @param messageI18n {@link MessageI18n} con el mensaje de error.
     * @param args {@link Object}[] con los argumentos para el mensaje de error.
     *
     * @return {@link ResponseEntity} con la {@link ApiRestResponse} de tipo {@link Empty} con la información del error.
     */
    private ResponseEntity<ApiRestResponse<Empty>> handler( final Exception exception, final MessageI18n messageI18n, final Object... args ){
        // Step 01: Get i18n message
        final String message = this.messageI18nManager.getMessage( messageI18n, args );

        // Step 02: Build wrapper response
        ApiRestResponse<Empty> response = ApiRestResponse.error().message( message ).build();

        // Step 03: Build response with error code and message
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

    /**
     * Busca y recupera la causa de un {@link Throwable} dado que se corresponda con la cláse {@link T}.
     *
     * @param ex {@link Throwable} que se va a buscar la causa.
     * @param type {@link Class} que de tipo {@link T} que se va a buscar.
     *
     * @return {@link T} con la causa encontrada, {@code null} si no se ha encontrado ninguna.
     *
     * @param <T> Tipo de la causa que se va a buscar que extiende de {@link Throwable}.
     */
    private <T extends Throwable> T findCause( Throwable ex, final Class<T> type ){
        while( Objects.nonNull( ex ) && ex.getCause() != ex ){
            if( type.isInstance( ex )) return type.cast( ex );
            ex = ex.getCause();
        }
        return null;
    }

// ------------------------------------------------------------------------------------------------------------------ \\

}
