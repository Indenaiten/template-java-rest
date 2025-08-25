package com.codenaiten.template.rest.web.rest.api;

import com.codenaiten.template.rest.web.rest.dto.request.LoginRequest;
import com.codenaiten.template.rest.web.rest.dto.request.RefreshTokenRequest;
import com.codenaiten.template.rest.web.rest.dto.request.RegisterRequest;
import com.codenaiten.template.rest.web.rest.dto.response.AccountInfoResponse;
import com.codenaiten.template.rest.web.rest.dto.response.LoginResponse;
import com.codenaiten.template.rest.web.rest.dto.response.RefreshTokenResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag( name = "Autenticación", description = "Operaciones relacionadas con la autenticación" )
@RequestMapping( "/api/auth" )
public interface AuthenticationApiRest {

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| LOGIN |------------------------------------------------------------------------------------------------------ \\
// ------------------------------------------------------------------------------------------------------------------ \\

    @Operation(
            summary = "Autenticar un usuario",
            description = "Permite autenticar un usuario en el sistema.",
            operationId = "login",
            responses = @ApiResponse(
                    responseCode = "200",
                    description = "Autenticación exitosa",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema( implementation = LoginResponse.class )
                    )
            )
    )
    @PostMapping( "/login" )
    ResponseEntity<LoginResponse> login(
            @Parameter( description = "Credenciales del usuario", required = true )
            @RequestBody LoginRequest request
    );

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| REGISTER |--------------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    @Operation(
            summary = "Registrar un nuevo usuario",
            description = "Permite registrar una nuevo usuario en el sistema.",
            operationId = "register",
            responses = @ApiResponse(
                    responseCode = "201",
                    description = "Usuario registrado correctamente",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema( implementation = AccountInfoResponse.class )
                    )
            )
    )
    @PostMapping( "/signup" )
    ResponseEntity<AccountInfoResponse> register(
            @Parameter( description = "Datos del usuario a registrar", required = true )
            @RequestBody RegisterRequest request
    );

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| LOGOUT |------------------------------------------------------------------------------------------------------ \\
// ------------------------------------------------------------------------------------------------------------------ \\

    @Operation(
            summary = "Cerrar sesión",
            description = "Invalida los tokens de acceso y actualización del usuario autenticado.",
            operationId = "logout",
            security = @SecurityRequirement( name = "Bearer Authentication" ),
            responses = @ApiResponse(
                    responseCode = "200",
                    description = "Sesión cerrada exitosamente"
            )
    )
    @PostMapping( "/logout" )
    ResponseEntity<Void> logout();

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| INVALIDATE |-------------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    @Operation(
            summary = "Invalidar todos los tokens",
            description = "Invalida todos los tokens de acceso y actualización del usuario autenticado.",
            operationId = "invalidate",
            security = @SecurityRequirement( name = "Bearer Authentication" ),
            responses = @ApiResponse(
                    responseCode = "200",
                    description = "Tokens invalidados exitosamente"
            )
    )
    @PostMapping( "/invalidate" )
    ResponseEntity<Void> invalidate();

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| REFRESH |---------------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    @Operation(
            summary = "Actualizar tokens",
            description = "Genera nuevos tokens de acceso y actualización usando un token de actualización válido.",
            operationId = "refresh",
            responses = @ApiResponse(
                    responseCode = "200",
                    description = "Tokens actualizados exitosamente",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema( implementation = RefreshTokenResponse.class )
                    )
            )
    )
    @PostMapping( "/refresh" )
    ResponseEntity<RefreshTokenResponse> refresh(
            @Parameter( description = "Token de actualización", required = true )
            @RequestBody RefreshTokenRequest request
    );

// ------------------------------------------------------------------------------------------------------------------ \\

}
