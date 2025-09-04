package com.codenaiten.template.rest.app.dto.command.account;

import com.codenaiten.template.rest.app.entity.Account;
import com.codenaiten.template.rest.app.entity.Image;
import com.codenaiten.template.rest.app.vo.image.ImageContentType;

import java.lang.reflect.Array;

/**
 * DTO Command que contiene los datos de creación de una nueva {@link Account}.
 *
 * @param image {@link Array} de {@code byte} que representa el contenido de la {@link Image} que se va a crear.
 * @param contentType {@link ImageContentType} que representa el tipo de contenido de la {@link Image} que se va a crear.
 * @param contentSize {@link Long} que representa el tamaño en bytes de la {@link Image} que se va a crear.
 */
public record CreateImageCommand(
        byte[] image,
        ImageContentType contentType,
        Long contentSize
) {
}
