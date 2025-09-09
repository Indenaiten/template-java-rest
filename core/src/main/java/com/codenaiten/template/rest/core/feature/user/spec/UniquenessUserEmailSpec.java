package com.codenaiten.template.rest.core.feature.user.spec;

import com.codenaiten.template.rest.core.feature.user.User;
import com.codenaiten.template.rest.core.feature.user.spi.UserRepository;
import com.codenaiten.template.rest.core.shared.spec.Specification;
import com.codenaiten.template.rest.core.shared.vo.Email;
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
