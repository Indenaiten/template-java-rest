package com.codenaiten.template.rest.core.feature.media.dto.input;

import com.codenaiten.template.rest.core.feature.media.vo.MediaContentSize;
import com.codenaiten.template.rest.core.feature.media.vo.MediaContentType;
import lombok.Builder;
import lombok.Setter;

import java.util.Optional;

@Setter
@Builder
public class UpdateMediaInput {

    private MediaContentType contentType;
    private MediaContentSize contentSize;
    private String namespace;
    private byte[] content;

//--------------------------------------------------------------------------------------------------------------------\\
//---| GETTERS |------------------------------------------------------------------------------------------------------\\
//--------------------------------------------------------------------------------------------------------------------\\

    public Optional<MediaContentType> getContentType(){
        return Optional.ofNullable( this.contentType );
    }

    public Optional<MediaContentSize> getContentSize(){
        return Optional.ofNullable( this.contentSize );
    }

    public Optional<String> getNamespace(){
        return Optional.ofNullable( this.namespace );
    }

    public Optional<byte[]> getContent(){
        return Optional.ofNullable( this.content );
    }

//--------------------------------------------------------------------------------------------------------------------\\

}