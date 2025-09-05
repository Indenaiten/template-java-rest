package com.codenaiten.template.rest.app.dto.command.auth;

import com.codenaiten.template.rest.app.vo.account.AccountPassword;

public record LoginCommand(
        String login,
        AccountPassword password
){

// ------------------------------------------------------------------------------------------------------------------ \\

}
