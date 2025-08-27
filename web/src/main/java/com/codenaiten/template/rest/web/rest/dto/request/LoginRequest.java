package com.codenaiten.template.rest.web.rest.dto.request;

import com.codenaiten.template.rest.app.vo.account.AccountPassword;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema( description = "Petición para validar la autenticación de un usuario y generar un token de acceso" )
public record LoginRequest(

        @Schema( description = "Identificador, username o email del usuario", example = "root", requiredMode = Schema.RequiredMode.REQUIRED )
        @NotNull
        String login,

        @Schema( description = "Contraseña de la cuenta del usuario", type="string", example = "Root.123", requiredMode = Schema.RequiredMode.REQUIRED  )
        @NotNull
        AccountPassword password

) {
}
