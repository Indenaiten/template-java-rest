package com.codenaiten.template.rest.app.dto.command.account;

import com.codenaiten.template.rest.app.vo.Email;
import com.codenaiten.template.rest.app.vo.account.AccountPassword;
import com.codenaiten.template.rest.app.vo.account.AccountRole;
import com.codenaiten.template.rest.app.vo.user.UserName;
import com.codenaiten.template.rest.app.vo.user.UserSurname;
import com.codenaiten.template.rest.app.vo.user.UserUsername;

import java.time.LocalDate;
import java.util.Locale;

public record AccountCreateCommand(
        Locale lang,
        AccountRole role,
        UserUsername username,
        Email email,
        UserName name,
        UserSurname surname,
        LocalDate birthdate,
        AccountPassword password
) {
}
