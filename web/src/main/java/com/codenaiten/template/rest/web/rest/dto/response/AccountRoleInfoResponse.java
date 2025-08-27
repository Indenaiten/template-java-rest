package com.codenaiten.template.rest.web.rest.dto.response;

import com.codenaiten.template.rest.app.vo.account.AccountRole;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Builder
@RequiredArgsConstructor
@Schema( description = "Información del rol de la cuenta de usuario" )
public class AccountRoleInfoResponse implements Serializable{

    @Serial
    private static final long serialVersionUID = 1L;

// ------------------------------------------------------------------------------------------------------------------ \\

    @Schema( description = "Identificador único del rol de la cuenta de usuario", example = "1" )
    private final Integer id;

    @Schema( description = "Nombre del role de la cuenta de usuario", example = "ADMIN" )
    private final String name;

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| CONSTRUCTOR |------------------------------------------------------------------------------------------------ \\
// ------------------------------------------------------------------------------------------------------------------ \\

    public AccountRoleInfoResponse( final AccountRole role ){
        this.id = role.value();
        this.name = role.getName();
    }

// ------------------------------------------------------------------------------------------------------------------ \\

}
