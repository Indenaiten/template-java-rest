package com.codenaiten.template.rest.core.service;

import com.codenaiten.template.rest.core.entity.Account;

public interface AccountService{
    void create( Account account );
    void update( Account account );
    void delete( Account account );
}
