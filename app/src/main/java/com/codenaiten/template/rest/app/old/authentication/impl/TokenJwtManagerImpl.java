package com.codenaiten.template.rest.app.old.authentication.impl;

import com.codenaiten.template.rest.app.old.authentication.TokenInfo;
import com.codenaiten.template.rest.app.old.authentication.TokenJwtManager;
import com.codenaiten.template.rest.app.old.entity.Account;
import com.codenaiten.template.rest.app.old.entity.SecurityToken;
import com.codenaiten.template.rest.app.old.exception.InvalidTokenException;
import com.codenaiten.template.rest.app.old.properties.TokenSecurityProperties;
import com.codenaiten.template.rest.app.old.repository.SecurityTokenRepository;
import com.codenaiten.template.rest.app.old.vo.account.AccountId;
import com.codenaiten.template.rest.app.old.vo.user.UserId;
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
import java.time.temporal.ChronoUnit;
import java.util.*;

@Component
@RequiredArgsConstructor
public class TokenJwtManagerImpl implements TokenJwtManager {

    private static final String CLAIM_VERSION = "version";
    private static final String CLAIM_ACCOUNT = "account";
    private static final String CLAIM_USER = "user";

// ------------------------------------------------------------------------------------------------------------------ \\

    private final TokenSecurityProperties tokenSecurityProperties;
    private final SecurityTokenRepository securityTokenRepository;

    private Long accessTokenDuration = 180000L;
    private String accessTokenSecret = UUID.randomUUID().toString();

    private Long refreshTokenDuration = 604800000L;
    private String refreshTokenSecret = UUID.randomUUID().toString();

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| INITIALIZATION |--------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    @PostConstruct
    public void init() {
        this.accessTokenDuration = this.tokenSecurityProperties.getAccessTokenExpiration();
        this.accessTokenSecret = this.tokenSecurityProperties.getAccessTokenSecret();
        this.refreshTokenDuration = this.tokenSecurityProperties.getRefreshTokenExpiration();
        this.refreshTokenSecret = this.tokenSecurityProperties.getRefreshTokenSecret();
    }

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| IMPLEMENTED METHODS |---------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    @Override
    @Transactional
    public TokenInfo create( final Account account, final String ip ) {
        final AccountId accountId = new AccountId( account.getId() );
        final UserId userId = new UserId( account.getOwner().getId() );
        final UUID id = UUID.randomUUID();
        final UUID version = UUID.randomUUID();
        final SecurityToken securityToken = SecurityToken.builder().id( id ).account( account ).ip( ip ).version( version ).build();
        this.securityTokenRepository.save( securityToken );
        final String accessToken = this.create( id, this.accessTokenSecret, this.accessTokenDuration, version, accountId, userId );
        final String refreshToken = this.create( id, this.refreshTokenSecret, this.refreshTokenDuration, version, accountId, userId );
        return TokenInfo.builder().accountId( account.getId() ).token( accessToken ).refreshToken( refreshToken ).build();
    }

    @Override
    @Transactional
    public TokenInfo create( final Account account ) {
        return this.create( account, null );
    }

    @Override
    @Transactional
    public TokenInfo refresh( final String refreshToken, final String ip ){
        this.validateRefreshToken( ip, refreshToken );
        final UUID id = this.getSubject( refreshToken );
        final AccountId account = this.getAccountId( refreshToken );
        final UserId user = this.getUserId( refreshToken );
        final SecurityToken securityToken = this.securityTokenRepository.findById( id )
                .orElseThrow( () -> new InvalidTokenException( refreshToken ));
        final UUID version = UUID.randomUUID();
        securityToken.setVersion( version );
        this.securityTokenRepository.save( securityToken );
        final String accessToken = this.create( id, this.accessTokenSecret, this.accessTokenDuration, version, account, user );
        final String newRefreshToken = this.create( id, this.refreshTokenSecret, this.refreshTokenDuration, version, account, user );
        return TokenInfo.builder().accountId( account.value() ).token( accessToken ).refreshToken( newRefreshToken ).build();
    }

    @Override
    @Transactional
    public TokenInfo refresh( final String refreshToken ){
        return this.refresh( refreshToken, null );
    }

