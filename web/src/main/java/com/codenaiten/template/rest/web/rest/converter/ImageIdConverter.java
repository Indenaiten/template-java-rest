package com.codenaiten.template.rest.web.rest.converter;

import com.codenaiten.template.rest.app.vo.image.ImageId;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

/**
 * Convierte un {@link UUID} a un objeto de tipo {@link ImageId}.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class ImageIdConverter implements Converter<UUID, ImageId>{

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| IMPLEMENTED METHODS |---------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    @Override
    public ImageId convert( final UUID source ){
        return Optional.ofNullable( source ).map( ImageId::new ).orElse( null );
    }

// ------------------------------------------------------------------------------------------------------------------ \\

}
