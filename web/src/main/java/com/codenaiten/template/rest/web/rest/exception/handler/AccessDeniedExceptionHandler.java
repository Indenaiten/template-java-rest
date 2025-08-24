package com.codenaiten.template.rest.web.rest.exception.handler;

import com.codenaiten.template.rest.app.i18n.AppMessage;
import com.codenaiten.template.rest.app.i18n.MessageI18nManager;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

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
        log.error( authException.getMessage() );
        final String message = this.messageI18nManager.getMessage( AppMessage.ERROR_SECURITY_ACCESS_DENIED );
        final Map<String, Object> body = Map.of( "message", message );
        res.setStatus( HttpStatus.UNAUTHORIZED.value() );
        res.setContentType( MediaType.APPLICATION_JSON_VALUE );
        res.setCharacterEncoding( StandardCharsets.UTF_8.name() );
        res.getWriter().write( new ObjectMapper().writeValueAsString( body ));
    }

// ------------------------------------------------------------------------------------------------------------------ \\

}
