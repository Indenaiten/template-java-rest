package com.codenaiten.template.rest.app.policy;

import com.codenaiten.template.rest.app.AppMessage;
import com.codenaiten.template.rest.app.entity.Account;
import com.codenaiten.template.rest.app.exception.security.access.CreateAccessException;
import com.codenaiten.template.rest.app.exception.security.access.QueryAccessException;
import com.codenaiten.template.rest.app.exception.security.access.ReadAccessException;
import com.codenaiten.template.rest.app.spec.access.account.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class AccountAccessPolicy {

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| METHODS |------------------------------------------------------------------------------------------------------ \\
// ------------------------------------------------------------------------------------------------------------------ \\

    public void checkQuery( final Account requester ){
        if( new AccountQuerySpec().not().test( requester ))
            throw new QueryAccessException( AppMessage.ERROR_SECURITY_ACCOUNT_QUERY_NOT_ALLOWED, requester.getId() );
    }

    public void checkRead( final Account requester, final Account account ){
        if( new AccountReadSpec( account ).not().test( requester ))
            throw new ReadAccessException( AppMessage.ERROR_SECURITY_ACCOUNT_READ_NOT_ALLOWED, requester.getId(), account.getId() );
    }

    public void checkCreate( final Account requester ){
        if( new AccountCreateSpec().not().test( requester ))
            throw new CreateAccessException( AppMessage.ERROR_SECURITY_ACCOUNT_CREATE_NOT_ALLOWED, requester.getId() );
    }

    public void checkWrite( final Account requester, final Account account ){
        if( new AccountWriteSpec( account ).not().test( requester ))
            throw new CreateAccessException( AppMessage.ERROR_SECURITY_ACCOUNT_WRITE_NOT_ALLOWED, requester.getId(), account.getId() );
    }

    public void checkDelete( final Account requester, final Account account ){
        if( new AccountDeleteSpec( account ).not().test( requester ))
            throw new CreateAccessException( AppMessage.ERROR_SECURITY_ACCOUNT_DELETE_NOT_ALLOWED, requester.getId(), account.getId() );
    }

// ------------------------------------------------------------------------------------------------------------------ \\

}
