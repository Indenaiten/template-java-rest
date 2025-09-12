package com.codenaiten.template.rest.core.feature.media.dto.input;

import com.codenaiten.template.rest.core.feature.media.vo.MediaContentSize;
import com.codenaiten.template.rest.core.feature.media.vo.MediaContentType;
import com.codenaiten.template.rest.core.feature.user.vo.UserId;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Optional;

@Getter
@Setter
@RequiredArgsConstructor
public class CreateMediaInput {

    private final UserId owner;
    private final MediaContentType contentType;
    private final MediaContentSize contentSize;
    private final byte[] content;

//--------------------------------------------------------------------------------------------------------------------\\

    private String namespace;

//--------------------------------------------------------------------------------------------------------------------\\
//---| GETTERS |------------------------------------------------------------------------------------------------------\\
//--------------------------------------------------------------------------------------------------------------------\\

    public Optional<String> getNamespace(){
        return Optional.ofNullable( this.namespace );
    }

//--------------------------------------------------------------------------------------------------------------------\\

}