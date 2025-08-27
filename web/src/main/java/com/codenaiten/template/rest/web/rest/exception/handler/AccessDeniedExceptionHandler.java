package com.codenaiten.template.rest.web.rest.exception.handler;

import com.codenaiten.template.rest.app.AppMessage;
import com.codenaiten.template.rest.app.i18n.MessageI18n;
import com.codenaiten.template.rest.app.i18n.MessageI18nManager;
import com.codenaiten.template.rest.web.rest.dto.ApiRestResponse;
import com.codenaiten.template.rest.web.rest.dto.Empty;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.logging.LogLevel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Slf4j
@Component
@RequiredArgsConstructor
public class AccessDeniedExceptionHandler implements AccessDeniedHandler {

    private final MessageI18nManager messageI18nManager;

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| IMPLEMENTED METHODS |---------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    @Override
    public void handle( final HttpServletRequest req, final HttpServletResponse res,
                        final AccessDeniedException authException ) throws IOException {
        final MessageI18n messageI18n = AppMessage.ERROR_SECURITY_ACCESS_DENIED;
        log.error( messageI18n.getLoggerMessage() );
        log.debug( authException.getMessage(), authException );
        final String message = this.messageI18nManager.getMessage( messageI18n, LogLevel.ERROR );
        final ApiRestResponse<Empty> body = ApiRestResponse.securityError().message( message ).build();
        res.setStatus( HttpStatus.FORBIDDEN.value() );
        res.setContentType( MediaType.APPLICATION_JSON_VALUE );
        res.setCharacterEncoding( StandardCharsets.UTF_8.name() );
        res.getWriter().write( new ObjectMapper().writeValueAsString( body ));
    }

// ------------------------------------------------------------------------------------------------------------------ \\

}
