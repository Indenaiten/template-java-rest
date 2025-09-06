package com.codenaiten.template.rest.app.old.dto.command.account;

import com.codenaiten.template.rest.app.old.vo.Email;
import com.codenaiten.template.rest.app.old.vo.account.AccountPassword;

import java.util.Locale;

public record UpdateAccountCommand(
        Locale lang,
        AccountRole role,
        Email email,
        AccountPassword password
){

// ------------------------------------------------------------------------------------------------------------------ \\

}
