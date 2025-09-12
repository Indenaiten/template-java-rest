package com.codenaiten.template.rest.core.feature.user.constraint;

import com.codenaiten.template.rest.core.feature.media.Media;
import com.codenaiten.template.rest.core.feature.media.port.MediaRepository;
import com.codenaiten.template.rest.core.feature.media.vo.MediaId;
import com.codenaiten.template.rest.core.feature.user.User;
import com.codenaiten.template.rest.core.feature.user.constraint.violation.InvalidContentTypeUserImageConstraintViolation;
import com.codenaiten.template.rest.core.feature.user.constraint.violation.InvalidOwnerUserImageConstraintViolation;
import com.codenaiten.template.rest.core.feature.user.constraint.violation.NotFoundUserImageConstraintViolation;
import com.codenaiten.template.rest.core.shared.constraint.Constraint;
import com.codenaiten.template.rest.core.shared.constraint.ConstraintViolation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserImageConstraint implements Constraint<User> {

    private final MediaRepository mediaRepository;

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
            if( !image.isOwner( candidate )) return Optional.of( new InvalidOwnerUserImageConstraintViolation( candidate, image ));
            if( !image.isImageType() ) return Optional.of( new InvalidContentTypeUserImageConstraintViolation( candidate, image ));
        }

        return Optional.empty();
    }

//--------------------------------------------------------------------------------------------------------------------\\

}
