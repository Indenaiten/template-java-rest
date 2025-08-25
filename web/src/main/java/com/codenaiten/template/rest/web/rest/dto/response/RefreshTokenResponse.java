package com.codenaiten.template.rest.web.rest.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

@Schema( description = "Respuesta de actualización de token exitosa" )
public record RefreshTokenResponse(
        @Schema( description = "Identificador único de la cuenta de usuario", example = "5d16313f-de31-40a3-9db6-28bc2e518ae1" )
        UUID accountId,

        @Schema( description = "Identificador único del usuario", example = "8e423b1a-ec10-4d92-a0cb-88d7e3e9f5a6" )
        UUID userId,

        @Schema( description = "Nuevo token JWT de acceso", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..." )
        String accessToken,

        @Schema( description = "Nuevo token JWT de actualización", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..." )
        String refreshToken

){
}