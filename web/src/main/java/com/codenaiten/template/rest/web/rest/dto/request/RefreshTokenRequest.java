package com.codenaiten.template.rest.web.rest.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema( description = "Solicitud de actualización de token" )
public record RefreshTokenRequest(
        @Schema( description = "Token de actualización", required = true, example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..." )
        String refreshToken
) {
}