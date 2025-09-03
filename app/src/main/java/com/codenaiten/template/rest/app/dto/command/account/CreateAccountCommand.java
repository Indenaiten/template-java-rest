package com.codenaiten.template.rest.app.dto.command.account;

import com.codenaiten.template.rest.app.entity.Account;
import com.codenaiten.template.rest.app.entity.User;
import com.codenaiten.template.rest.app.vo.Email;
import com.codenaiten.template.rest.app.vo.account.AccountPassword;
import com.codenaiten.template.rest.app.vo.account.AccountRole;
import com.codenaiten.template.rest.app.vo.user.UserName;
import com.codenaiten.template.rest.app.vo.user.UserSurname;
import com.codenaiten.template.rest.app.vo.user.UserUsername;

import java.time.LocalDate;
import java.util.Locale;

/**
 * DTO Command que contiene los datos de creación de una nueva {@link Account}.
 *
 * @param lang {@link Locale} que representa el lenguaje de la {@link Account}.
 * @param role {@link AccountRole} que representa el role de la {@link Account}.
 * @param username {@link UserUsername} que representa el username del {@link User} de la {@link Account}.
 * @param email {@link Email} que representa el email de la {@link Account}.
 * @param name {@link UserName} que representa el nombre del {@link User} de la {@link Account}.
 * @param surname {@link UserSurname} que representa el apellido paterno del {@link User} de la {@link Account}.
 * @param birthdate {@link LocalDate} que representa la fecha de nacimiento del {@link User} de la {@link Account}.
 * @param password {@link AccountPassword} que representa la contraseña de la {@link Account}.
 */
public record CreateAccountCommand(
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
