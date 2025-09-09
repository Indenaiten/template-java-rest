package com.codenaiten.template.rest.core.feature.user.spec;

import com.codenaiten.template.rest.core.feature.user.User;
import com.codenaiten.template.rest.core.feature.user.spi.MinimumUserAgeProvider;
import com.codenaiten.template.rest.core.shared.spec.Specification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Slf4j
@Component
@RequiredArgsConstructor
public class MinimumUserAgeSpec implements Specification<User>{

    private final MinimumUserAgeProvider minimumUserAgeProvider;

//--------------------------------------------------------------------------------------------------------------------\\
//---| IMPLEMENTED METHODS |------------------------------------------------------------------------------------------\\
//--------------------------------------------------------------------------------------------------------------------\\

    @Override
    public boolean test( final User user ){
        if( Objects.isNull( user )) throw new IllegalArgumentException( "User to test minimum age is required" );
        final Integer minAge = this.minimumUserAgeProvider.getMinimumAge();
        final Integer age = user.getAge();
        return age >= minAge;
    }

//--------------------------------------------------------------------------------------------------------------------\\

}
