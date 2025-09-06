package com.codenaiten.template.rest.app.old.policy;

import com.codenaiten.template.rest.app.old.AppMessage;
import com.codenaiten.template.rest.app.old.entity.Account;
import com.codenaiten.template.rest.app.old.exception.security.access.*;
import com.codenaiten.template.rest.app.old.spec.access.account.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Policy que permite controlar el acceso a los recursos de tipo {@link Account}.
 */
@Slf4j
@RequiredArgsConstructor
public class AccountAccessPolicy {

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| METHODS |------------------------------------------------------------------------------------------------------ \\
// ------------------------------------------------------------------------------------------------------------------ \\

    /**
     * Permite comprobar si se tiene permiso para realizar una consulta sobre los recursos de tipo {@link Account}.
     *
     * @param requester {@link Account} que representa la cuenta de usuario que se va a evaluar si tiene permiso.
     *
     * @throws QueryAccessException si no tiene permiso para realizar la consulta.
     *
     * @see AccountQuerySpec
     */
    public void checkQuery( final Account requester ){
        if( new AccountQuerySpec().not().test( requester ))
            throw new QueryAccessException( AppMessage.ERROR_SECURITY_ACCOUNT_QUERY_NOT_ALLOWED, requester.getId() );
    }

    /**
     * Permite comprobar si se tiene permiso para leer los recursos de tipo {@link Account}.
     *
     * @param requester {@link Account} que representa la cuenta de usuario que se va a evaluar si tiene permiso.
     * @param account {@link Account} que representa la cuenta de usuario que se va a consultar.
     *
     * @throws ReadAccessException si no tiene permiso para leer el recurso.
     *
     * @see AccountReadSpec
     */
    public void checkRead( final Account requester, final Account account ){
        if( new AccountReadSpec( account ).not().test( requester ))
            throw new ReadAccessException( AppMessage.ERROR_SECURITY_ACCOUNT_READ_NOT_ALLOWED, requester.getId(), account.getId() );
    }

    /**
     * Permite comprobar si se tiene permiso para crear los recursos de tipo {@link Account}.
     *
     * @param requester {@link Account} que representa la cuenta de usuario que se va a evaluar si tiene permiso.
     *
     * @throws CreateAccessException si no tiene permiso para crear el recurso.
     *
     * @see AccountCreateSpec
     */
    public void checkCreate( final Account requester ){
        if( new AccountCreateSpec().not().test( requester ))
            throw new CreateAccessException( AppMessage.ERROR_SECURITY_ACCOUNT_CREATE_NOT_ALLOWED, requester.getId() );
    }

    /**
     * Permite comprobar si se tiene permiso para actualizar los recursos de tipo {@link Account}.
     *
     * @param requester {@link Account} que representa la cuenta de usuario que se va a evaluar si tiene permiso.
     * @param account {@link Account} que representa la cuenta de usuario que se va a actualizar.
     *
     * @throws WriteAccessException si no tiene permiso para actualizar el recurso.
     *
     * @see AccountWriteSpec
     */
    public void checkWrite( final Account requester, final Account account ){
        if( new AccountWriteSpec( account ).not().test( requester ))
            throw new WriteAccessException( AppMessage.ERROR_SECURITY_ACCOUNT_WRITE_NOT_ALLOWED, requester.getId(), account.getId() );
    }

    /**
     * Permite comprobar si se tiene permiso para eliminar los recursos de tipo {@link Account}.
     *
     * @param requester {@link Account} que representa la cuenta de usuario que se va a evaluar si tiene permiso.
     * @param account {@link Account} que representa la cuenta de usuario que se va a eliminar.
     *
     * @see DeleteAccessException if no tiene permiso para eliminar el recurso.
     */
    public void checkDelete( final Account requester, final Account account ){
        if( new AccountDeleteSpec( account ).not().test( requester ))
            throw new DeleteAccessException( AppMessage.ERROR_SECURITY_ACCOUNT_DELETE_NOT_ALLOWED, requester.getId(), account.getId() );
    }

// ------------------------------------------------------------------------------------------------------------------ \\

}
