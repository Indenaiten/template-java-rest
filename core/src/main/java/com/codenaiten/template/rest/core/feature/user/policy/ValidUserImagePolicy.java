package com.codenaiten.template.rest.core.feature.user.policy;

import com.codenaiten.template.rest.core.feature.media.Media;
import com.codenaiten.template.rest.core.feature.media.spec.IsImageMediaSpec;
import com.codenaiten.template.rest.core.feature.media.spi.MediaRepository;
import com.codenaiten.template.rest.core.feature.media.vo.MediaId;
import com.codenaiten.template.rest.core.feature.user.User;
import com.codenaiten.template.rest.core.shared.exception.AppException;
import com.codenaiten.template.rest.core.shared.policy.Policy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Slf4j
@Component
@RequiredArgsConstructor
public class ValidUserImagePolicy implements Policy<User> {

    private final MediaRepository mediaRepository;
    private final IsImageMediaSpec isImageMediaSpec;

//--------------------------------------------------------------------------------------------------------------------\\
//---| IMPLEMENTED METHODS |------------------------------------------------------------------------------------------\\
//--------------------------------------------------------------------------------------------------------------------\\

    @Override
    public void check( final User candidate ){
        if( Objects.isNull( candidate )) throw new IllegalArgumentException( "User to check valid image is required" );
        final MediaId id = candidate.getImage().orElseThrow( AppException::new );
        if( Objects.nonNull( id )){
            final Media image = this.mediaRepository.find( id ).orElseThrow( AppException::new );
            if( this.isImageMediaSpec.not().test( image )) throw new AppException();
        }
    }

//--------------------------------------------------------------------------------------------------------------------\\

}
