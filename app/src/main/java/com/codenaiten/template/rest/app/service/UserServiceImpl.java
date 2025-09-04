package com.codenaiten.template.rest.app.service;

import com.codenaiten.template.rest.app.api.UserService;
import com.codenaiten.template.rest.app.authentication.AuthenticationProvider;
import com.codenaiten.template.rest.app.dto.command.PageableCommand;
import com.codenaiten.template.rest.app.dto.command.user.FilterUserCommand;
import com.codenaiten.template.rest.app.dto.command.user.UpdateUserCommand;
import com.codenaiten.template.rest.app.dto.result.ImageContentResult;
import com.codenaiten.template.rest.app.dto.result.ImageInfoResult;
import com.codenaiten.template.rest.app.dto.result.PageResult;
import com.codenaiten.template.rest.app.dto.result.UserInfoResult;
import com.codenaiten.template.rest.app.editor.UserEditor;
import com.codenaiten.template.rest.app.entity.Account;
import com.codenaiten.template.rest.app.entity.Image;
import com.codenaiten.template.rest.app.entity.User;
import com.codenaiten.template.rest.app.exception.data.found.ImageNotFoundByIdException;
import com.codenaiten.template.rest.app.exception.data.found.UserImageProfileNotExistsException;
import com.codenaiten.template.rest.app.exception.data.found.UserNotFoundByIdException;
import com.codenaiten.template.rest.app.exception.security.AuthNotFoundException;
import com.codenaiten.template.rest.app.file.ImageFileManager;
import com.codenaiten.template.rest.app.mapper.ImageMapper;
import com.codenaiten.template.rest.app.mapper.UserMapper;
import com.codenaiten.template.rest.app.policy.ImageAccessPolicy;
import com.codenaiten.template.rest.app.policy.UserAccessPolicy;
import com.codenaiten.template.rest.app.policy.UserMinimumAgePolicy;
import com.codenaiten.template.rest.app.policy.UserUsernameUniquenessPolicy;
import com.codenaiten.template.rest.app.properties.AppProperties;
import com.codenaiten.template.rest.app.repository.ImageRepository;
import com.codenaiten.template.rest.app.repository.UserRepository;
import com.codenaiten.template.rest.app.vo.image.ImageId;
import com.codenaiten.template.rest.app.vo.user.UserId;
import com.codenaiten.template.rest.app.vo.user.UserName;
import com.codenaiten.template.rest.app.vo.user.UserSurname;
import com.codenaiten.template.rest.app.vo.user.UserUsername;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    /** Properties con información relacionada con la configuración de la aplicación */
    private final AppProperties appProperties;

    /** Provider relacionado con la autenticación de un usuario */
    private final AuthenticationProvider authenticationProvider;

    /** Manager relacionado con las operaciones relacionadas con el manejo de archivos de imagenes */
    private final ImageFileManager imageFileManager;

    /** Repository relacionado con las entidades de tipo {@link Image} */
    private final ImageRepository imageRepository;

    /** Repository relacionado con las entidades de tipo {@link User} */
    private final UserRepository userRepository;

    /** Mapper principal de objetos relacionados con las {@link Image} */
    private final ImageMapper imageMapper;

    /** Mapper principal de objetos relacionados con los {@link User} */
    private final UserMapper userMapper;

// ------------------------------------------------------------------------------------------------------------------ \\

    /** Editor para actualizar entidades de tipo {@link User} */
    private UserEditor userEditor;

    /** Policy relacionado con las políticas de acceso de las {@link Image} */
    private ImageAccessPolicy imageAccessPolicy;

    /** Policy relacionado con las políticas de acceso de los {@link User} */
    private UserAccessPolicy userAccessPolicy;

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| INITIALIZER |------------------------------------------------------------------------------------------------ \\
// ------------------------------------------------------------------------------------------------------------------ \\

    /**
     * Inicializa las propiedades necesarias que no son beans de Spring y que pueden requerir dependencias que si son
     * beans de Spring, después de construir la clase.
     */
    @PostConstruct
    public void init(){
        // UserEditor
        var userUsernameUniquenessPolicy = new UserUsernameUniquenessPolicy( this.userRepository );
        var userMinimumAgePolicy = new UserMinimumAgePolicy( this.appProperties );
        this.userEditor = new UserEditor( userUsernameUniquenessPolicy, userMinimumAgePolicy );

        // ImageAccessPolicy & UserAccessPolicy
        this.imageAccessPolicy = new ImageAccessPolicy();
        this.userAccessPolicy = new UserAccessPolicy();
    }

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| IMPLEMENTED METHODS |---------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    @Override
    public UserInfoResult me() {
        // Step 01: Get authenticated user
        final Account requester = this.authenticationProvider.getAuthenticatedAccount().orElseThrow( AuthNotFoundException::new );
        final User user = requester.getOwner();

        // Step 02: Convert to Result and return
        return this.userMapper.toInfoResult( user );
    }

