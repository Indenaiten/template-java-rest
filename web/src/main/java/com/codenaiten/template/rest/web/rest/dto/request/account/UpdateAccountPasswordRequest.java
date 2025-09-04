package com.codenaiten.template.rest.web.rest.dto.request.account;

import com.codenaiten.template.rest.app.vo.account.AccountPassword;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Schema( description = "Petición para actualizar la contraseña de la cuenta" )
public record UpdateAccountPasswordRequest(

        @Schema( description = "Contraseña actual para confirmar la acción", example = "CurrentPassword.123", requiredMode = Schema.RequiredMode.REQUIRED )
        @NotNull
        @Size( min = AccountPassword.MIN_SIZE, max = AccountPassword.MAX_SIZE )
        @Pattern( regexp = AccountPassword.FORMAT, message = "La contraseña debe tener entre 8 y 64 caracteres, y contener al menos una letra mayúscula, una minúscula, un número y un carácter especial." )
        AccountPassword password,

        @Schema( description = "Nueva contraseña de usuario", example = "NewPassword.456", requiredMode = Schema.RequiredMode.REQUIRED )
        @NotNull
        @Size( min = AccountPassword.MIN_SIZE, max = AccountPassword.MAX_SIZE )
        @Pattern( regexp = AccountPassword.FORMAT, message = "La contraseña debe tener entre 8 y 64 caracteres, y contener al menos una letra mayúscula, una minúscula, un número y un carácter especial." )
        AccountPassword newPassword
) {
}
