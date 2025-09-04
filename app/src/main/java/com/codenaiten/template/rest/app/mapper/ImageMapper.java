package com.codenaiten.template.rest.app.mapper;

import com.codenaiten.template.rest.app.dto.result.ImageInfoResult;
import com.codenaiten.template.rest.app.entity.Image;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper( componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
         uses = { OptionalMapper.class, TemporalMapper.class })
public interface ImageMapper {

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| ENTITY ---> RESULT |----------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    ImageInfoResult toInfoResult( Image image );

// ------------------------------------------------------------------------------------------------------------------ \\

}
