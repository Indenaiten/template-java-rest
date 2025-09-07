package com.codenaiten.template.rest.app.port;

import com.codenaiten.template.rest.core.feature.user.User;
import com.codenaiten.template.rest.core.feature.user.vo.UserId;
import com.codenaiten.template.rest.core.feature.user.vo.UserUsername;

import java.util.Optional;

public interface UserRepositoryPort{
    User save( User user );
    void delete( User user );
    Optional<User> find( UserId id );
    Optional<User> find( UserUsername username );
    boolean exists( UserId id );
    boolean exists( UserUsername username );
}
