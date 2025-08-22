package com.codenaiten.template.rest.app.authentication.impl;

import com.codenaiten.template.rest.app.authentication.TokenJwtManager;
import com.codenaiten.template.rest.app.dto.result.AccountInfoResult;
import com.codenaiten.template.rest.app.properties.SecurityProperties;
import com.codenaiten.template.rest.app.vo.account.AccountId;
import com.codenaiten.template.rest.app.vo.user.UserId;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class TokenJwtManagerImpl implements TokenJwtManager {

    private static final String CLAIM_ACCOUNT = "account";

// ------------------------------------------------------------------------------------------------------------------ \\

    private final SecurityProperties securityProperties;

    private Long accessTokenDuration = 180000L;
    private String accessTokenSecret = UUID.randomUUID().toString();

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| INITIALIZATION |--------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    @PostConstruct
    public void init() {
        this.accessTokenDuration = this.securityProperties.getAccessTokenExpiration();
        this.accessTokenSecret = this.securityProperties.getAccessTokenSecret();
    }

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| IMPLEMENTED METHODS |---------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    @Override
    public String create( final AccountInfoResult info ) {
        return Jwts.builder()
                .setSubject( info.owner().id().toString() )
                .claim( CLAIM_ACCOUNT, info.id() )
                .setIssuedAt( new Date())
                .setExpiration( Date.from( Instant.now().plus( this.accessTokenDuration, ChronoUnit.MILLIS )))
                .signWith( Keys.hmacShaKeyFor( this.accessTokenSecret.getBytes( StandardCharsets.UTF_8 )))
                .compact();
    }

    @Override
    public UserId getSubject( final String token ){
        final String subject = this.parse( token ).getBody().getSubject();
        final UUID uuid = UUID.fromString( subject );
        return UserId.of( uuid.toString() );
    }

    @Override
    public AccountId getAccountId( final String token ){
        final String claim = this.parse( token ).getBody().get( CLAIM_ACCOUNT, String.class );
        return new AccountId( UUID.fromString( claim ));
    }

    @Override
    public boolean check( final String token ) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey( this.accessTokenSecret.getBytes( StandardCharsets.UTF_8 ))
                    .build()
                    .parseClaimsJws( token );
            return true;
        }
        catch( final Exception e ) {
            return false;
        }
    }

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| HELPER METHODS |--------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    private Jws<Claims> parse( final String token ){
        return Jwts.parserBuilder()
                .setSigningKey( this.accessTokenSecret.getBytes( StandardCharsets.UTF_8 ))
                .build()
                .parseClaimsJws( token );
    }

// ------------------------------------------------------------------------------------------------------------------ \\

}
