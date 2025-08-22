package com.codenaiten.template.rest.app.dto.command;

import com.codenaiten.template.rest.app.vo.Email;
import com.codenaiten.template.rest.app.vo.account.AccountPassword;
import com.codenaiten.template.rest.app.vo.user.UserName;
import com.codenaiten.template.rest.app.vo.user.UserSurname;
import com.codenaiten.template.rest.app.vo.user.UserUsername;

import java.time.LocalDate;

public record RegisterCommand(
        UserUsername username,
        Email email,
        UserName name,
        UserSurname surname,
        LocalDate birthdate,
        AccountPassword password
) {
}
