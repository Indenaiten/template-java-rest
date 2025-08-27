package com.codenaiten.template.rest.app.api;

import com.codenaiten.template.rest.app.dto.command.auth.LoginCommand;
import com.codenaiten.template.rest.app.dto.command.auth.RegisterCommand;
import com.codenaiten.template.rest.app.dto.result.AccountInfoResult;
import com.codenaiten.template.rest.app.dto.result.LoginResult;

import java.util.List;

public interface AuthenticationService {

    AccountInfoResult register( RegisterCommand command );
    LoginResult login( LoginCommand command, String ip );
    LoginResult refresh( String token, String ip );
    void logout();
    void invalidate( List<String> ips );
    void invalidate();
}
