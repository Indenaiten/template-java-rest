package com.codenaiten.template.rest.app.port;

import com.codenaiten.template.rest.core.feature.account.Account;
import com.codenaiten.template.rest.core.feature.account.vo.AccountId;
import com.codenaiten.template.rest.core.shared.vo.common.Email;

import java.util.Optional;

public interface AccountRepositoryPort{
    Account save( Account account );
    void delete( Account account );
    Optional<Account> find( AccountId id );
    Optional<Account> find( Email email );
    boolean exists( AccountId id );
    boolean exists( Email email );
}
