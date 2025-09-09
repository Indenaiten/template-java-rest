package com.codenaiten.template.rest.core.layer.spec;

import com.codenaiten.template.rest.core.layer.entity.User;
import com.codenaiten.template.rest.core.layer.spi.MinAgeProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserMinimumAgeSpec implements Specification<User>{

    private final MinAgeProvider minAgeProvider;

//--------------------------------------------------------------------------------------------------------------------\\
//---| IMPLEMENTED METHODS |------------------------------------------------------------------------------------------\\
//--------------------------------------------------------------------------------------------------------------------\\

    @Override
    public boolean test( final User user ){
        if( Objects.isNull( user )) throw new IllegalArgumentException( "User to test minimum age is required" );
        final Integer minAge = this.minAgeProvider.getMinAge();
        final Integer age = user.getAge();
        return age >= minAge;
    }

//--------------------------------------------------------------------------------------------------------------------\\

}
