package com.codenaiten.template.rest.app.properties;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class TokenProperties extends Properties {

    @Value( "${app.security.token.access-token.secret}" )
    private String accessTokenSecret;

    @Value( "${app.security.token.access-token.expiration:180000}" )
    private Long accessTokenExpiration;

    @Value( "${app.security.token.access-token.name:Token}" )
    private String accessTokenName;

    @Value( "${app.security.token.refresh-token.secret}" )
    private String refreshTokenSecret;

    @Value( "${app.security.token.refresh-token.expiration:604800000}" )
    private Long refreshTokenExpiration;

    @Value( "${app.security.token.refresh-token.name:Refresh-Token}" )
    private String refreshTokenName;

}
