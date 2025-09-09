package com.codenaiten.template.rest.core.feature.user.policy;

import com.codenaiten.template.rest.core.feature.user.User;
import com.codenaiten.template.rest.core.feature.user.spec.UniquenessUserEmailSpec;
import com.codenaiten.template.rest.core.shared.exception.AppException;
import com.codenaiten.template.rest.core.shared.policy.Policy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Slf4j
@Component
@RequiredArgsConstructor
public class ValidUserEmailPolicy implements Policy<User> {

    private final UniquenessUserEmailSpec uniquenessUserEmailSpec;

//--------------------------------------------------------------------------------------------------------------------\\
//---| IMPLEMENTED METHODS |------------------------------------------------------------------------------------------\\
//--------------------------------------------------------------------------------------------------------------------\\

    @Override
    public void check( final User candidate ){
        if( Objects.isNull( candidate )) throw new IllegalArgumentException( "User to check valid email is required" );
        if( this.uniquenessUserEmailSpec.not().test( candidate )) throw new AppException();
    }

//--------------------------------------------------------------------------------------------------------------------\\

}
