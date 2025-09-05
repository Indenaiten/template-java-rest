package com.codenaiten.template.rest.app.dto.command.account;

import com.codenaiten.template.rest.app.vo.Email;
import com.codenaiten.template.rest.app.vo.account.AccountPassword;
import com.codenaiten.template.rest.app.vo.account.AccountRole;

import java.util.Locale;

public record UpdateAccountCommand(
        Locale lang,
        AccountRole role,
        Email email,
        AccountPassword password
){

// ------------------------------------------------------------------------------------------------------------------ \\

}
