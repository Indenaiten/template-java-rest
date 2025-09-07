package com.codenaiten.template.rest.core.feature.user.service;

import com.codenaiten.template.rest.core.feature.user.User;

public interface UserService{
    void create( User user );
    void update( User user );
    void delete( User user );
}
