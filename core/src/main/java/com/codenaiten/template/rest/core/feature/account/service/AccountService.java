package com.codenaiten.template.rest.core.feature.account.service;

import com.codenaiten.template.rest.core.feature.account.Account;

public interface AccountService{
    void create( Account account );
    void update( Account account );
    void delete( Account account );
}
