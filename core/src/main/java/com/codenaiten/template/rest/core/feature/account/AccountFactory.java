package com.codenaiten.template.rest.core.feature.account;

import com.codenaiten.template.rest.core.feature.account.constraint.AccountIdConstraint;
import com.codenaiten.template.rest.core.feature.account.constraint.AccountOwnerConstraint;
import com.codenaiten.template.rest.core.feature.account.vo.AccountId;
import com.codenaiten.template.rest.core.feature.user.vo.UserId;
import com.codenaiten.template.rest.core.shared.constraint.ConstraintViolation;
import com.codenaiten.template.rest.core.shared.exception.ConstraintException;
import com.codenaiten.template.rest.core.shared.vo.Language;
import com.codenaiten.template.rest.core.shared.vo.Timestamp;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class AccountFactory {

    private final UserId owner;

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

    public Account build( final AccountIdConstraint accountIdConstraint, final AccountOwnerConstraint accountOwnerConstraint ){
        // Step 01: Create Account
        final AccountId id = AccountId.random();
        final Timestamp now = Timestamp.now();
        final Account account = new Account( id, this.owner, this.language, now, null );

        // Step 02: Validate Account
        final List<ConstraintViolation<?>> violations = new ArrayList<>();
        accountIdConstraint.check( account ).ifPresent( violations::add );
        accountOwnerConstraint.check( account ).ifPresent( violations::add );
        if( !violations.isEmpty() ) throw new ConstraintException( violations );

        // Step 03: Return Account
        return account;
    }

// ------------------------------------------------------------------------------------------------------------------ \\

}
