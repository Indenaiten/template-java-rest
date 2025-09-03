package com.codenaiten.template.rest.app.dto.result;

import com.codenaiten.template.rest.app.entity.Account;
import com.codenaiten.template.rest.app.entity.User;
import com.codenaiten.template.rest.app.vo.Email;
import com.codenaiten.template.rest.app.vo.Timestamp;
import com.codenaiten.template.rest.app.vo.account.AccountId;
import com.codenaiten.template.rest.app.vo.account.AccountRole;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.UUID;

/**
 * DTO Result con la información de una {@link Account}.
 *
 * @see Serializable
 */
@Getter
@Builder
@RequiredArgsConstructor
public class AccountInfoResult implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

// ------------------------------------------------------------------------------------------------------------------ \\

    /** El {@link AccountId} único de la {@link Account} */
    private final UUID id;

    /** El {@link User} propietario de la {@link Account} */
    private final UserInfoResult owner;

    /** El {@link Locale} de la {@link Account} */
    private final String lang;

    /** El {@link AccountRole} de la {@link Account} */
    private final Integer role;

    /** El {@link Email} de la {@link Account} */
    private final String email;

    /** El {@link Timestamp} con la fecha y hora de creación de la {@link Account} */
    private final LocalDateTime createdAt;

    /** El {@link Timestamp} con la fecha y hora de actualización de la {@link Account} */
    private final LocalDateTime updatedAt;

// ------------------------------------------------------------------------------------------------------------------ \\

}
