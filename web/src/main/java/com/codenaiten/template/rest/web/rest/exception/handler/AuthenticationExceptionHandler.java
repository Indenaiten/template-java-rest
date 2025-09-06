package com.codenaiten.template.rest.web.rest.exception.handler;

import com.codenaiten.template.rest.app.old.AppMessage;
import com.codenaiten.template.rest.app.old.i18n.MessageI18n;
import com.codenaiten.template.rest.app.old.i18n.MessageI18nManager;
import com.codenaiten.template.rest.web.rest.dto.ApiRestResponse;
import com.codenaiten.template.rest.web.rest.dto.Empty;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.logging.LogLevel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * Handler de errores para la excepción {@link AuthenticationException} de Spring Security.
 *
 * @see AuthenticationEntryPoint
 */
@Slf4j
@Component
@AllArgsConstructor
public class AuthenticationExceptionHandler implements AuthenticationEntryPoint {

    /** Manager de mensajes internacionalizados del sistema */
    private final MessageI18nManager messageI18nManager;

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| IMPLEMENTED METHODS |---------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    @Override
    public void commence( final HttpServletRequest req, final HttpServletResponse res,
                          final AuthenticationException authException ) throws IOException {
        // Step 01: Set i18n message
        final MessageI18n messageI18n = AppMessage.ERROR_SECURITY_AUTH_NOT_FOUND;

        // Step 02: Log error message
        log.error( messageI18n.getLoggerMessage() );
        log.debug( authException.getMessage(), authException );

        // Step 03: Get i18n error message
        final String message = this.messageI18nManager.getMessage( messageI18n, LogLevel.ERROR  );

        // Step 04: Build wrapper response
        final ApiRestResponse<Empty> body = ApiRestResponse.securityError().message( message ).build();

        // Step 05: Write response
        res.setStatus( HttpStatus.UNAUTHORIZED.value() );
        res.setContentType( MediaType.APPLICATION_JSON_VALUE );
        res.setCharacterEncoding( StandardCharsets.UTF_8.name() );
        res.getWriter().write( new ObjectMapper().writeValueAsString( body ));
    }

// ------------------------------------------------------------------------------------------------------------------ \\

}

