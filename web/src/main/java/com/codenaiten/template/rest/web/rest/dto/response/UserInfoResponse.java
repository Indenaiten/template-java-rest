package com.codenaiten.template.rest.web.rest.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Builder
@RequiredArgsConstructor
@Schema( description = "Información de un usuario" )
public class UserInfoResponse  implements Serializable{

    @Serial
    private static final long serialVersionUID = 1L;

// ------------------------------------------------------------------------------------------------------------------ \\

    @Schema( description = "Identificador único del usuario", example = "8e423b1a-ec10-4d92-a0cb-88d7e3e9f5a6" )
    private final UUID id;

    @Schema( description = "Nombre de usuario", example = "Example" )
    private final String username;

    @Schema( description = "Nombre real del usuario", example = "Real Name" )
    private final String name;

    @Schema( description = "Apellido del usuario", example = "Optional Surname", nullable = true )
    private final String surname;

    @Schema( description = "Fecha de nacimiento", example = "10/02/1993" )
    private final LocalDate birthdate;

    @Schema( description = "Fecha de creación (epoch millis)", type = "number", example = "1725403200000" )
    private final Long createdAt;

    @Schema( description = "Fecha de última modificación (epoch millis)", type = "number", example = "1725489600000" )
    private final Long updatedAt;

// ------------------------------------------------------------------------------------------------------------------ \\

}
