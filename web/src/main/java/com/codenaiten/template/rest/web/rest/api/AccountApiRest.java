package com.codenaiten.template.rest.web.rest.api;

import com.codenaiten.template.rest.web.rest.dto.response.AccountInfoResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag( name = "Cuentas", description = "Operaciones relacionadas con las cuentas de usuario" )
@RequestMapping( "/api/account" )
public interface AccountApiRest {

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| GET MY ACCOUNT INFO |---------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    @Operation(
            summary = "Recupera la información de la cuenta del usuario autenticado",
            description = "Permite recuperar la información de la cuenta del usuario autenticado",
            operationId = "getMyAccountInfo",
            responses = @ApiResponse(
                    responseCode = "200",
                    description = "Datos recuperados correctamente",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema( implementation = AccountInfoResponse.class )
                    )
            )
    )
    @GetMapping( "/me" )
    ResponseEntity<AccountInfoResponse> getMyAccountInfo();

// ------------------------------------------------------------------------------------------------------------------ \\

}
