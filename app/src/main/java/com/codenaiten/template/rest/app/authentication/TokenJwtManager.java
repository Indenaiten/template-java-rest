package com.codenaiten.template.rest.app.authentication;

import com.codenaiten.template.rest.app.dto.result.AccountInfoResult;
import com.codenaiten.template.rest.app.vo.account.AccountId;
import com.codenaiten.template.rest.app.vo.user.UserId;

public interface TokenJwtManager {

    String createAccessToken( AccountInfoResult accountInfoResult );
    String createRefreshToken( AccountInfoResult accountInfoResult );
    UserId getSubject( String token );
    UserId getSubjectFromRefreshToken( String refreshToken );
    AccountId getAccountId( String token );
    boolean check( String token );
    boolean checkRefreshToken( String refreshToken );
    void invalidateUserTokens( UserId userId );
    void invalidateSpecificTokens( UserId userId, String accessToken, String refreshToken );
}
