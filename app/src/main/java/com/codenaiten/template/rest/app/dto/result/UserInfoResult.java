package com.codenaiten.template.rest.app.dto.result;

import com.codenaiten.template.rest.app.entity.User;
import com.codenaiten.template.rest.app.vo.Timestamp;
import com.codenaiten.template.rest.app.vo.user.UserId;
import com.codenaiten.template.rest.app.vo.user.UserName;
import com.codenaiten.template.rest.app.vo.user.UserSurname;
import com.codenaiten.template.rest.app.vo.user.UserUsername;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * DTO Result con la información de un {@link User}.
 *
 * @see Serializable
 */
@Getter
@Builder
@RequiredArgsConstructor
public class UserInfoResult implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

// ------------------------------------------------------------------------------------------------------------------ \\

    /** El {@link UserId} único del {@link User} */
    private final UUID id;

    /** El {@link UserUsername} único del {@link User} */
    private final String username;

    /** El {@link UserName} del {@link User} */
    private final String name;

    /** El {@link UserSurname} del {@link User} */
    private final String surname;

    /** La fecha de nacimiento del {@link User} */
    private final LocalDate birthdate;

    /** El {@link Timestamp} con la fecha y hora de creación del {@link User} */
    private final LocalDateTime createdAt;

    /** El {@link Timestamp} con la fecha y hora de actualización del {@link User} */
    private final LocalDateTime updatedAt;

// ------------------------------------------------------------------------------------------------------------------ \\

}