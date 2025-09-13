package com.codenaiten.template.rest.core.feature.media;

import com.codenaiten.template.rest.core.feature.media.vo.MediaContentSize;
import com.codenaiten.template.rest.core.feature.media.vo.MediaContentType;
import com.codenaiten.template.rest.core.feature.media.vo.MediaId;
import com.codenaiten.template.rest.core.feature.user.vo.UserId;
import com.codenaiten.template.rest.core.shared.exception.ConstraintException;
import com.codenaiten.template.rest.core.shared.vo.Timestamp;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@Tag( "Entity" )
@Tag( "Media" )
@Tag( "Editor" )
@DisplayName( "Tests for MediaEditor" )
class MediaEditorTest{

//--------------------------------------------------------------------------------------------------------------------\\
//---| HELPER METHODS |--------------------------------------------------------------------------------------------------------\\
//--------------------------------------------------------------------------------------------------------------------\\

    /**
     * Crea un nuevo {@link Media} para utilizarlo en los tests.
     *
     * @return {@link Media} para utilizarlo en los tests.
     */
    public static Media createNewMedia(){
        final MediaId id = MediaId.random();
        final UserId owner = UserId.of( "7575299c-c194-4af6-82b1-5fb29664f724" );
        final MediaContentType contentType = MediaContentType.IMAGE_JPEG;
        final MediaContentSize contentSize = new MediaContentSize( 100L );
        final String namespace = "test";
        final Timestamp createdAt = Timestamp.now();

        return new Media( id, owner, contentType, contentSize, namespace, createdAt, null );
    }

//--------------------------------------------------------------------------------------------------------------------\\
//---| TESTS |--------------------------------------------------------------------------------------------------------\\
//--------------------------------------------------------------------------------------------------------------------\\

    /**
     * Test que comprueba que la clase {@link MediaEditor} actualiza un {@link Media} con nuevos datos, el editor
     * devuelve {@code true}, actualiza los correspondientes campos incluyendo el campo {@code updatedAt} con el
     * {@link Timestamp} de la fecha y hora de actualización en la instacia {@link Media} y no lanza la excepción
     * {@link ConstraintException}.
     */
    @Test
    @DisplayName( "Given valid media data that does not violate constraints, When apply is called, Then it returns true, the media is updated, and no constraints are triggered" )
    void givenValidMediaDataThatNotViolationConstraints_whenApplyCalled_thenReturnTrueAndMediaUpdatedAndConstraintsNotTriggered(){
        // Given Valid data
        final MediaContentType contentType = MediaContentType.IMAGE_PNG;
        final MediaContentSize contentSize = new MediaContentSize( 50L );
        final String namespace = "new";

        // Given Valid Media
        final Media media = createNewMedia();

        // Update Media Data
        final MediaEditor editor = media.update().contentType( contentType ).contentSize( contentSize ).namespace( namespace );

        // Check Media Data
        assertNotEquals( contentType, media.getContentType() );
        assertNotEquals( contentSize, media.getContentSize() );
        assertNotEquals( namespace, media.getNamespace().orElse( null ));
        assertTrue( media.getUpdatedAt().isEmpty(), "UpdatedAt should be empty" );

        // When editor apply changes
        final boolean changed = assertDoesNotThrow( editor::apply );

        // Then assert that return true and changes were made
        assertTrue( changed, "Should have changed" );
        assertEquals( contentType, media.getContentType() );
        assertEquals( contentSize, media.getContentSize() );
        assertEquals( namespace, media.getNamespace().orElse( null ));
        assertTrue( media.getUpdatedAt().isPresent(), "UpdatedAt should not be empty" );
    }

//--------------------------------------------------------------------------------------------------------------------\\

    /**
     * Test que comprueba que la clase {@link MediaEditor} actualiza un {@link Media} sin nuevos datos, el editor
     * devuelve {@code false}, no actualiza ningún campo de la instacia {@link Media} y no lanza la excepción 
     * {@link ConstraintException}.
     */
    @Test
    @DisplayName( "Given no changes, When apply is called, Then it returns false, the media is not updated, and no constraints are triggered")
    void givenNoChanges_whenApplyCalled_thenReturnFalseAndMediaNotUpdatedAndConstraintsNotTriggered(){
        // Given Valid Media
        final Media media = createNewMedia();

        // Check that UpdatedAt is empty
        assertTrue( media.getUpdatedAt().isEmpty(), "UpdatedAt should be empty" );

        // When update without changes and apply
        final boolean changed = assertDoesNotThrow( media.update()::apply );

        // Then assert that return false and no changes were made
        assertFalse( changed, "Should not have changed" );
        assertTrue( media.getUpdatedAt().isEmpty(), "UpdatedAt should still be empty" );
    }

//--------------------------------------------------------------------------------------------------------------------\\

    /**
     * Test que comrpueba que la clase {@link MediaEditor} devuelve {@code false} si no se han actualizado los campos 
     * del {@link Media} con un nuevo valor.
     */
    @Test
    @DisplayName( "Given no changes, When hasChanges is called, Then it returns false" )
    void givenNoChanges_whenHasChangesCalled_thenReturnFalse(){
        // Given Valid Media
        final Media media = createNewMedia();

        // When Update and hasChanges called Then return false
        final String message = "Should not have changes";
        assertFalse( media.update().contentType( media.getContentType() ).hasChanges(), message );
        assertFalse( media.update().contentSize( media.getContentSize() ).hasChanges(), message );
        assertFalse( media.update().namespace( media.getNamespace().orElse( null )).hasChanges(), message );
    }

//--------------------------------------------------------------------------------------------------------------------\\

    /**
     * Test que comrpueba que la clase {@link MediaEditor} devuelve {@code true} si se han actualizado alguno de los
     * campos de la {@link Media}.
     */
    @Test
    @DisplayName( "Given valid media data, When hasChanges is called, Then it returns true" )
    void givenValidMediaData_whenHasChangesCalled_thenReturnTrue(){
        // Given Valid Media
        final Media media = createNewMedia();

        // When Update and hasChanges called Then return true
        final String message = "Should have changes";
        assertTrue( media.update().contentType( MediaContentType.IMAGE_PNG ).hasChanges(), message );
        assertTrue( media.update().contentSize( new MediaContentSize( 50L )).hasChanges(), message );
        assertTrue( media.update().namespace( "new" ).hasChanges(), message );
    }

//--------------------------------------------------------------------------------------------------------------------\\

}
