package com.codenaiten.template.rest.app.dto.result;

import com.codenaiten.template.rest.app.entity.Image;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.lang.reflect.Array;

/**
 * DTO Result con la información del contenido de una {@link Image}.
 *
 * @see Serializable
 */
@Getter
@Builder
@RequiredArgsConstructor
public class ImageContentResult implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

// ------------------------------------------------------------------------------------------------------------------ \\

    /** El {@link ImageInfoResult} con la información de la {@link Image} */
    private final ImageInfoResult info;

    /** Un {@link Array} de {@code byte} con el contenido de la {@link Image} */
    private final byte[] bytes;

// ------------------------------------------------------------------------------------------------------------------ \\

}