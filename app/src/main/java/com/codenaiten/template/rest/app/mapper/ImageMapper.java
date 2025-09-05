package com.codenaiten.template.rest.app.mapper;

import com.codenaiten.template.rest.app.dto.result.ImageInfoResult;
import com.codenaiten.template.rest.app.entity.Image;
import com.codenaiten.template.rest.app.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.Optional;
import java.util.UUID;

@Mapper( componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
         uses = { OptionalMapper.class, TemporalMapper.class })
public interface ImageMapper {

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| ENTITY ---> RESULT |----------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    ImageInfoResult toInfoResult( Image image );

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| DEFAULT |---------------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    default UUID toUserId( final User user ){
        return Optional.ofNullable( user ).map( User::getId ).orElse( null );
    }

// ------------------------------------------------------------------------------------------------------------------ \\

}
