package com.codenaiten.template.rest.app.dto.command.account;

import com.codenaiten.template.rest.app.entity.Account;
import com.codenaiten.template.rest.app.entity.User;
import com.codenaiten.template.rest.app.vo.Email;
import com.codenaiten.template.rest.app.vo.account.AccountRole;
import com.codenaiten.template.rest.app.vo.user.UserName;
import com.codenaiten.template.rest.app.vo.user.UserSurname;
import com.codenaiten.template.rest.app.vo.user.UserUsername;

import java.util.Locale;

/**
 * DTO Command que contiene los datos de filtrado para las consultas de {@link Account}.
 *
 * @param username {@link String} que representa el término de filtrado del {@link UserUsername} del {@link User} de la {@link Account}.
 * @param role {@link Integer} que representa el término de filtrado del {@link AccountRole} de la {@link Account}.
 * @param lang {@link String} que representa el término de filtrado del {@link Locale} de la {@link Account}.
 * @param email {@link String} que representa el término de filtrado del {@link Email} de la {@link Account}.
 * @param name {@link String} que representa el término de filtrado del {@link UserName} del {@link User} de la {@link Account}.
 * @param surname {@link String} que representa el término de filtrado del {@link UserSurname} del {@link User} de la {@link Account}.
 */
public record FilterAccountCommand(
        String username,
        Integer role,
        String lang,
        String email,
        String name,
        String surname
) {
}
