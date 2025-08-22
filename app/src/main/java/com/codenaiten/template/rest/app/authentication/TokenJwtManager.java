package com.codenaiten.template.rest.app.authentication;

import com.codenaiten.template.rest.app.dto.result.AccountInfoResult;
import com.codenaiten.template.rest.app.vo.account.AccountId;
import com.codenaiten.template.rest.app.vo.user.UserId;

//TODO: Añadir el refresh-token para poder actualizar el access-token cuando expira.
//TODO: Crear un Repositorio para almacenar las versiones de los tokens para poder invalidarlos.
public interface TokenJwtManager {

    String create( AccountInfoResult accountInfoResult );
    UserId getSubject( String token );
    AccountId getAccountId( String token );
    boolean check( String token );
}
