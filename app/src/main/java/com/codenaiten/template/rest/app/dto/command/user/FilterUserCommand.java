package com.codenaiten.template.rest.app.dto.command.user;

import com.codenaiten.template.rest.app.entity.User;
import com.codenaiten.template.rest.app.vo.user.UserName;
import com.codenaiten.template.rest.app.vo.user.UserSurname;
import com.codenaiten.template.rest.app.vo.user.UserUsername;

/**
 * DTO Command que contiene los datos de filtrado para las consultas de {@link User}.
 *
 * @param username {@link String} que representa el término de filtrado del {@link UserUsername} del {@link User}.
 * @param name {@link String} que representa el término de filtrado del {@link UserName} del {@link User}.
 * @param surname {@link String} que representa el término de filtrado del {@link UserSurname} del {@link User}.
 */
public record FilterUserCommand(
        String username,
        String name,
        String surname
) {
}
