package com.codenaiten.template.rest.core.feature.account.spi;

import com.codenaiten.template.rest.core.feature.account.Account;
import com.codenaiten.template.rest.core.feature.account.vo.AccountId;
import com.codenaiten.template.rest.core.feature.user.vo.UserId;
import com.codenaiten.template.rest.core.shared.vo.Email;

import java.util.Optional;

public interface AccountRepository{

//--------------------------------------------------------------------------------------------------------------------\\

    Account save( Account account );

//--------------------------------------------------------------------------------------------------------------------\\

    Optional<Account> find( AccountId id );

//--------------------------------------------------------------------------------------------------------------------\\

    Optional<Account> find( UserId id );

//--------------------------------------------------------------------------------------------------------------------\\

    boolean exists( AccountId id );

//--------------------------------------------------------------------------------------------------------------------\\

    boolean exists( UserId id );

//--------------------------------------------------------------------------------------------------------------------\\

    boolean exists( Email email );

//--------------------------------------------------------------------------------------------------------------------\\

}
