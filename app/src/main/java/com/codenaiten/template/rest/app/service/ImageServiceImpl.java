package com.codenaiten.template.rest.app.service;

import com.codenaiten.template.rest.app.api.ImageService;
import com.codenaiten.template.rest.app.authentication.AuthenticationProvider;
import com.codenaiten.template.rest.app.dto.command.account.CreateImageCommand;
import com.codenaiten.template.rest.app.dto.result.ImageContentResult;
import com.codenaiten.template.rest.app.dto.result.ImageInfoResult;
import com.codenaiten.template.rest.app.entity.Account;
import com.codenaiten.template.rest.app.entity.Image;
import com.codenaiten.template.rest.app.entity.User;
import com.codenaiten.template.rest.app.exception.data.found.ImageNotFoundByIdException;
import com.codenaiten.template.rest.app.exception.security.AuthNotFoundException;
import com.codenaiten.template.rest.app.factory.ImageFactory;
import com.codenaiten.template.rest.app.file.ImageFileManager;
import com.codenaiten.template.rest.app.mapper.ImageMapper;
import com.codenaiten.template.rest.app.policy.ImageAccessPolicy;
import com.codenaiten.template.rest.app.repository.ImageRepository;
import com.codenaiten.template.rest.app.vo.image.ImageContentType;
import com.codenaiten.template.rest.app.vo.image.ImageId;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Slf4j
@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

    /** Provider relacionado con la autenticación de un usuario */
    private final AuthenticationProvider authenticationProvider;

    /** Manager relacionado con las operaciones relacionadas con el manejo de archivos de imagenes */
    private final ImageFileManager imageFileManager;

    /** Repository relacionado con las entidades de tipo {@link Image} */
    private final ImageRepository imageRepository;

    /** Mapper principal de objetos relacionados con las {@link Image} */
    private final ImageMapper imageMapper;

// ------------------------------------------------------------------------------------------------------------------ \\

    /** Factory para crear entidades de tipo {@link Image} */
    private ImageFactory imageFactory;

    /** Policy relacionado con las políticas de acceso de las {@link Image} */
    private ImageAccessPolicy imageAccessPolicy;

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| INITIALIZER |------------------------------------------------------------------------------------------------ \\
// ------------------------------------------------------------------------------------------------------------------ \\

    /**
     * Inicializa las propiedades necesarias que no son beans de Spring y que pueden requerir dependencias que si son
     * beans de Spring, después de construir la clase.
     */
    @PostConstruct
    public void init(){
        // ImageFactory
        this.imageFactory = new ImageFactory();

        // ImageAccessPolicy
        this.imageAccessPolicy = new ImageAccessPolicy();
    }

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| IMPLEMENTED METHODS |---------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    @Override
    @SneakyThrows( IOException.class )
    public ImageContentResult getContent( final ImageId id ){
        // Step 01: Get authenticated user
        final Account requester = this.authenticationProvider.getAuthenticatedAccount().orElseThrow( AuthNotFoundException::new );
        final User user = requester.getOwner();

        // Step 02: Get image by ID
        final Image image = this.imageRepository.findById( id.value() ).orElseThrow( () -> new ImageNotFoundByIdException( id ));

        // Step 03: Check if current user has read access to image
        this.imageAccessPolicy.checkRead( requester, image );

        // Step 04: Get image content
        final File file = this.imageFileManager.get( id );
        final byte[] content = this.imageFileManager.read( file );

        // Step 05: Create result and return
        final ImageInfoResult info = this.imageMapper.toInfoResult( image );
        return new ImageContentResult( info, content );
    }

// ------------------------------------------------------------------------------------------------------------------ \\

    @Override
    @SneakyThrows( IOException.class )
    @Transactional( rollbackOn = IOException.class )
    public ImageInfoResult create( final CreateImageCommand command ){
        // Step 01: Get authenticated user
        final Account requester = this.authenticationProvider.getAuthenticatedAccount().orElseThrow( AuthNotFoundException::new );
        final User user = requester.getOwner();

        // Step 02: Get provided data
        final byte[] content = command.image();
        final ImageContentType contentType = command.contentType();
        final Long contentSize = command.contentSize();

        // Step 03: Create image
        final ImageId id = new ImageId( user.getId() );
        final Image image = this.imageFactory.create( user, contentType, contentSize ).build( id );

        // Step 04: Save image
        this.imageRepository.save( image );
        this.imageFileManager.write( id, content );

        // Step 05: Convert to Result and return
        return this.imageMapper.toInfoResult( image );
    }

// ------------------------------------------------------------------------------------------------------------------ \\

}
