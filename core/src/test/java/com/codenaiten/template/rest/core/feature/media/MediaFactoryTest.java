package com.codenaiten.template.rest.core.feature.media;

import com.codenaiten.template.rest.core.CoreMessageKey;
import com.codenaiten.template.rest.core.feature.media.constraint.MediaIdConstraint;
import com.codenaiten.template.rest.core.feature.media.constraint.MediaOwnerConstraint;
import com.codenaiten.template.rest.core.feature.media.vo.MediaContentSize;
import com.codenaiten.template.rest.core.feature.media.vo.MediaContentType;
import com.codenaiten.template.rest.core.feature.user.vo.UserId;
import com.codenaiten.template.rest.core.shared.constraint.ConstraintViolation;
import com.codenaiten.template.rest.core.shared.exception.ConstraintException;
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
@Tag( "Media" )
@Tag( "Factory" )
@DisplayName( "Tests for MediaFactory" )
class MediaFactoryTest{

    private MediaIdConstraint mediaIdConstraint;
    private MediaOwnerConstraint mediaOwnerConstraint;

//--------------------------------------------------------------------------------------------------------------------\\
//---| SETUP |--------------------------------------------------------------------------------------------------------\\
//--------------------------------------------------------------------------------------------------------------------\\

    @BeforeEach
    void setUp(){
        this.mediaIdConstraint = mock( MediaIdConstraint.class );
        this.mediaOwnerConstraint = mock( MediaOwnerConstraint.class );
    }

//--------------------------------------------------------------------------------------------------------------------\\
//---| TESTS |--------------------------------------------------------------------------------------------------------\\
//--------------------------------------------------------------------------------------------------------------------\\

    /**
     * Test que comprueba que la clase {@link MediaFactory} construye un {@link Media} con datos válidos que noviolan las
     * restricciones requeridas por la factoría para construir el {@link Media}.
     */
    @Test
    @DisplayName( "Given valid media data that does not violate constraints, When build is called, Then the media is created successfully" )
    void givenValidMediaDataThatNotViolationConstraints_whenBuildCalled_thenMediaCreatedSuccessfully(){
        // Mocks Setting
        when( this.mediaIdConstraint.check( any( Media.class ))).thenReturn( Optional.empty() );
        when( this.mediaOwnerConstraint.check( any( Media.class ))).thenReturn( Optional.empty() );

        // Given valid Media data
        final UserId owner = UserId.random();
        final MediaContentType contentType = MediaContentType.IMAGE_JPEG;
        final MediaContentSize contentSize = new MediaContentSize( 100L );
        final String namespace = "test";

        // When building the Media
        final Media media = Media.create( owner, contentType, contentSize ).namespace( namespace )
                .build( this.mediaIdConstraint,
                        this.mediaOwnerConstraint );

        // Then check that constraints were checked
        verify( this.mediaIdConstraint ).check( any( Media.class ));
        verify( this.mediaOwnerConstraint ).check( any( Media.class ));

        // Then assert Media was created successfully with correct data
        assertNotNull( media.getId() );
        assertEquals( owner, media.getOwner() );
        assertEquals( contentType, media.getContentType() );
        assertEquals( contentSize, media.getContentSize() );
        assertEquals( namespace, media.getNamespace().orElse( null ));
        assertNotNull( media.getCreatedAt() );
        assertTrue( media.getUpdatedAt().isEmpty() );
    }

//--------------------------------------------------------------------------------------------------------------------\\

    /**
     * Test que comprueba que la clase {@link MediaFactory} lanza la excepción {@link ConstraintException} cuando se
     * intenta construir un {@link Media} con datos válidos que violan las restricciones requeridas por la factoría para
     * construir el {@link Media}.
     */
    @Test
    @DisplayName( "Given media data that violates constraints, When build is called, Then a ConstraintException is thrown" )
    void givenValidMediaDataThatViolationConstraints_whenBuildCalled_thenThrowConstraintException(){
        // Mocks Setting
        final ConstraintViolation<?> violation = mock( ConstraintViolation.class );
        when( this.mediaIdConstraint.check( any( Media.class ))).thenReturn( Optional.of( violation ));
        when( this.mediaOwnerConstraint.check( any( Media.class ))).thenReturn( Optional.of( violation ));

        // Given valid Media data
        final UserId owner = UserId.random();
        final MediaContentType contentType = MediaContentType.IMAGE_JPEG;
        final MediaContentSize contentSize = new MediaContentSize( 100L );
        final String namespace = "test";

        // When building the Media
        final MediaFactory factory = Media.create( owner, contentType, contentSize ).namespace( namespace );
        final ConstraintException exception = assertThrows( ConstraintException.class,
                () -> factory.build( this.mediaIdConstraint,
                                     this.mediaOwnerConstraint ));

        // Then check that constraints were checked
        verify( this.mediaIdConstraint ).check( any( Media.class ));
        verify( this.mediaOwnerConstraint ).check( any( Media.class ));

        // Then assert thrown exception
        assertEquals( CoreMessageKey.ERROR_CONSTRAINT_GENERIC.getMessage(), exception.getMessage() );
        final List<ConstraintViolation<?>> violations = exception.getViolations();
        assertEquals( 2, violations.size() );
    }

//--------------------------------------------------------------------------------------------------------------------\\

}
