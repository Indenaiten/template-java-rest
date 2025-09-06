package com.codenaiten.template.rest.app.old.dto.command.auth;

import com.codenaiten.template.rest.app.old.vo.account.AccountPassword;

public record LoginCommand(
        String login,
        AccountPassword password
){

// ------------------------------------------------------------------------------------------------------------------ \\

}
