package com.codenaiten.template.rest.web.rest.dto.response;

import com.codenaiten.template.rest.app.vo.account.AccountRole;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema( description = "Información del rol de la cuenta de usuario" )
public record AccountRoleInfoResponse(

        @Schema( description = "Identificador único del rol de la cuenta de usuario", example = "1" )
        Integer id,

        @Schema( description = "Nombre del role de la cuenta de usuario", example = "ADMIN" )
        String name
){

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| BUILDER |---------------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    public static AccountRoleInfoResponse of( final AccountRole role ){
        return new AccountRoleInfoResponse( role.value(), role.getName() );
    }

// ------------------------------------------------------------------------------------------------------------------ \\

}
