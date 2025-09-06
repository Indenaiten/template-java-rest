package com.codenaiten.template.rest.core.service;

import com.codenaiten.template.rest.core.entity.User;

public interface UserService{
    void create( User user );
    void update( User user );
    void delete( User user );
}