    @Override
    public UUID getSubject( final String token ){
        final String subject = this.parse( token )
                .orElseThrow( () -> new InvalidTokenException( token ))
                .getBody().getSubject();
        return UUID.fromString( subject );
    }

    @Override
    public UUID getVersion( final String token ){
        final String claim = this.parse( token )
                .orElseThrow( () -> new InvalidTokenException( token ))
                .getBody().get( CLAIM_VERSION, String.class );
        return UUID.fromString( claim );
    }

    @Override
    public AccountId getAccountId( final String token ){
        final String claim = this.parse( token )
                .orElseThrow( () -> new InvalidTokenException( token ))
                .getBody().get( CLAIM_ACCOUNT, String.class );
        return new AccountId( UUID.fromString( claim ));
    }

    @Override
    public UserId getUserId( final String token ){
        final String claim = this.parse( token )
                .orElseThrow( () -> new InvalidTokenException( token ))
                .getBody().get( CLAIM_USER, String.class );
        return new UserId( UUID.fromString( claim ));
    }

    @Override
    @Transactional
    public void invalidate( final String token ){
        final UUID id = this.getSubject( token );
        this.securityTokenRepository.deleteById( id );
    }

    @Override
    @Transactional
    public void invalidate( final AccountId id ){
        this.securityTokenRepository.deleteAllByAccount_Id( id.value() );
    }

    @Override
    @Transactional
    public void invalidate( final AccountId id, final List<String> ipList ){
        this.securityTokenRepository.deleteAllByIpInAndAccount_Id( ipList, id.value() );
    }

    @Override
    public boolean validateAccessToken( final String token, final String ip ) {
        try {
            return this.validate( token, this.accessTokenSecret, ip );
        }
        catch( final Exception e ) {
            return false;
        }
    }

    @Override
    public boolean validateAccessToken( final String token ){
        return this.validateAccessToken( null, token );
    }

    @Override
    public boolean validateRefreshToken( final String token, final String ip ) {
        try {
            return this.validate( token, this.refreshTokenSecret, ip );
        }
        catch( final Exception e ) {
            return false;
        }
    }

    @Override
    public boolean validateRefreshToken( final String token ) {
        return this.validateRefreshToken( null, token );
    }

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| HELPER METHODS |--------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    private String create( final UUID id, final String secret, final Long duration, final UUID version, final AccountId accountid, final UserId userid ){
        return Jwts.builder()
                .setSubject( id.toString() )
                .claim( CLAIM_VERSION, version )
                .claim( CLAIM_ACCOUNT, accountid.value() )
                .claim( CLAIM_USER, userid.value() )
                .setIssuedAt( new Date())
                .setExpiration( Date.from( Instant.now().plus( duration, ChronoUnit.MILLIS )))
                .signWith( Keys.hmacShaKeyFor( secret.getBytes( StandardCharsets.UTF_8 )))
                .compact();
    }

    private Optional<Jws<Claims>> parse( final String token ){
        Jws<Claims> jws;
        try{
            jws = this.parse( token, this.accessTokenSecret );
        }
        catch( final Exception e ) {
            jws = this.parse( token, this.refreshTokenSecret );
        }

        return Optional.ofNullable( jws );
    }

    private Jws<Claims> parse( final String token, final String sign ){
        return Jwts.parserBuilder()
                    .setSigningKey( sign.getBytes( StandardCharsets.UTF_8 ))
                    .build()
                    .parseClaimsJws( token );
    }

    private boolean validate( final String token, final String secret, final String ip ) {
        try {
            this.parse( token, secret );
            final UUID id = this.getSubject( token );
            final UUID version = this.getVersion( token );
            final SecurityToken entity = this.securityTokenRepository.findById( id )
                    .orElseThrow( () -> new InvalidTokenException( token ));
            if( entity.getIp().isEmpty() ) return Objects.equals( entity.getVersion(), version );
            else return Objects.equals( entity.getVersion(), version ) && Objects.equals( entity.getIp().orElse( null ), ip );
        }
        catch( final Exception e ) {
            return false;
        }
    }

// ------------------------------------------------------------------------------------------------------------------ \\

}
