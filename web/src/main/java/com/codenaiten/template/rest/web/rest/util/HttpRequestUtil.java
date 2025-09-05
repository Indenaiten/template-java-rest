package com.codenaiten.template.rest.web.rest.util;

import jakarta.servlet.http.HttpServletRequest;
import lombok.experimental.UtilityClass;

import java.util.Objects;
import java.util.Optional;

/**
 * Utilidades relacionadas con las solicitudes HTTP.
 */
@UtilityClass
public class HttpRequestUtil {

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| IP |--------------------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    /**
     * Obtiene la IP del cliente que realiza la solicitud HTTP.
     *
     * @param request {@link HttpServletRequest} que representa la solicitud HTTP.
     *
     * @return {@link Optional} con el {@link String} que representa la IP del cliente, {@link Optional#empty()} en caso
     * contrario.
     */
    public static Optional<String> getClientIp( final HttpServletRequest request ){
        String result = null;
        String ip = request.getHeader( "X-Forwarded-For" ); // Detrás de proxy / load balancer
        if( Objects.nonNull( ip ) && !ip.isEmpty() && !"unknown".equalsIgnoreCase( ip )) result = ip.split( "," )[0].trim();
        ip = request.getHeader( "X-Real-IP" ); // A veces Nginx la pone así
        if( Objects.nonNull( ip ) && !ip.isEmpty() && !"unknown".equalsIgnoreCase( ip )) result = ip;
        return Optional.ofNullable( result );
    }

// ------------------------------------------------------------------------------------------------------------------ \\

}
