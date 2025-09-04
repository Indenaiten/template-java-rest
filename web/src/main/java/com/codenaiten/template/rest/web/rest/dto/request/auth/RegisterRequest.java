package com.codenaiten.template.rest.web.rest.dto.request.auth;

import com.codenaiten.template.rest.app.vo.Email;
import com.codenaiten.template.rest.app.vo.account.AccountPassword;
import com.codenaiten.template.rest.app.vo.user.UserName;
import com.codenaiten.template.rest.app.vo.user.UserSurname;
import com.codenaiten.template.rest.app.vo.user.UserUsername;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

@Schema( description = "Petición para registrar una nueva cuenta de usuario" )
public record RegisterRequest(

        @Schema( description = "Lenguaje de la cuenta de usuario", type="string", example = "es" )
        String lang,

        @Schema( description = "Nickname de usuario único", example = "root", requiredMode = Schema.RequiredMode.REQUIRED  )
        @NotNull
        @Size( min = UserUsername.MIN_SIZE, max = UserUsername.MAX_SIZE )
        @Pattern( regexp = UserUsername.FORMAT, message = "El nickname debe tener entre 3 y 25 caracteres, y solo puede contener letras, números, guiones bajos y guiones." )
        String username,

        @Schema( description = "Email de usuario", example = "root@maildrop.cc", requiredMode = Schema.RequiredMode.REQUIRED  )
        @NotNull
        @Size( min = Email.MIN_SIZE, max = Email.MAX_SIZE )
        @Pattern( regexp = Email.FORMAT, message = "El email debe tener entre 6 y 256 caracteres y seguir el formato estándar de email." )
        String email,

        @Schema( description = "Nombre del usuario", example = "Root", requiredMode = Schema.RequiredMode.REQUIRED  )
        @NotNull
        @Size( min = UserName.MIN_SIZE, max = UserName.MAX_SIZE )
        @Pattern( regexp = UserName.FORMAT, message = "El nombre debe tener entre 3 y 50 caracteres y solo puede contener letras, espacios y guiones." )
        String name,

        @Schema( description = "Apellido del usuario", example = "Admin", nullable = true )
        @Size( min = UserSurname.MIN_SIZE, max = UserSurname.MAX_SIZE )
        @Pattern( regexp = UserSurname.FORMAT, message = "El apellido debe tener entre 3 y 50 caracteres y solo puede contener letras, espacios y guiones." )
        String surname,

        @Schema( description = "Fecha de nacimiento", example = "01/01/2000", requiredMode = Schema.RequiredMode.REQUIRED  )
        @NotNull
        @Pattern( regexp = "\\d{2}/\\d{2}/\\d{4}", message = "La fecha de nacimiento debe estar en formato (dd/mm/yyyy)." )
        LocalDate birthdate,

        @Schema( description = "Contraseña de usuario", example = "Root.123", requiredMode = Schema.RequiredMode.REQUIRED  )
        @NotNull
        @Size( min = AccountPassword.MIN_SIZE, max = AccountPassword.MAX_SIZE )
        @Pattern( regexp = AccountPassword.FORMAT, message = "La contraseña debe tener entre 8 y 64 caracteres, y contener al menos una letra mayúscula, una minúscula, un número y un carácter especial." )
        String password
){
}

