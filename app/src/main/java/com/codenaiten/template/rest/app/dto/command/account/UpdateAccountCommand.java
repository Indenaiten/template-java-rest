package com.codenaiten.template.rest.app.dto.command.account;

import com.codenaiten.template.rest.app.entity.Account;
import com.codenaiten.template.rest.app.vo.Email;
import com.codenaiten.template.rest.app.vo.account.AccountPassword;
import com.codenaiten.template.rest.app.vo.account.AccountRole;

import java.util.Locale;

/**
 * DTO Command que contiene los datos de actualización de una {@link Account}.
 *
 * @param lang {@link Locale} que representa el nuevo lenguaje de la {@link Account}.
 * @param role {@link AccountRole} que representa el nuevo role de la {@link Account}.
 * @param email {@link Email} que representa el nuevo email de la {@link Account}.
 * @param password {@link AccountPassword} que representa la nueva contraseña de la {@link Account}.
 */
public record UpdateAccountCommand(
        Locale lang,
        AccountRole role,
        Email email,
        AccountPassword password
) {
}
