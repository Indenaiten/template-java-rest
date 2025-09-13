package com.codenaiten.template.rest.core.feature.account;

import com.codenaiten.template.rest.core.CoreMessageKey;
import com.codenaiten.template.rest.core.feature.account.constraint.AccountIdConstraint;
import com.codenaiten.template.rest.core.feature.account.constraint.AccountOwnerConstraint;
import com.codenaiten.template.rest.core.feature.user.vo.UserId;
import com.codenaiten.template.rest.core.shared.constraint.ConstraintViolation;
import com.codenaiten.template.rest.core.shared.exception.ConstraintException;
import com.codenaiten.template.rest.core.shared.vo.Language;
import org.junit.jupiter.api.BeforeEach;
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
class AccountFactoryTest{

    private AccountIdConstraint accountIdConstraint;
    private AccountOwnerConstraint accountOwnerConstraint;

//--------------------------------------------------------------------------------------------------------------------\\
//---| SETUP |--------------------------------------------------------------------------------------------------------\\
//--------------------------------------------------------------------------------------------------------------------\\

    @BeforeEach
    void setUp(){
        this.accountIdConstraint = mock( AccountIdConstraint.class );
        this.accountOwnerConstraint = mock( AccountOwnerConstraint.class );
    }

//--------------------------------------------------------------------------------------------------------------------\\
//---| TESTS |--------------------------------------------------------------------------------------------------------\\
//--------------------------------------------------------------------------------------------------------------------\\

    /**
     * Test que comprueba que la clase {@link AccountFactory} construye una {@link Account} con datos válidos que no 
     * violan las restricciones requeridas por la factoría para construir la {@link Account}.
     */
    @Test
    @DisplayName( "Given valid account data that does not violate constraints, When build is called, Then the account is created successfully" )
    void givenValidAccountDataThatNotViolationConstraints_whenBuildCalled_thenAccountCreatedSuccessfully(){
        // Mocks Setting
        when( this.accountIdConstraint.check( any( Account.class ))).thenReturn( Optional.empty() );
        when( this.accountOwnerConstraint.check( any( Account.class ))).thenReturn( Optional.empty() );

        // Given valid Account data
        final UserId owner = UserId.random();
        final Language language = Language.ES_ES;

        // When building the Account
        final Account account = Account.create( owner ).language( language )
                .build( this.accountIdConstraint,
                        this.accountOwnerConstraint );

        // Then check that constraints were checked
        verify( this.accountIdConstraint ).check( any( Account.class ));
        verify( this.accountOwnerConstraint ).check( any( Account.class ));

        // Then assert Account was created successfully with correct data
        assertNotNull( account.getId() );
        assertEquals( owner, account.getOwner() );
        assertEquals( language, account.getLang() );
        assertNotNull( account.getCreatedAt() );
        assertTrue( account.getUpdatedAt().isEmpty() );
    }

//--------------------------------------------------------------------------------------------------------------------\\

    /**
     * Test que comprueba que la clase {@link AccountFactory} lanza la excepción {@link ConstraintException} cuando se
     * intenta construir una {@link Account} con datos válidos que violan las restricciones requeridas por la factoría
     * para construir la {@link Account}.
     */
    @Test
    @DisplayName( "Given account data that violates constraints, When build is called, Then a ConstraintException is thrown" )
    void givenValidAccountDataThatViolationConstraints_whenBuildCalled_thenThrowConstraintException(){
        // Mocks Setting
        final ConstraintViolation<?> violation = mock( ConstraintViolation.class );
        when( this.accountIdConstraint.check( any( Account.class ))).thenReturn( Optional.of( violation ));
        when( this.accountOwnerConstraint.check( any( Account.class ))).thenReturn( Optional.of( violation ));

        // Given valid Account data
        final UserId owner = UserId.random();
        final Language language = Language.ES_ES;

        // When building the Account
        final AccountFactory factory = Account.create( owner ).language( language );
        final ConstraintException exception = assertThrows( ConstraintException.class,
                () -> factory.build( this.accountIdConstraint,
                                     this.accountOwnerConstraint ));

        // Then check that constraints were checked
        verify( this.accountIdConstraint ).check( any( Account.class ));
        verify( this.accountOwnerConstraint ).check( any( Account.class ));

        // Then assert thrown exception
        assertEquals( CoreMessageKey.ERROR_CONSTRAINT_GENERIC.getMessage(), exception.getMessage() );
        final List<ConstraintViolation<?>> violations = exception.getViolations();
        assertEquals( 2, violations.size() );
    }

//--------------------------------------------------------------------------------------------------------------------\\

}
