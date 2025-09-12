package com.codenaiten.template.rest.core.feature.media.constraint;

import com.codenaiten.template.rest.core.feature.media.Media;
import com.codenaiten.template.rest.core.feature.media.constraint.violation.AlreadyExistsMediaIdConstraintViolation;
import com.codenaiten.template.rest.core.feature.media.spi.MediaRepository;
import com.codenaiten.template.rest.core.feature.media.vo.MediaId;
import com.codenaiten.template.rest.core.shared.constraint.Constraint;
import com.codenaiten.template.rest.core.shared.constraint.ConstraintViolation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

@Slf4j
@Component
public class MediaIdConstraint implements Constraint<Media> {

    private final MediaRepository mediaRepository;

//--------------------------------------------------------------------------------------------------------------------\\
//---| CONSTRUCTOR |--------------------------------------------------------------------------------------------------\\
//--------------------------------------------------------------------------------------------------------------------\\

    public MediaIdConstraint( final MediaRepository mediaRepository ){
        log.info( "MediaIdConstraint initialized" );

        if( Objects.isNull( mediaRepository ))
            throw new IllegalArgumentException( "MediaRepository is required by MediaIdConstraint" );

        this.mediaRepository = mediaRepository;
    }

//--------------------------------------------------------------------------------------------------------------------\\
//---| IMPLEMENTED METHODS |------------------------------------------------------------------------------------------\\
//--------------------------------------------------------------------------------------------------------------------\\

    @Override
    public Optional<ConstraintViolation<?>> check( final Media candidate ){
        if( Objects.isNull( candidate ))
            throw new IllegalArgumentException( "Account candidate to check UserIdConstraint is required" );

        final MediaId id = candidate.getId();
        if( this.mediaRepository.exists( id )) return Optional.of( new AlreadyExistsMediaIdConstraintViolation( candidate ));

        return Optional.empty();
    }

//--------------------------------------------------------------------------------------------------------------------\\

}
