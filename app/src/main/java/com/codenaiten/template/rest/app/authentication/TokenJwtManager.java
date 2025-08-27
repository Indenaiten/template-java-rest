package com.codenaiten.template.rest.app.authentication;

import com.codenaiten.template.rest.app.entity.Account;
import com.codenaiten.template.rest.app.vo.account.AccountId;
import com.codenaiten.template.rest.app.vo.user.UserId;

import java.util.List;
import java.util.UUID;

public interface TokenJwtManager {

    TokenInfo create( Account result, String ip );
    TokenInfo create( Account result );

    TokenInfo refresh( String refreshToken );
    TokenInfo refresh( String refreshToken, String ip );

    UUID getSubject( String token );
    UUID getVersion( String token );
    AccountId getAccountId( String token );
    UserId getUserId( String token );

    void invalidate( String token );
    void invalidate( AccountId id );
    void invalidate( AccountId id, List<String> ipList );

    boolean validateAccessToken( String token, String ip );
    boolean validateAccessToken( String token );

    boolean validateRefreshToken( String token, String ip );
    boolean validateRefreshToken( String token );
}
