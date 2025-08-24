package com.codenaiten.template.rest.web.rest.exception;

import com.codenaiten.template.rest.app.exception.AppException;
import com.codenaiten.template.rest.app.exception.NotFoundException;
import com.codenaiten.template.rest.app.exception.SecurityException;
import com.codenaiten.template.rest.app.exception.ValidationException;
import com.codenaiten.template.rest.app.i18n.AppMessage;
import com.codenaiten.template.rest.app.i18n.MessageI18n;
import com.codenaiten.template.rest.app.i18n.MessageI18nManager;
import com.fasterxml.jackson.databind.exc.ValueInstantiationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;
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
    public ResponseEntity<Map<String, Object>> handleValueInstantiation( final ValueInstantiationException e ){
        final ValidationException ve = findCause( e, ValidationException.class );
        if( ve != null ) return this.handler( ve, ve.getMessageI18n(), ve.getArgs() );
        return this.handler( e, AppMessage.ERROR_VALIDATION_GENERIC );
    }

    @ExceptionHandler( HttpMessageNotReadableException.class )
    public ResponseEntity<Map<String, Object>> handleHttpMessageNotReadable( final HttpMessageNotReadableException e ){
        final ValidationException ve = this.findCause( e, ValidationException.class );
        if( Objects.nonNull( ve )) return this.handler( ve, ve.getMessageI18n(), ve.getArgs() );
        return this.handler( ve, AppMessage.ERROR_VALIDATION_GENERIC );
    }

    @ExceptionHandler({ AppException.class })
    public ResponseEntity<Map<String, Object>> handleAppException( final AppException e ) {
        final MessageI18n message = e.getMessageI18n();
        final Object[] args = e.getArgs();
        return this.handler( e, message, args );
    }

    @ExceptionHandler( BadCredentialsException.class )
    public ResponseEntity<Map<String, Object>> handleBadCredentialsException( final BadCredentialsException e ) {
        return this.handler( e, AppMessage.ERROR_SECURITY_AUTH_BAD_CREDENTIALS );
    }

    @ExceptionHandler( Exception.class )
    public ResponseEntity<Map<String, Object>> handlerException( final Exception e ) {
        return this.handler( e, AppMessage.ERROR_GENERIC );
    }

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| HELPER METHODS |--------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    private ResponseEntity<Map<String, Object>> handler( final Exception exception, final MessageI18n messageI18n, final Object... args ){
        log.error( exception.getMessage() );
        final String message = this.messageI18nManager.getMessage( messageI18n, args );
        final Map<String, Object> body = Map.of( "message", message );
        ResponseEntity<Map<String, Object>> result = ResponseEntity.status( HttpStatus.INTERNAL_SERVER_ERROR ).body( body );
        if( exception instanceof ValidationException )
            result = ResponseEntity.status( HttpStatus.BAD_REQUEST ).body( body );
        else if( exception instanceof NotFoundException )
            result = ResponseEntity.status( HttpStatus.NOT_FOUND ).body( body );
        else if( exception instanceof SecurityException )
            result = ResponseEntity.status( HttpStatus.FORBIDDEN ).body( body );
        return result;
    }

    private <T extends Throwable> T findCause( Throwable ex, final Class<T> type ){
        while( ex != null && ex.getCause() != ex ){
            if( type.isInstance( ex )) return type.cast( ex );
            ex = ex.getCause();
        }
        return null;
    }

// ------------------------------------------------------------------------------------------------------------------ \\

}
