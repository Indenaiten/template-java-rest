package com.codenaiten.template.rest.app.port;

import com.codenaiten.template.rest.core.entity.Account;
import com.codenaiten.template.rest.core.vo.Email;
import com.codenaiten.template.rest.core.vo.account.AccountId;

import java.util.Optional;

public interface AccountRepositoryPort{
    Account save( Account account );
    void delete( Account account );
    Optional<Account> find( AccountId id );
    Optional<Account> find( Email email );
    boolean exists( AccountId id );
    boolean exists( Email email );
}
