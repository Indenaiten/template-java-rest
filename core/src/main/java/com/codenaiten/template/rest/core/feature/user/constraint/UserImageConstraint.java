package com.codenaiten.template.rest.core.feature.user.constraint;

import com.codenaiten.template.rest.core.feature.media.Media;
import com.codenaiten.template.rest.core.feature.media.spi.MediaRepository;
import com.codenaiten.template.rest.core.feature.media.vo.MediaId;
import com.codenaiten.template.rest.core.feature.user.User;
import com.codenaiten.template.rest.core.feature.user.constraint.violation.InvalidUserImageConstraintViolation;
import com.codenaiten.template.rest.core.feature.user.constraint.violation.NotFoundUserImageConstraintViolation;
import com.codenaiten.template.rest.core.shared.constraint.Constraint;
import com.codenaiten.template.rest.core.shared.constraint.ConstraintViolation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

@Slf4j
@Component
public class UserImageConstraint implements Constraint<User> {

    private final MediaRepository mediaRepository;

//--------------------------------------------------------------------------------------------------------------------\\
//---| CONSTRUCTOR |--------------------------------------------------------------------------------------------------\\
//--------------------------------------------------------------------------------------------------------------------\\

    public UserImageConstraint( final MediaRepository mediaRepository ){
        log.info( "UserImageConstraint initialized" );

        if( Objects.isNull( mediaRepository ))
            throw new IllegalArgumentException( "MediaRepository is required by UserImageConstraint" );

        this.mediaRepository = mediaRepository;
    }

//--------------------------------------------------------------------------------------------------------------------\\
//---| IMPLEMENTED METHODS |------------------------------------------------------------------------------------------\\
//--------------------------------------------------------------------------------------------------------------------\\

    @Override
    public Optional<ConstraintViolation<?>> check( final User candidate ){
        if( Objects.isNull( candidate ))
            throw new IllegalArgumentException( "User candidate to check UserImageConstraint is required" );

        final MediaId id = candidate.getImage().orElse( null );
        if( Objects.nonNull( id )){
            final Optional<Media> media = this.mediaRepository.find( id );
            if( media.isEmpty() ) return Optional.of( new NotFoundUserImageConstraintViolation( candidate ));

            final Media image = media.get();
            if( !image.isImageType() ) return Optional.of( new InvalidUserImageConstraintViolation( candidate, image ));
        }

        return Optional.empty();
    }

//--------------------------------------------------------------------------------------------------------------------\\

}