// ------------------------------------------------------------------------------------------------------------------ \\

    @Override
    public UserInfoResult get( final UserId id ) {
        // Step 01: Find User by ID
        final User user = this.userRepository.findById( id.value() ).orElseThrow( () -> new UserNotFoundByIdException( id ));

        // Step 02: Convert to Result and return
        return this.userMapper.toInfoResult( user );
    }

// ------------------------------------------------------------------------------------------------------------------ \\

    @Override
    public PageResult<UserInfoResult> search( final String search, final PageableCommand pageableCommand ){
        // Step 01: Create pageable
        final Pageable pageable = PageRequest.of( pageableCommand.page(), pageableCommand.size() );

        // Step 02: Search users
        final Page<User> page;
        if( Objects.nonNull( search )) page = this.userRepository.search( search, pageable );
        else page = this.userRepository.findAll( pageable );

        // Step 03: Convert to Result
        final List<UserInfoResult> content = page.getContent().stream().map( this.userMapper::toInfoResult ).toList();

        // Step 04: Create page info and return
        return new PageResult<>( page.getTotalElements(), page.getNumber(), page.getSize(), content );
    }

// ------------------------------------------------------------------------------------------------------------------ \\

    @Override
    public PageResult<UserInfoResult> search( final FilterUserCommand filterCommand, final PageableCommand pageableCommand ){
        // Step 01: Create pageable
        final Pageable pageable = PageRequest.of( pageableCommand.page(), pageableCommand.size() );

        // Step 02: Initialize probe and matcher
        final User probe = new User();
        final ExampleMatcher matcher = ExampleMatcher.matchingAll().withIgnoreNullValues().withIgnoreCase();

        // Step 03: Check if filter exists
        if( Objects.nonNull( filterCommand )){ // If filter exists
            // Set Matchers
            matcher.withMatcher("username", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase() );
            matcher.withMatcher("name", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase() );
            matcher.withMatcher("surname", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase() );

            // Set Probes from Filter
            Optional.ofNullable( filterCommand.username() ).ifPresent( probe::setUsername );
            Optional.ofNullable( filterCommand.name() ).ifPresent( probe::setName );
            Optional.ofNullable( filterCommand.surname() ).ifPresent( probe::setSurname );
        }

        // Step 04: Create example from probe and matcher
        final Example<User> example = Example.of( probe, matcher );

        // Step 05: Search users with example and pageable
        final Page<User> page = this.userRepository.findAll( example, pageable );

        // Step 06: Convert to Result
        final List<UserInfoResult> content = page.getContent().stream().map( this.userMapper::toInfoResult ).toList();

        // Step 07: Create page info and return
        return new PageResult<>( page.getTotalElements(), page.getNumber(), page.getSize(), content );
    }

// ------------------------------------------------------------------------------------------------------------------ \\

    @Override
    @Transactional
    public UserInfoResult update( final UserId id, final UpdateUserCommand command ) {
        // Step 01: Get authenticated user
        final Account requester = this.authenticationProvider.getAuthenticatedAccount().orElseThrow( AuthNotFoundException::new );
        final User user = requester.getOwner();

        // Step 02: Check if current user has write access
        this.userAccessPolicy.checkWrite( requester, user );

        // Step 03: Get provided data
        final ImageId imageId = command.image();
        final UserUsername username = command.username();
        final UserName name = command.name();
        final UserSurname surname = command.surname();
        final LocalDate birthdate = command.birthdate();

        // Step 04: Check if exists image if image ID is provided
        if( Objects.nonNull( imageId )){
            final Image image = this.imageRepository.findById( imageId.value() )
                    .orElseThrow( () -> new ImageNotFoundByIdException( imageId ));

            // Check if current user has read access to image
            this.imageAccessPolicy.checkRead( requester, image );
        }

        // Step 05: Update user
        final UserEditor.Editor editor = this.userEditor.update( user );
        editor.image( imageId ).username( username ).name( name ).surname( surname ).birthdate( birthdate );

        // Step 06: Save user changes
        if( editor.hasChanges() ){ // If editor has changes
            // Apply changes and save new data
            editor.apply();
            this.userRepository.save( user );
        }

        // Step 07: Convert to Result and return
        return this.userMapper.toInfoResult( user );
    }

