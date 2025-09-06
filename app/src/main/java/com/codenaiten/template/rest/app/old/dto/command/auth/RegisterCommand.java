package com.codenaiten.template.rest.app.old.dto.command.auth;

import com.codenaiten.template.rest.app.old.vo.Email;
import com.codenaiten.template.rest.app.old.vo.account.AccountPassword;
import com.codenaiten.template.rest.app.old.vo.user.UserName;
import com.codenaiten.template.rest.app.old.vo.user.UserSurname;
import com.codenaiten.template.rest.app.old.vo.user.UserUsername;

import java.time.LocalDate;
import java.util.Locale;

public record RegisterCommand(
        Locale lang,
        UserUsername username,
        Email email,
        UserName name,
        UserSurname surname,
        LocalDate birthdate,
        AccountPassword password
){

// ------------------------------------------------------------------------------------------------------------------ \\

}
