package com.codenaiten.template.rest.app.dto.command.user;

import com.codenaiten.template.rest.app.entity.User;
import com.codenaiten.template.rest.app.vo.image.ImageContentType;
import com.codenaiten.template.rest.app.vo.user.UserName;
import com.codenaiten.template.rest.app.vo.user.UserSurname;
import com.codenaiten.template.rest.app.vo.user.UserUsername;

import java.lang.reflect.Array;
import java.time.LocalDate;

/**
 * DTO Command que contiene los datos de actualización de un {@link User}.
 *
 * @param image {@link Array} de {@code byte} que representa la imagen de perfil del {@link User}.
 * @param imageContentType {@link ImageContentType} que representa el tipo de contenido de la imagen de perfil del {@link User}.
 * @param username {@link UserUsername} que representa el nuevo username del {@link User}.
 * @param name {@link UserName} que representa el nuevo nombre del {@link User}.
 * @param surname {@link UserSurname} que representa el nuevo apellido paterno del {@link User}.
 * @param birthdate {@link LocalDate} que representa la nueva fecha de nacimiento del {@link User}
 */
public record UpdateUserCommand(
        byte[] image,
        ImageContentType imageContentType,
        UserUsername username,
        UserName name,
        UserSurname surname,
        LocalDate birthdate
) {
}