// ------------------------------------------------------------------------------------------------------------------ \\

    @Override
    @Transactional
    public UserInfoResult update( final UpdateUserCommand command ){
        // Step 01: Get authenticated user
        final Account requester = this.authenticationProvider.getAuthenticatedAccount().orElseThrow( AuthNotFoundException::new );
        final User user = requester.getOwner();

        // Step 02: Get provided data
        final ImageId imageId = command.image();
        final UserUsername username = command.username();
        final UserName name = command.name();
        final UserSurname surname = command.surname();
        final LocalDate birthdate = command.birthdate();

        // Step 03: Check if exists image if image ID is provided
        if( Objects.nonNull( imageId )){
            final Image image = this.imageRepository.findById( imageId.value() )
                    .orElseThrow( () -> new ImageNotFoundByIdException( imageId ));

            // Check if current user has read access to image
            this.imageAccessPolicy.checkRead( requester, image );
        }

        // Step 04: Update user
        final UserEditor.Editor editor = this.userEditor.update( user );
        editor.image( imageId ).username( username ).name( name ).surname( surname ).birthdate( birthdate );

        // Step 04: Save user changes
        if( editor.hasChanges() ){ // If editor has changes
            // Apply changes and save new data
            editor.apply();
            this.userRepository.save( user );
        }

        // Step 05: Convert to Result and return
        return this.userMapper.toInfoResult( user );
    }

// ------------------------------------------------------------------------------------------------------------------ \\

    @Override
    @SneakyThrows( IOException.class )
    public ImageContentResult image( final UserId id ){
        // Step 01: Get authenticated user
        final Account requester = this.authenticationProvider.getAuthenticatedAccount().orElseThrow( AuthNotFoundException::new );

        // Step 02: Find User by ID
        final User user = this.userRepository.findById( id.value() ).orElseThrow( () -> new UserNotFoundByIdException( id ));

        // Step 03: Check if current user has image profile
        if( user.getImage().isEmpty() ) throw new UserImageProfileNotExistsException( new UserId( requester.getId() ));

        // Step 04: Get Image Profile
        final ImageId imageId = new ImageId( user.getId() );
        final Image image = this.imageRepository.findById( imageId.value() )
                .orElseThrow( () -> new ImageNotFoundByIdException( imageId ));

        // Step 05: Convert to Result and return
        final ImageInfoResult info = this.imageMapper.toInfoResult( image );

        // Step 06: Get Image content
        final File file = this.imageFileManager.get( imageId );
        final byte[] content = this.imageFileManager.read( file );

        // Step 07: Create result and return
        return new ImageContentResult( info, content );
    }

// ------------------------------------------------------------------------------------------------------------------ \\

    @Override
    @SneakyThrows( IOException.class )
    public ImageContentResult image(){
        // Step 01: Get authenticated user
        final Account requesterAccount = this.authenticationProvider.getAuthenticatedAccount().orElseThrow( AuthNotFoundException::new );
        final User requester = requesterAccount.getOwner();

        // Step 02: Check if current user has image profile
        if( requester.getImage().isEmpty() ) throw new UserImageProfileNotExistsException( new UserId( requester.getId() ));

        // Step 03: Get Image Profile
        final ImageId imageId = new ImageId( requester.getId() );
        final Image image = this.imageRepository.findById( imageId.value() )
                .orElseThrow( () -> new ImageNotFoundByIdException( imageId ));

        // Step 04: Convert to Result and return
        final ImageInfoResult info = this.imageMapper.toInfoResult( image );

        // Step 05: Get Image content
        final File file = this.imageFileManager.get( imageId );
        final byte[] content = this.imageFileManager.read( file );

        // Step 06: Create result and return
        return new ImageContentResult( info, content );
    }

// ------------------------------------------------------------------------------------------------------------------ \\

}
