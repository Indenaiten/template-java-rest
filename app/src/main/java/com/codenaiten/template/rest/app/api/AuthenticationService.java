package com.codenaiten.template.rest.app.api;

import com.codenaiten.template.rest.app.dto.command.LoginCommand;
import com.codenaiten.template.rest.app.dto.command.RefreshTokenCommand;
import com.codenaiten.template.rest.app.dto.command.RegisterCommand;
import com.codenaiten.template.rest.app.dto.result.AccountInfoResult;
import com.codenaiten.template.rest.app.dto.result.LoginResult;
import com.codenaiten.template.rest.app.dto.result.RefreshTokenResult;

public interface AuthenticationService {

    AccountInfoResult register( RegisterCommand command );
    LoginResult login( LoginCommand command );
    void logout();
    void invalidate();
    RefreshTokenResult refresh( RefreshTokenCommand command );
}
