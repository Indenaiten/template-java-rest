package com.codenaiten.template.rest.core.feature.account;

import com.codenaiten.template.rest.core.feature.account.vo.AccountId;
import com.codenaiten.template.rest.core.feature.user.vo.UserId;
import com.codenaiten.template.rest.core.shared.exception.ConstraintException;
import com.codenaiten.template.rest.core.shared.vo.Language;
import com.codenaiten.template.rest.core.shared.vo.Timestamp;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@Tag( "Entity" )
@Tag( "Account" )
@Tag( "Editor" )
@DisplayName( "Tests for AccountEditor" )
class AccountEditorTest{

//--------------------------------------------------------------------------------------------------------------------\\
//---| HELPER METHODS |--------------------------------------------------------------------------------------------------------\\
//--------------------------------------------------------------------------------------------------------------------\\

    /**
     * Crea un nueva {@link Account} para utilizarla en los tests.
     *
     * @return {@link Account} para utilizarla en los tests.
     */
    public static Account createNewAccount(){
        final AccountId id = AccountId.random();
        final UserId owner = UserId.of( "7575299c-c194-4af6-82b1-5fb29664f724" );
        final Language language = Language.ES_ES;
        final Timestamp createdAt = Timestamp.now();

        return new Account( id, owner, language, createdAt, null );
    }

//--------------------------------------------------------------------------------------------------------------------\\
//---| TESTS |--------------------------------------------------------------------------------------------------------\\
//--------------------------------------------------------------------------------------------------------------------\\

    /**
     * Test que comprueba que la clase {@link AccountEditor} actualiza un {@link Account} con nuevos datos, el editor
     * devuelve {@code true}, actualiza los correspondientes campos incluyendo el campo {@code updatedAt} con el
     * {@link Timestamp} de la fecha y hora de actualización en la instacia {@link Account} y no lanza la excepción
     * {@link ConstraintException}.
     */
    @Test
    @DisplayName( "Given valid account data that does not violate constraints, When apply is called, Then it returns true, the account is updated, and no constraints are triggered" )
    void givenValidAccountDataThatNotViolationConstraints_whenApplyCalled_thenReturnTrueAndAccountUpdatedAndConstraintsNotTriggered(){
        // Given Valid data
        final Language language = Language.EN_EN;

        // Given Valid Account
        final Account account = createNewAccount();

        // Update Account Data
        final AccountEditor editor = account.update().language( language );

        // Check Account Data
        assertNotEquals( language, account.getLang() );
        assertTrue( account.getUpdatedAt().isEmpty(), "UpdatedAt should be empty" );

        // When editor apply changes
        final boolean changed = assertDoesNotThrow( editor::apply );

        // Then assert that return true and changes were made
        assertTrue( changed, "Should have changed" );
        assertEquals( language, account.getLang() );
        assertTrue( account.getUpdatedAt().isPresent(), "UpdatedAt should not be empty" );
    }

//--------------------------------------------------------------------------------------------------------------------\\

    /**
     * Test que comprueba que la clase {@link AccountEditor} actualiza una {@link Account} sin nuevos datos, el editor
     * devuelve {@code false}, no actualiza ningún campo de la instacia {@link Account} y no lanza la excepción
     * {@link ConstraintException}.
     */
    @Test
    @DisplayName( "Given no changes, When apply is called, Then it returns false, the account is not updated, and no constraints are triggered")
    void givenNoChanges_whenApplyCalled_thenReturnFalseAndAccountNotUpdatedAndConstraintsNotTriggered(){
        // Given Valid Account
        final Account account = createNewAccount();

        // Check that UpdatedAt is empty
        assertTrue( account.getUpdatedAt().isEmpty(), "UpdatedAt should be empty" );

        // When update without changes and apply
        final boolean changed = assertDoesNotThrow( account.update()::apply );

        // Then assert that return false and no changes were made
        assertFalse( changed, "Should not have changed" );
        assertTrue( account.getUpdatedAt().isEmpty(), "UpdatedAt should still be empty" );
    }

//--------------------------------------------------------------------------------------------------------------------\\

    /**
     * Test que comrpueba que la clase {@link AccountEditor} devuelve {@code false} si no se han actualizado los campos
     * del {@link Account} con un nuevo valor.
     */
    @Test
    @DisplayName( "Given no changes, When hasChanges is called, Then it returns false" )
    void givenNoChanges_whenHasChangesCalled_thenReturnFalse(){
        // Given Valid Account
        final Account account = createNewAccount();

        // When Update and hasChanges called Then return false
        final String message = "Should not have changes";
        assertFalse( account.update().language( account.getLang() ).hasChanges(), message );
    }

//--------------------------------------------------------------------------------------------------------------------\\

    /**
     * Test que comrpueba que la clase {@link AccountEditor} devuelve {@code true} si se han actualizado alguno de los
     * campos de la {@link Account}.
     */
    @Test
    @DisplayName( "Given valid account data, When hasChanges is called, Then it returns true" )
    void givenValidAccountData_whenHasChangesCalled_thenReturnTrue(){
        // Given Valid Account
        final Account account = createNewAccount();

        // When Update and hasChanges called Then return true
        final String message = "Should have changes";
        assertTrue( account.update().language( Language.EN_EN ).hasChanges(), message );
    }

//--------------------------------------------------------------------------------------------------------------------\\

}
