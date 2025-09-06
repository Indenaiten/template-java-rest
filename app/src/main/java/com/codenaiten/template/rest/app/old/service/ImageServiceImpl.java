package com.codenaiten.template.rest.app.old.service;

import com.codenaiten.template.rest.app.old.api.ImageService;
import com.codenaiten.template.rest.app.old.authentication.AuthenticationProvider;
import com.codenaiten.template.rest.app.old.dto.command.account.CreateImageCommand;
import com.codenaiten.template.rest.app.old.dto.result.ImageContentResult;
import com.codenaiten.template.rest.app.old.dto.result.ImageInfoResult;
import com.codenaiten.template.rest.app.old.entity.Account;
import com.codenaiten.template.rest.app.old.entity.Image;
import com.codenaiten.template.rest.app.old.entity.User;
import com.codenaiten.template.rest.app.old.exception.data.found.ImageNotFoundByIdException;
import com.codenaiten.template.rest.app.old.exception.security.AuthNotFoundException;
import com.codenaiten.template.rest.app.old.factory.ImageFactory;
import com.codenaiten.template.rest.app.old.file.ImageFileManager;
import com.codenaiten.template.rest.app.old.mapper.ImageMapper;
import com.codenaiten.template.rest.app.old.policy.ImageAccessPolicy;
import com.codenaiten.template.rest.app.old.repository.ImageRepository;
import com.codenaiten.template.rest.app.old.vo.image.ImageContentType;
import com.codenaiten.template.rest.app.old.vo.image.ImageId;
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

    /** Manager relacionado con las operaciones relacionadas con los ficheros de las imágenes */
    private final ImageFileManager imageFileManager;

    /** Repository relacionado con la entidad {@link Image} */
    private final ImageRepository imageRepository;

    /** Mapper relacionado con la entidad {@link Image} */
    private final ImageMapper imageMapper;

// ------------------------------------------------------------------------------------------------------------------ \\

    /** Factory para la creación de la entidad {@link Image} */
    private ImageFactory imageFactory;

    /** Policy relacionado con las políticas de acceso a la entidad {@link Image} */
    private ImageAccessPolicy imageAccessPolicy;

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| INITIALIZER |------------------------------------------------------------------------------------------------ \\
// ------------------------------------------------------------------------------------------------------------------ \\

    /**
     * Inicializa las propiedades necesarias que no son beans de Spring.
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
        // Step 01: Get Authenticated Account
        final Account requester = this.authenticationProvider.getAuthenticatedAccount()
                .orElseThrow( AuthNotFoundException::new );

        // Step 02: Get Image by ID
        final Image image = this.imageRepository.findById( id.value() )
                .orElseThrow( () -> new ImageNotFoundByIdException( id ));

        // Step 03: // Check if Authenticated Account has Read access
        this.imageAccessPolicy.checkRead( requester, image );

        // Step 04: Create Image Info Result from Image
        final ImageInfoResult info = this.imageMapper.toInfoResult( image );

        // Step 05: Get Image content from Image File
        final File file = this.imageFileManager.get( id );
        final byte[] content = this.imageFileManager.read( file );

        // Step 06: Create Image Content Result from Image Info & Content
        return new ImageContentResult( info, content );
    }

// ------------------------------------------------------------------------------------------------------------------ \\

    @Override
    @SneakyThrows( IOException.class )
    @Transactional( rollbackOn = IOException.class )
    public ImageInfoResult create( final CreateImageCommand command ){
        // Step 01: Get Authenticated Account
        final Account requester = this.authenticationProvider.getAuthenticatedAccount()
                .orElseThrow( AuthNotFoundException::new );

        // Step 02: Get Authenticated User
        final User user = requester.getOwner();

        // Step 03: Get provided data
        final byte[] content = command.image();
        final ImageContentType contentType = command.contentType();
        final Long contentSize = command.contentSize();

        // Step 04: Create Image
        final Image image = this.imageFactory.create( user, contentType, contentSize ).build();

        // Step 05: Save Image
        this.imageRepository.save( image );

        // Step 06: Write File
        final ImageId id = new ImageId( image.getId() );
        this.imageFileManager.write( id, content );

        // Step 07: Convert to Result and return
        return this.imageMapper.toInfoResult( image );
    }

// ------------------------------------------------------------------------------------------------------------------ \\

}
