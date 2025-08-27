package com.codenaiten.template.rest.web.rest.util;

import jakarta.servlet.http.HttpServletRequest;
import lombok.experimental.UtilityClass;

import java.util.Objects;

@UtilityClass
public class HttpRequestUtil {

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| IP |--------------------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    public static String getClientIp( final HttpServletRequest request ){
        String ip = request.getHeader( "X-Forwarded-For" ); // Detrás de proxy / load balancer
        if( Objects.nonNull( ip ) && !ip.isEmpty() && !"unknown".equalsIgnoreCase( ip )) return ip.split( "," )[0].trim();
        ip = request.getHeader( "X-Real-IP" ); // A veces Nginx la pone así
        if( Objects.nonNull( ip ) && !ip.isEmpty() && !"unknown".equalsIgnoreCase( ip )) return ip;
        return request.getRemoteAddr(); // fallback
    }

// ------------------------------------------------------------------------------------------------------------------ \\

}
