package com.codenaiten.template.rest.app.api;

import com.codenaiten.template.rest.app.dto.command.LoginCommand;
import com.codenaiten.template.rest.app.dto.command.RegisterCommand;
import com.codenaiten.template.rest.app.dto.result.AccountInfoResult;
import com.codenaiten.template.rest.app.dto.result.LoginResult;

public interface AuthenticationService {

    AccountInfoResult register( RegisterCommand command );
    LoginResult login( LoginCommand command );
    //TODO: Método logout que invalide el token access-token y refresh-token del token autenticado.
    //TODO: Método invalidate que invalide todos los tokens access-token y refresh-token del usuario autenticado.
    //TODO: Método refresh para actualizar el token access-token mediante el token refresh-token.
}
