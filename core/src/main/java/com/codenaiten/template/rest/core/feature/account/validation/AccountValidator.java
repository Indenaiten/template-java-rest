package com.codenaiten.template.rest.core.feature.account.validation;

import com.codenaiten.template.rest.core.feature.account.Account;
import com.codenaiten.template.rest.core.shared.exception.ConstraintException;
import com.codenaiten.template.rest.core.shared.validator.Validator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class AccountValidator implements Validator<Account> {

    @Override
    public List<ConstraintException> validate( final Account candidate ){
        throw new UnsupportedOperationException();
    }
}
