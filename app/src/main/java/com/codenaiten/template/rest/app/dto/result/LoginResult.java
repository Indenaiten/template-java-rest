package com.codenaiten.template.rest.app.dto.result;

import com.codenaiten.template.rest.app.authentication.TokenInfo;
import com.codenaiten.template.rest.app.entity.Account;
import com.codenaiten.template.rest.app.entity.User;
import com.codenaiten.template.rest.app.vo.account.AccountId;
import com.codenaiten.template.rest.app.vo.user.UserId;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

/**
 * DTO Result con la información de una autenticación con éxito.
 *
 * @see Serializable
 */
@Getter
@Builder
@RequiredArgsConstructor
public class LoginResult implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

// ------------------------------------------------------------------------------------------------------------------ \\

    /** El {@link AccountId} único de la {@link Account} */
    private final UUID accountId;

    /** El {@link UserId} único del {@link User} */
    private final UUID userId;

    /** La información de los tokens producidos */
    private final TokenInfo tokenInfo;

// ------------------------------------------------------------------------------------------------------------------ \\

}
