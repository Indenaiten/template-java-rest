package com.codenaiten.template.rest.core.feature.media.service;

import com.codenaiten.template.rest.core.feature.media.Media;
import com.codenaiten.template.rest.core.feature.media.constraint.MediaIdConstraint;
import com.codenaiten.template.rest.core.feature.media.constraint.MediaOwnerConstraint;
import com.codenaiten.template.rest.core.feature.media.dto.input.CreateMediaInput;
import com.codenaiten.template.rest.core.feature.media.port.MediaFileManager;
import com.codenaiten.template.rest.core.feature.media.port.MediaRepository;
import com.codenaiten.template.rest.core.feature.media.vo.MediaContentSize;
import com.codenaiten.template.rest.core.feature.media.vo.MediaContentType;
import com.codenaiten.template.rest.core.feature.user.vo.UserId;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.File;

@Slf4j
@Component
@RequiredArgsConstructor
public class CreateMediaService {

    // Ports
    private final MediaRepository mediaRepository;
    private final MediaFileManager mediaFileManager;

    // User Constraints
    private final MediaIdConstraint mediaIdConstraint;
    private final MediaOwnerConstraint mediaOwnerConstraint;

//--------------------------------------------------------------------------------------------------------------------\\
//---| RESULT |-------------------------------------------------------------------------------------------------------\\
//--------------------------------------------------------------------------------------------------------------------\\

    public record Result ( Media media, File file ){}

//--------------------------------------------------------------------------------------------------------------------\\
//---| METHODS |------------------------------------------------------------------------------------------------------\\
//--------------------------------------------------------------------------------------------------------------------\\

    public Result create( final CreateMediaInput input ){
        // Step 01: Get Media Data
        final UserId owner = input.getOwner();
        final MediaContentType contentType = input.getContentType();
        final MediaContentSize contentSize = input.getContentSize();
        final byte[] content = input.getContent();
        final String namespace = input.getNamespace().orElse( null );

        // Step 01: Create Media with Factory
        final Media media = Media.create( owner, contentType, contentSize ).namespace( namespace )
                .build( this.mediaIdConstraint,
                        this.mediaOwnerConstraint );

        // Step 07: Save Media
        this.mediaRepository.save( media );
        final File file = this.mediaFileManager.write( media, content );

        // Step 08: Return Result
        return new Result( media, file );
    }

//--------------------------------------------------------------------------------------------------------------------\\

}
