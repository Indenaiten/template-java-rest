package com.codenaiten.template.rest.web.rest.dto.request.user;

import com.codenaiten.template.rest.app.vo.image.ImageId;
import com.codenaiten.template.rest.app.vo.user.UserName;
import com.codenaiten.template.rest.app.vo.user.UserSurname;
import com.codenaiten.template.rest.app.vo.user.UserUsername;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

@Schema( description = "Petición para actualizar información de usuario" )
public record UpdateUserRequest(

        @Schema( description = "ID de la imágen que será la image de perfil del usuario", example = "24843dfd-3783-4d47-8577-7d14d6c509e5" )
        ImageId image,

        @Schema( description = "Nickname de usuario único", example = "newusername" )
        @Size( min = UserUsername.MIN_SIZE, max = UserUsername.MAX_SIZE )
        @Pattern( regexp = UserUsername.FORMAT, message = "El nickname debe tener entre 3 y 25 caracteres, y solo puede contener letras, números, guiones bajos y guiones.")
        UserUsername username,

        @Schema( description = "Nombre del usuario", example = "Nuevo Nombre" )
        @Size( min = UserName.MIN_SIZE, max = UserName.MAX_SIZE )
        @Pattern( regexp = UserName.FORMAT, message = "El nombre debe tener entre 3 y 50 caracteres y solo puede contener letras, espacios y guiones." )
        UserName name,

        @Schema( description = "Apellido del usuario", example = "Nuevo Apellido", nullable = true )
        @Size( min = UserSurname.MIN_SIZE, max = UserSurname.MAX_SIZE )
        @Pattern( regexp = UserSurname.FORMAT, message = "El apellido debe tener entre 3 y 50 caracteres y solo puede contener letras, espacios y guiones." )
        UserSurname surname,

        @Schema( description = "Fecha de nacimiento", example = "01/01/2000" )
        @Pattern( regexp = "\\d{2}/\\d{2}/\\d{4}", message = "La fecha de nacimiento debe estar en formato (dd/mm/yyyy)." )
        LocalDate birthdate
) {
}
