package com.codenaiten.template.rest.app.authentication.impl;

import com.codenaiten.template.rest.app.authentication.TokenJwtManager;
import com.codenaiten.template.rest.app.dto.result.AccountInfoResult;
import com.codenaiten.template.rest.app.entity.TokenVersion;
import com.codenaiten.template.rest.app.properties.SecurityProperties;
import com.codenaiten.template.rest.app.repository.TokenVersionRepository;
import com.codenaiten.template.rest.app.vo.account.AccountId;
import com.codenaiten.template.rest.app.vo.user.UserId;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class TokenJwtManagerImpl implements TokenJwtManager {

    private static final String CLAIM_ACCOUNT = "account";
    private static final String CLAIM_VERSION = "version";
    private static final String TOKEN_TYPE_ACCESS = "ACCESS";
    private static final String TOKEN_TYPE_REFRESH = "REFRESH";

// ------------------------------------------------------------------------------------------------------------------ \\

    private final SecurityProperties securityProperties;
    private final TokenVersionRepository tokenVersionRepository;

    private Long accessTokenDuration = 180000L;
    private String accessTokenSecret = UUID.randomUUID().toString();
    private Long refreshTokenDuration = 1209600000L;
    private String refreshTokenSecret = UUID.randomUUID().toString();

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| INITIALIZATION |--------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    @PostConstruct
    public void init() {
        this.accessTokenDuration = this.securityProperties.getAccessTokenExpiration();
        this.accessTokenSecret = this.securityProperties.getAccessTokenSecret();
        this.refreshTokenDuration = this.securityProperties.getRefreshTokenExpiration();
        this.refreshTokenSecret = this.securityProperties.getRefreshTokenSecret();
    }

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| IMPLEMENTED METHODS |---------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    @Override
    @Transactional
    public String createAccessToken( final AccountInfoResult info ) {
        final UUID userId = UUID.fromString( info.owner().id().toString() );
        final long version = this.getNextVersion( userId, TOKEN_TYPE_ACCESS );
        
        // Save token version to database
        final TokenVersion tokenVersion = TokenVersion.builder()
                .id( UUID.randomUUID() )
                .userId( userId )
                .version( version )
                .tokenType( TOKEN_TYPE_ACCESS )
                .expiresAt( LocalDateTime.now().plus( this.accessTokenDuration, ChronoUnit.MILLIS ) )
                .isInvalidated( false )
                .build();
        this.tokenVersionRepository.save( tokenVersion );

        return Jwts.builder()
                .setSubject( info.owner().id().toString() )
                .claim( CLAIM_ACCOUNT, info.id() )
                .claim( CLAIM_VERSION, version )
                .setIssuedAt( new Date())
                .setExpiration( Date.from( Instant.now().plus( this.accessTokenDuration, ChronoUnit.MILLIS )))
                .signWith( Keys.hmacShaKeyFor( this.accessTokenSecret.getBytes( StandardCharsets.UTF_8 )))
                .compact();
    }

    @Override
    @Transactional
    public String createRefreshToken( final AccountInfoResult info ) {
        final UUID userId = UUID.fromString( info.owner().id().toString() );
        final long version = this.getNextVersion( userId, TOKEN_TYPE_REFRESH );
        
        // Save token version to database
        final TokenVersion tokenVersion = TokenVersion.builder()
                .id( UUID.randomUUID() )
                .userId( userId )
                .version( version )
                .tokenType( TOKEN_TYPE_REFRESH )
                .expiresAt( LocalDateTime.now().plus( this.refreshTokenDuration, ChronoUnit.MILLIS ) )
                .isInvalidated( false )
                .build();
        this.tokenVersionRepository.save( tokenVersion );

        return Jwts.builder()
                .setSubject( info.owner().id().toString() )
                .claim( CLAIM_ACCOUNT, info.id() )
                .claim( CLAIM_VERSION, version )
                .setIssuedAt( new Date())
                .setExpiration( Date.from( Instant.now().plus( this.refreshTokenDuration, ChronoUnit.MILLIS )))
                .signWith( Keys.hmacShaKeyFor( this.refreshTokenSecret.getBytes( StandardCharsets.UTF_8 )))
                .compact();
    }

    @Override
    public UserId getSubject( final String token ){
        final String subject = this.parseAccessToken( token ).getBody().getSubject();
        final UUID uuid = UUID.fromString( subject );
        return UserId.of( uuid.toString() );
    }

    public UserId getSubjectFromRefreshToken( final String refreshToken ){
        final String subject = this.parseRefreshToken( refreshToken ).getBody().getSubject();
        final UUID uuid = UUID.fromString( subject );
        return UserId.of( uuid.toString() );
    }

    @Override
    public AccountId getAccountId( final String token ){
        final String claim = this.parseAccessToken( token ).getBody().get( CLAIM_ACCOUNT, String.class );
        return new AccountId( UUID.fromString( claim ));
    }

    @Override
    public boolean check( final String token ) {
        try {
            final Jws<Claims> claims = this.parseAccessToken( token );
            final UUID userId = UUID.fromString( claims.getBody().getSubject() );
            final Long version = claims.getBody().get( CLAIM_VERSION, Long.class );
            
            // Check if token version is still valid (not invalidated)
            return this.tokenVersionRepository
                    .findByUserIdAndVersionAndTokenTypeAndIsInvalidatedFalse( userId, version, TOKEN_TYPE_ACCESS )
                    .isPresent();
        }
        catch( final Exception e ) {
            return false;
        }
    }

    @Override
    public boolean checkRefreshToken( final String refreshToken ) {
        try {
            final Jws<Claims> claims = this.parseRefreshToken( refreshToken );
            final UUID userId = UUID.fromString( claims.getBody().getSubject() );
            final Long version = claims.getBody().get( CLAIM_VERSION, Long.class );
            
            // Check if refresh token version is still valid (not invalidated)
            return this.tokenVersionRepository
                    .findByUserIdAndVersionAndTokenTypeAndIsInvalidatedFalse( userId, version, TOKEN_TYPE_REFRESH )
                    .isPresent();
        }
        catch( final Exception e ) {
            return false;
        }
    }

    @Override
    @Transactional
    public void invalidateUserTokens( final UserId userId ) {
        final UUID userUUID = UUID.fromString( userId.toString() );
        this.tokenVersionRepository.invalidateAllByUserIdAndTokenType( userUUID, TOKEN_TYPE_ACCESS );
        this.tokenVersionRepository.invalidateAllByUserIdAndTokenType( userUUID, TOKEN_TYPE_REFRESH );
    }

    @Override
    @Transactional
    public void invalidateSpecificTokens( final UserId userId, final String accessToken, final String refreshToken ) {
        try {
            final UUID userUUID = UUID.fromString( userId.toString() );
            
            // Invalidate access token
            if ( accessToken != null ) {
                final Long accessTokenVersion = this.parseAccessToken( accessToken ).getBody().get( CLAIM_VERSION, Long.class );
                this.tokenVersionRepository.invalidateByUserIdAndVersionAndTokenType( userUUID, accessTokenVersion, TOKEN_TYPE_ACCESS );
            }
            
            // Invalidate refresh token
            if ( refreshToken != null ) {
                final Long refreshTokenVersion = this.parseRefreshToken( refreshToken ).getBody().get( CLAIM_VERSION, Long.class );
                this.tokenVersionRepository.invalidateByUserIdAndVersionAndTokenType( userUUID, refreshTokenVersion, TOKEN_TYPE_REFRESH );
            }
        }
        catch( final Exception e ) {
            // Log error but don't throw exception
        }
    }

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| HELPER METHODS |--------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    private Jws<Claims> parseAccessToken( final String token ){
        return Jwts.parserBuilder()
                .setSigningKey( this.accessTokenSecret.getBytes( StandardCharsets.UTF_8 ))
                .build()
                .parseClaimsJws( token );
    }

    private Jws<Claims> parseRefreshToken( final String token ){
        return Jwts.parserBuilder()
                .setSigningKey( this.refreshTokenSecret.getBytes( StandardCharsets.UTF_8 ))
                .build()
                .parseClaimsJws( token );
    }

    private long getNextVersion( final UUID userId, final String tokenType ) {
        return this.tokenVersionRepository.findMaxVersionByUserIdAndTokenType( userId, tokenType )
                .orElse( 0L ) + 1;
    }

// ------------------------------------------------------------------------------------------------------------------ \\

}
