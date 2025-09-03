package com.codenaiten.template.rest.app.dto.command.auth;

import com.codenaiten.template.rest.app.entity.Account;
import com.codenaiten.template.rest.app.entity.User;
import com.codenaiten.template.rest.app.vo.Email;
import com.codenaiten.template.rest.app.vo.account.AccountPassword;
import com.codenaiten.template.rest.app.vo.user.UserId;
import com.codenaiten.template.rest.app.vo.user.UserUsername;

/**
 * DTO Command que contiene los datos para autenticar un usuario.
 *
 * @param login {@link String} que representa el {@link UserUsername}, {@link Email} o el {@link UserId} de un {@link User}.
 * @param password {@link AccountPassword} que representa la contraseña de la {@link Account}.
 */
public record LoginCommand(
        String login,
        AccountPassword password
) {
}
