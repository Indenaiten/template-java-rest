package com.codenaiten.template.rest.core.layer.spec;

import com.codenaiten.template.rest.core.layer.entity.User;
import com.codenaiten.template.rest.core.layer.spi.UserRepository;
import com.codenaiten.template.rest.core.layer.vo.Email;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Slf4j
@Component
@RequiredArgsConstructor
public class UniquenessUserEmailSpec implements Specification<User>{

    private final UserRepository userRepository;

//--------------------------------------------------------------------------------------------------------------------\\
//---| IMPLEMENTED METHODS |------------------------------------------------------------------------------------------\\
//--------------------------------------------------------------------------------------------------------------------\\

    @Override
    public boolean test( final User candidate ){
        if( Objects.isNull( candidate )) throw new IllegalArgumentException( "User to test email uniqueness is required" );
        final Email email = candidate.getEmail();
        return !this.userRepository.exists( email );
    }

//--------------------------------------------------------------------------------------------------------------------\\

}
