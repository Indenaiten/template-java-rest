package com.codenaiten.template.rest.core.feature.user.validation;

import com.codenaiten.template.rest.core.feature.user.User;
import com.codenaiten.template.rest.core.shared.exception.ConstraintException;
import com.codenaiten.template.rest.core.shared.validator.Validator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserValidator implements Validator<User> {

    @Override
    public List<ConstraintException> validate( final User candidate ){
        throw new UnsupportedOperationException();
    }
}
