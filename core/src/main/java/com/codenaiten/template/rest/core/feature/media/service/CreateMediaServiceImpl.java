package com.codenaiten.template.rest.core.feature.media.service;

import com.codenaiten.template.rest.core.feature.media.Media;
import com.codenaiten.template.rest.core.feature.media.policy.ValidMediaOwnerSpec;
import com.codenaiten.template.rest.core.feature.media.vo.MediaContentSize;
import com.codenaiten.template.rest.core.feature.media.vo.MediaContentType;
import com.codenaiten.template.rest.core.feature.media.vo.MediaId;
import com.codenaiten.template.rest.core.feature.user.vo.UserId;
import com.codenaiten.template.rest.core.shared.vo.Timestamp;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class CreateMediaServiceImpl {

    private final ValidMediaOwnerSpec validMediaOwnerSpec;

//--------------------------------------------------------------------------------------------------------------------\\
//---| IMPLEMENTED METHODS |------------------------------------------------------------------------------------------\\
//--------------------------------------------------------------------------------------------------------------------\\

    public Media create( final Input input ){
        log.info( "Creating media: {}", input );

        // Get Data
        final MediaId id = MediaId.random();
        final UserId owner = input.getOwner();
        final String namespace = input.getNamespace().orElse( null );
        final MediaContentType contentType = input.getContentType();
        final MediaContentSize contentSize = input.getContentSize();
        final Timestamp createdAt = Timestamp.now();
        final Timestamp updatedAt = null;

        // Create Media
        final Media media = new Media( id, owner, namespace, contentType, contentSize, createdAt, updatedAt );

        // Check Restrictions
        this.validMediaOwnerSpec.check( media );

        // Return Media
        return media;
    }

//--------------------------------------------------------------------------------------------------------------------\\
//---| INPUT |--------------------------------------------------------------------------------------------------------\\
//--------------------------------------------------------------------------------------------------------------------\\

    @Getter
    @Setter
    @RequiredArgsConstructor
    class Input {

        private final UserId owner;
        private final MediaContentType contentType;
        private final MediaContentSize contentSize;
        private String namespace;

    //----------------------------------------------------------------------------------------------------------------\\
    //---| GETTERS |--------------------------------------------------------------------------------------------------\\
    //----------------------------------------------------------------------------------------------------------------\\

        public Optional<String> getNamespace(){
            return Optional.ofNullable( this.namespace );
        }

    //----------------------------------------------------------------------------------------------------------------\\

    }

//--------------------------------------------------------------------------------------------------------------------\\

}
