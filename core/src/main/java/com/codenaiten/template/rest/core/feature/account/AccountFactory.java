package com.codenaiten.template.rest.core.feature.account;

import com.codenaiten.template.rest.core.feature.account.validation.AccountValidator;
import com.codenaiten.template.rest.core.feature.account.vo.AccountId;
import com.codenaiten.template.rest.core.feature.account.vo.Language;
import com.codenaiten.template.rest.core.feature.user.User;
import com.codenaiten.template.rest.core.shared.exception.ConstraintException;
import com.codenaiten.template.rest.core.shared.exception.ValidationException;
import com.codenaiten.template.rest.core.shared.vo.Timestamp;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class AccountFactory {

    private final User owner;

// -------------------------------------------------------------------------------------------------------------- \\

    private Language language;

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| SETTERS |---------------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    public AccountFactory language( final Language language ){
        this.language = language;
        return this;
    }

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| BUILDER METHOD |--------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    public Account build( final AccountValidator validator ){
        // Step 01: Create Account
        final AccountId id = AccountId.random();
        final Timestamp now = Timestamp.now();
        final Account account = Account.builder().id( id ).owner( this.owner ).lang( this.language ).createdAt( now )
                .build();

        // Step 02: Validate Account
        final List<ConstraintException> violations = validator.validate( account );
        if( !violations.isEmpty() ) throw new ValidationException( violations );

        // Step 03: Return Account
        return account;
    }

// ------------------------------------------------------------------------------------------------------------------ \\

}
