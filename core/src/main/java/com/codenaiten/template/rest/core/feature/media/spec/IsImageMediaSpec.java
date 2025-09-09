package com.codenaiten.template.rest.core.feature.media.spec;

import com.codenaiten.template.rest.core.feature.media.Media;
import com.codenaiten.template.rest.core.feature.media.vo.MediaContentType;
import com.codenaiten.template.rest.core.shared.spec.Specification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Slf4j
@Component
@RequiredArgsConstructor
public class IsImageMediaSpec implements Specification<Media>{

//--------------------------------------------------------------------------------------------------------------------\\
//---| IMPLEMENTED METHODS |------------------------------------------------------------------------------------------\\
//--------------------------------------------------------------------------------------------------------------------\\

    @Override
    public boolean test( final Media candidate ){
        if( Objects.isNull( candidate )) throw new IllegalArgumentException( "Media to check if is image is required" );
        final MediaContentType contentType = candidate.getContentType();
        return contentType.isImage();
    }

//--------------------------------------------------------------------------------------------------------------------\\

}
