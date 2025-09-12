package com.codenaiten.template.rest.core.feature.media;

import com.codenaiten.template.rest.core.CoreMessageKey;
import com.codenaiten.template.rest.core.feature.media.constraint.MediaIdConstraint;
import com.codenaiten.template.rest.core.feature.media.constraint.MediaOwnerConstraint;
import com.codenaiten.template.rest.core.feature.media.vo.MediaContentSize;
import com.codenaiten.template.rest.core.feature.media.vo.MediaContentType;
import com.codenaiten.template.rest.core.feature.user.vo.UserId;
import com.codenaiten.template.rest.core.shared.constraint.ConstraintViolation;
import com.codenaiten.template.rest.core.shared.exception.ConstraintException;
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
class MediaFactoryTest {

//--------------------------------------------------------------------------------------------------------------------\\

    @Test
    @DisplayName( "Media creation succeeds when no constraint violations exist" )
    void givenValidMediaDataAndNoConstraintViolations_whenBuildingMedia_thenMediaIsCreatedSuccessfully(){
        // Given mocked MediaIdConstraint that return no violations
        final MediaIdConstraint mediaIdConstraint = mock( MediaIdConstraint.class );
        when( mediaIdConstraint.check( any( Media.class ))).thenReturn( Optional.empty() );

        // Given mocked MediaOwnerConstraint that return no violations
        final MediaOwnerConstraint mediaOwnerConstraint = mock( MediaOwnerConstraint.class );
        when( mediaOwnerConstraint.check( any( Media.class ))).thenReturn( Optional.empty() );

        // Given valid Media data
        final UserId owner = UserId.random();
        final MediaContentType contentType = MediaContentType.IMAGE_JPEG;
        final MediaContentSize contentSize = new MediaContentSize( 100L );
        final String namespace = "test";

        // When building the Media
        final Media media = Media.create( owner, contentType, contentSize ).namespace( namespace )
                .build( mediaIdConstraint, mediaOwnerConstraint );

        // Then check that constraints were checked
        verify( mediaIdConstraint ).check( any( Media.class ));
        verify( mediaOwnerConstraint ).check( any( Media.class ));

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

    @Test
    @DisplayName( "Media creation fails with ConstraintException when constraint violations exist" )
    void givenValidMediaDataAndConstraintViolations_whenBuildingMedia_thenThrowConstraintException(){
        // Given mocked ConstraintViolation that constraints will be return
        final ConstraintViolation<?> violation = mock( ConstraintViolation.class );

        // Given mocked MediaIdConstraint that return violations
        final MediaIdConstraint mediaIdConstraint = mock( MediaIdConstraint.class );
        when( mediaIdConstraint.check( any( Media.class ))).thenReturn( Optional.of( violation ));

        // Given mocked MediaOwnerConstraint that return violations
        final MediaOwnerConstraint mediaOwnerConstraint = mock( MediaOwnerConstraint.class );
        when( mediaOwnerConstraint.check( any( Media.class ))).thenReturn( Optional.of( violation ));

        // Given valid Media data
        final UserId owner = UserId.random();
        final MediaContentType contentType = MediaContentType.IMAGE_JPEG;
        final MediaContentSize contentSize = new MediaContentSize( 100L );
        final String namespace = "test";

        // When building the Media
        final MediaFactory factory = Media.create( owner, contentType, contentSize ).namespace( namespace );
        final ConstraintException exception = assertThrows( ConstraintException.class, () ->
                factory.build( mediaIdConstraint, mediaOwnerConstraint ));

        // Then check that constraints were checked
        verify( mediaIdConstraint ).check( any( Media.class ));
        verify( mediaOwnerConstraint ).check( any( Media.class ));

        // Then assert thrown exception
        assertEquals( CoreMessageKey.ERROR_CONSTRAINT_GENERIC.getMessage(), exception.getMessage() );
        final List<ConstraintViolation<?>> violations = exception.getViolations();
        assertEquals( 2, violations.size() );
    }

//--------------------------------------------------------------------------------------------------------------------\\

}
