package com.codenaiten.template.rest.app.old.authentication;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Getter
@Builder
@RequiredArgsConstructor
public class TokenInfo{

    private final UUID accountId;
    private final String token;
    private final String refreshToken;

}
