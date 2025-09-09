package com.codenaiten.template.rest.core.feature.media.service;

import com.codenaiten.template.rest.core.feature.media.Media;
import com.codenaiten.template.rest.core.feature.media.vo.MediaContentSize;
import com.codenaiten.template.rest.core.feature.media.vo.MediaContentType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Slf4j
@Component
@RequiredArgsConstructor
public class UpdateMediaServiceImpl {

//--------------------------------------------------------------------------------------------------------------------\\
//---| IMPLEMENTED METHODS |------------------------------------------------------------------------------------------\\
//--------------------------------------------------------------------------------------------------------------------\\

    public void updateNamespace( final Media media, final String namespace ){
        if( Objects.isNull( media )) throw new IllegalArgumentException( "Media to update namespace is required" );
        if( !Objects.equals( media.getNamespace().orElse( null ), namespace )) media.setNamespace( namespace );
    }

    public void updateContentType( final Media media, final MediaContentType contentType ){
        if( Objects.isNull( media )) throw new IllegalArgumentException( "Media to update Content Type is required" );
        if( !Objects.equals( media.getContentType(), contentType )) media.setContentType( contentType );
    }

    public void updateContentSize( final Media media, final MediaContentSize contentSize ){
        if( Objects.isNull( media )) throw new IllegalArgumentException( "Media to update Content Size is required" );
        if( !Objects.equals( media.getContentSize(), contentSize )) media.setContentSize( contentSize );
    }

//--------------------------------------------------------------------------------------------------------------------\\

}
