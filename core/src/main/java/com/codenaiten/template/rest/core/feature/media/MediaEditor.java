package com.codenaiten.template.rest.core.feature.media;

import com.codenaiten.template.rest.core.feature.media.vo.MediaContentSize;
import com.codenaiten.template.rest.core.feature.media.vo.MediaContentType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
public class MediaEditor {

    private final Media update;
    private final Media media;

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| CONSTRUCTOR |------------------------------------------------------------------------------------------------ \\
// ------------------------------------------------------------------------------------------------------------------ \\

    public MediaEditor( final Media media ){
        Objects.requireNonNull( media, "Media to update in Editor is required" );
        this.update = media.copy();
        this.media = media;
    }

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| SETTERS |---------------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    public MediaEditor namespace( final String namespace ){
        this.update.setNamespace( namespace );
        return this;
    }

    public MediaEditor contentType( final MediaContentType contentType ){
        Optional.ofNullable( contentType ).ifPresent( this.update::setContentType );
        return this;
    }

    public MediaEditor contentSize( final MediaContentSize contentSize ){
        Optional.ofNullable( contentSize ).ifPresent( this.update::setContentSize );
        return this;
    }

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| CHECKERS |--------------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    public boolean hasChanges(){
        return !Objects.equals( this.media.getNamespace(), this.update.getNamespace() ) ||
               !Objects.equals( this.media.getContentType(), this.update.getContentType() ) ||
               !Objects.equals( this.media.getContentSize(), this.update.getContentSize() );
    }

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| APPLY METHOD |----------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    public boolean apply(){
        // Step 01: Check changes
        final boolean hasChanges = this.hasChanges();
        if( hasChanges ){ // If has changes, apply them
            this.media.setNamespace( this.update.getNamespace().orElse( null ));
            this.media.setContentType( this.update.getContentType() );
            this.media.setContentSize( this.update.getContentSize() );

            // Update UpdatedAt Timestamp
            this.media.setUpdatedAt();
        }

        // Step 04: Return true if has changes, false otherwise
        return hasChanges;
    }

// ------------------------------------------------------------------------------------------------------------------ \\

}
