package com.codenaiten.template.rest.core.feature.media.service;

import com.codenaiten.template.rest.core.feature.media.Media;
import com.codenaiten.template.rest.core.feature.media.MediaEditor;
import com.codenaiten.template.rest.core.feature.media.dto.input.UpdateMediaInput;
import com.codenaiten.template.rest.core.feature.media.exception.MediaNotFoundException;
import com.codenaiten.template.rest.core.feature.media.port.MediaFileManager;
import com.codenaiten.template.rest.core.feature.media.port.MediaRepository;
import com.codenaiten.template.rest.core.feature.media.vo.MediaId;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class UpdateMediaService {

    // Ports
    private final MediaRepository mediaRepository;
    private final MediaFileManager mediaFileManager;

//--------------------------------------------------------------------------------------------------------------------\\
//---| METHODS |------------------------------------------------------------------------------------------------------\\
//--------------------------------------------------------------------------------------------------------------------\\

    public Media update( final MediaId id, final UpdateMediaInput input ){
        // Step 01: Get Media to Update
        final Media media = this.mediaRepository.find( id ).orElseThrow( () -> new MediaNotFoundException( id ));

        // Step 02: Update Media with Editor
        final MediaEditor editor = media.update();
        editor.namespace( input.getNamespace().orElse( null ));
        input.getContentType().ifPresent( editor::contentType );
        input.getContentSize().ifPresent( editor::contentSize );

        // Step 03: Check if has changes
        if( editor.hasChanges() ){ // If has changes, apply changes
            editor.apply();

            // Save changes
            this.mediaRepository.save( media );
        }

        // Step 04: Check if content file has been updated
        if( input.getContent().isPresent() ){ // If has content, write content to file
            final byte[] content = input.getContent().get();
            this.mediaFileManager.write( media, content );
        }

        // Step 05: Return updated Media
        return media;
    }

//--------------------------------------------------------------------------------------------------------------------\\

}
