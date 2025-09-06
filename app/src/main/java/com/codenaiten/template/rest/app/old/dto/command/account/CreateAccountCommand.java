package com.codenaiten.template.rest.app.old.dto.command.account;

import com.codenaiten.template.rest.app.old.vo.Email;
import com.codenaiten.template.rest.app.old.vo.account.AccountPassword;
import com.codenaiten.template.rest.app.old.vo.image.ImageId;
import com.codenaiten.template.rest.app.old.vo.user.UserName;
import com.codenaiten.template.rest.app.old.vo.user.UserSurname;
import com.codenaiten.template.rest.app.old.vo.user.UserUsername;

import java.time.LocalDate;
import java.util.Locale;

public record CreateAccountCommand(
        Locale lang,
        ImageId image,
        AccountRole role,
        UserUsername username,
        Email email,
        UserName name,
        UserSurname surname,
        LocalDate birthdate,
        AccountPassword password
){

// ------------------------------------------------------------------------------------------------------------------ \\

}
