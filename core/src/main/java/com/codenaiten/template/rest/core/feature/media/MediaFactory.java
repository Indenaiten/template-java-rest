package com.codenaiten.template.rest.core.feature.media;

import com.codenaiten.template.rest.core.feature.media.constraint.MediaIdConstraint;
import com.codenaiten.template.rest.core.feature.media.constraint.MediaOwnerConstraint;
import com.codenaiten.template.rest.core.feature.media.vo.MediaContentSize;
import com.codenaiten.template.rest.core.feature.media.vo.MediaContentType;
import com.codenaiten.template.rest.core.feature.media.vo.MediaId;
import com.codenaiten.template.rest.core.feature.user.vo.UserId;
import com.codenaiten.template.rest.core.shared.constraint.ConstraintViolation;
import com.codenaiten.template.rest.core.shared.exception.ConstraintException;
import com.codenaiten.template.rest.core.shared.vo.Timestamp;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class MediaFactory {

    private final UserId owner;
    private final MediaContentType contentType;
    private final MediaContentSize contentSize;

// -------------------------------------------------------------------------------------------------------------- \\

    private String namespace;

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| SETTERS |---------------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    public MediaFactory namespace( final String namespace ){
        this.namespace = namespace;
        return this;
    }

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| BUILDER METHOD |--------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    public Media build( final MediaIdConstraint mediaIdConstraint,
                       final MediaOwnerConstraint mediaOwnerConstraint
    ){
        // Step 01: Create Media
        final MediaId id = MediaId.random();
        final Timestamp now = Timestamp.now();
        final Media media = Media.builder().id( id ).owner( this.owner ).contentType( this.contentType )
                .contentSize( this.contentSize ).namespace( this.namespace ).createdAt( now ).build();

        // Step 02: Validate Media
        final List<ConstraintViolation<?>> violations = new ArrayList<>();
        mediaIdConstraint.check( media ).ifPresent( violations::add );
        mediaOwnerConstraint.check( media ).ifPresent( violations::add );
        if( !violations.isEmpty() ) throw new ConstraintException( violations );

        // Step 03: Return Media
        return media;
    }

// ------------------------------------------------------------------------------------------------------------------ \\

}
