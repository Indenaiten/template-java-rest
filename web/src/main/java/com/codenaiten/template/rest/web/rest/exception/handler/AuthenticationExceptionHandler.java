package com.codenaiten.template.rest.web.rest.exception.handler;

import com.codenaiten.template.rest.app.i18n.AppMessage;
import com.codenaiten.template.rest.app.i18n.MessageI18nManager;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Slf4j
@Component
@AllArgsConstructor
public class AuthenticationExceptionHandler implements AuthenticationEntryPoint {

    private final MessageI18nManager messageI18nManager;

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| IMPLEMENTED METHODS |---------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    @Override
    public void commence( final HttpServletRequest req, final HttpServletResponse res,
                          final AuthenticationException authException ) throws IOException {
        log.error( authException.getMessage() );
        final String message = this.messageI18nManager.getMessage( AppMessage.ERROR_SECURITY_GENERIC );
        final Map<String, Object> body = Map.of( "message", message );
        res.setStatus( HttpStatus.UNAUTHORIZED.value() );
        res.setContentType( MediaType.APPLICATION_JSON_VALUE );
        res.setCharacterEncoding( StandardCharsets.UTF_8.name() );
        res.getWriter().write( new ObjectMapper().writeValueAsString( body ));
    }

// ------------------------------------------------------------------------------------------------------------------ \\

}

