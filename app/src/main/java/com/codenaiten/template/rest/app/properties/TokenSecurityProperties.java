package com.codenaiten.template.rest.app.properties;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Properties que contiene las propiedades relacionadas con los tokens de autenticación del sistema.
 *
 * @see Properties
 */
@Slf4j
@Getter
@Component
public class TokenSecurityProperties extends Properties {

    /** Clave secreta del access token */
    @Value( "${app.security.token.access-token.secret}" )
    private String accessTokenSecret;

    /** Tiempo de expiracion en milisegundos del access token */
    @Value( "${app.security.token.access-token.expiration:180000}" )
    private Long accessTokenExpiration;

    /** Nombre del cookie/header que almacena el access token */
    @Value( "${app.security.token.access-token.name:Token}" )
    private String accessTokenName;

    /** Clave secreta del refresh token */
    @Value( "${app.security.token.refresh-token.secret}" )
    private String refreshTokenSecret;

    /** Tiempo de expiracion en milisegundos del refresh token */
    @Value( "${app.security.token.refresh-token.expiration:604800000}" )
    private Long refreshTokenExpiration;

    /** Nombre de la cookie que almacena el refresh token */
    @Value( "${app.security.token.refresh-token.name:Refresh-Token}" )
    private String refreshTokenName;

}
