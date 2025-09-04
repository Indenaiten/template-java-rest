package com.codenaiten.template.rest.web.rest.dto.request.account;

import com.codenaiten.template.rest.app.vo.Email;
import com.codenaiten.template.rest.app.vo.account.AccountPassword;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Schema( description = "Petición para actualizar información de cuenta" )
public record UpdateAccountRequest(

        @Schema( description = "Lenguaje de la cuenta de usuario", example = "es" )
        String lang,

        @Schema( description = "Rol de la cuenta de usuario", type="number", example = "1" )
        @Min( value = 1, message = "El rol debe ser un valor positivo" )
        Integer role,

        @Schema( description = "Nuevo email de usuario", example = "newemail@maildrop.cc" )
        @Size( min = Email.MIN_SIZE, max = Email.MAX_SIZE )
        @Pattern( regexp = Email.FORMAT, message = "El email debe tener entre 6 y 256 caracteres y seguir el formato estándar de email." )
        String email,

        @Schema( description = "Nueva contraseña de usuario", example = "NewPassword.456" )
        @Size( min = AccountPassword.MIN_SIZE, max = AccountPassword.MAX_SIZE )
        @Pattern( regexp = AccountPassword.FORMAT, message = "La contraseña debe tener entre 8 y 64 caracteres, y contener al menos una letra mayúscula, una minúscula, un número y un carácter especial." )
        String password
) {
}
