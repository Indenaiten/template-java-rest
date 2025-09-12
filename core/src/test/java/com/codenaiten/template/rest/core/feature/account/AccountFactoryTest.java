package com.codenaiten.template.rest.core.feature.account;

import com.codenaiten.template.rest.core.CoreMessageKey;
import com.codenaiten.template.rest.core.feature.account.constraint.AccountIdConstraint;
import com.codenaiten.template.rest.core.feature.account.constraint.AccountOwnerConstraint;
import com.codenaiten.template.rest.core.feature.user.vo.UserId;
import com.codenaiten.template.rest.core.shared.constraint.ConstraintViolation;
import com.codenaiten.template.rest.core.shared.exception.ConstraintException;
import com.codenaiten.template.rest.core.shared.vo.Language;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@Tag( "Entity" )
@Tag( "Account" )
@Tag( "Factory" )
@DisplayName( "Tests for AccountFactory" )
class AccountFactoryTest {

//--------------------------------------------------------------------------------------------------------------------\\

    @Test
    @DisplayName( "Account creation succeeds when no constraint violations exist" )
    void givenValidAccountDataAndNoConstraintViolations_whenBuildingAccount_thenAccountIsCreatedSuccessfully(){
        // Given mocked AccountIdConstraint that return no violations
        final AccountIdConstraint accountIdConstraint = mock( AccountIdConstraint.class );
        when( accountIdConstraint.check( any( Account.class ))).thenReturn( Optional.empty() );

        // Given mocked AccountOwnerConstraint that return no violations
        final AccountOwnerConstraint accountOwnerConstraint = mock( AccountOwnerConstraint.class );
        when( accountOwnerConstraint.check( any( Account.class ))).thenReturn( Optional.empty() );

        // Given valid Account data
        final UserId owner = UserId.random();
        final Language language = Language.ES_ES;

        // When building the Account
        final Account account = Account.create( owner ).language( language )
                .build( accountIdConstraint, accountOwnerConstraint );

        // Then check that constraints were checked
        verify( accountIdConstraint ).check( any( Account.class ));
        verify( accountOwnerConstraint ).check( any( Account.class ));

        // Then assert Account was created successfully with correct data
        assertNotNull( account.getId() );
        assertEquals( owner, account.getOwner() );
        assertEquals( language, account.getLang() );
        assertNotNull( account.getCreatedAt() );
        assertTrue( account.getUpdatedAt().isEmpty() );
    }

//--------------------------------------------------------------------------------------------------------------------\\

    @Test
    @DisplayName( "Account creation fails with ConstraintException when constraint violations exist" )
    void givenValidAccountDataAndConstraintViolations_whenBuildingAccount_thenThrowConstraintException(){
        // Given mocked ConstraintViolation that constraints will be return
        final ConstraintViolation<?> violation = mock( ConstraintViolation.class );

        // Given mocked AccountIdConstraint that return violations
        final AccountIdConstraint accountIdConstraint = mock( AccountIdConstraint.class );
        when( accountIdConstraint.check( any( Account.class ))).thenReturn( Optional.of( violation ));

        // Given mocked AccountOwnerConstraint that return violations
        final AccountOwnerConstraint accountOwnerConstraint = mock( AccountOwnerConstraint.class );
        when( accountOwnerConstraint.check( any( Account.class ))).thenReturn( Optional.of( violation ));

        // Given valid Account data
        final UserId owner = UserId.random();
        final Language language = Language.ES_ES;

        // When building the Account
        final AccountFactory factory = Account.create( owner ).language( language );
        final ConstraintException exception = assertThrows( ConstraintException.class, () ->
                factory.build( accountIdConstraint, accountOwnerConstraint ));

        // Then check that constraints were checked
        verify( accountIdConstraint ).check( any( Account.class ));
        verify( accountOwnerConstraint ).check( any( Account.class ));

        // Then assert thrown exception
        assertEquals( CoreMessageKey.ERROR_CONSTRAINT_GENERIC.getMessage(), exception.getMessage() );
        final List<ConstraintViolation<?>> violations = exception.getViolations();
        assertEquals( 2, violations.size() );
    }

//--------------------------------------------------------------------------------------------------------------------\\//--------------------------------------------------------------------------------------------------------------------\\

}
