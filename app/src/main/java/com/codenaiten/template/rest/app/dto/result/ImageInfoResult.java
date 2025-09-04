package com.codenaiten.template.rest.app.dto.result;

import com.codenaiten.template.rest.app.entity.Image;
import com.codenaiten.template.rest.app.vo.Timestamp;
import com.codenaiten.template.rest.app.vo.image.ImageContentType;
import com.codenaiten.template.rest.app.vo.image.ImageId;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * DTO Result con la información de un {@link Image}.
 *
 * @see Serializable
 */
@Getter
@Builder
@RequiredArgsConstructor
public class ImageInfoResult implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

// ------------------------------------------------------------------------------------------------------------------ \\

    /** El {@link ImageId} único de la {@link Image} */
    private final UUID id;

    /** El {@link ImageContentType} de la {@link Image} */
    private final String contentType;

    /** El Content Size de la {@link Image} */
    private final Long size;

    /** El {@link Timestamp} con la fecha y hora de creación de la {@link Image} */
    private final LocalDateTime createdAt;

    /** El {@link Timestamp} con la fecha y hora de actualización de la {@link Image} */
    private final LocalDateTime updatedAt;

// ------------------------------------------------------------------------------------------------------------------ \\

}