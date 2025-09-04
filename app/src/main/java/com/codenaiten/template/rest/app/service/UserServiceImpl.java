package com.codenaiten.template.rest.app.service;

import com.codenaiten.template.rest.app.api.UserService;
import com.codenaiten.template.rest.app.authentication.AuthenticationProvider;
import com.codenaiten.template.rest.app.dto.command.PageableCommand;
import com.codenaiten.template.rest.app.dto.command.user.FilterUserCommand;
import com.codenaiten.template.rest.app.dto.command.user.UpdateUserCommand;
import com.codenaiten.template.rest.app.dto.result.PageResult;
import com.codenaiten.template.rest.app.dto.result.UserInfoResult;
import com.codenaiten.template.rest.app.editor.ImageEditor;
import com.codenaiten.template.rest.app.editor.UserEditor;
import com.codenaiten.template.rest.app.entity.Account;
import com.codenaiten.template.rest.app.entity.Image;
import com.codenaiten.template.rest.app.entity.User;
import com.codenaiten.template.rest.app.exception.data.found.UserNotFoundByIdException;
import com.codenaiten.template.rest.app.exception.security.AuthNotFoundException;
import com.codenaiten.template.rest.app.factory.ImageFactory;
import com.codenaiten.template.rest.app.file.ImageFileManager;
import com.codenaiten.template.rest.app.mapper.UserMapper;
import com.codenaiten.template.rest.app.policy.UserAccessPolicy;
import com.codenaiten.template.rest.app.policy.UserMinimumAgePolicy;
import com.codenaiten.template.rest.app.policy.UserUsernameUniquenessPolicy;
import com.codenaiten.template.rest.app.properties.AppProperties;
import com.codenaiten.template.rest.app.repository.ImageRepository;
import com.codenaiten.template.rest.app.repository.UserRepository;
import com.codenaiten.template.rest.app.vo.image.ImageContentType;
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

    /** Mapper principal de objetos relacionados con los {@link User} */
    private final UserMapper userMapper;

// ------------------------------------------------------------------------------------------------------------------ \\

    /** Factory para crear entidades de tipo {@link Image} */
    private ImageFactory imageFactory;

    /** Editor para actualizar entidades de tipo {@link Image} */
    private ImageEditor imageEditor;

    /** Editor para actualizar entidades de tipo {@link User} */
    private UserEditor userEditor;

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
        // ImageFactory & ImageEditor
        this.imageFactory = new ImageFactory();
        this.imageEditor = new ImageEditor();

        // UserEditor
        var userUsernameUniquenessPolicy = new UserUsernameUniquenessPolicy( this.userRepository );
        var userMinimumAgePolicy = new UserMinimumAgePolicy( this.appProperties );
        this.userEditor = new UserEditor( userUsernameUniquenessPolicy, userMinimumAgePolicy );

        // UserAccessPolicy
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
    @SneakyThrows( IOException.class )
    @Transactional( rollbackOn = Exception.class )
    public UserInfoResult update( final UserId id, final UpdateUserCommand command ) {
        // Step 01: Get authenticated user
        final Account requester = this.authenticationProvider.getAuthenticatedAccount().orElseThrow( AuthNotFoundException::new );
        final User user = requester.getOwner();

        // Step 02: Check if current user has write access
        this.userAccessPolicy.checkWrite( requester, user );

        // Step 03: Get provided data
        final byte[] bytes = command.image();
        final ImageContentType contentType = command.imageContentType();
        final UserUsername username = command.username();
        final UserName name = command.name();
        final UserSurname surname = command.surname();
        final LocalDate birthdate = command.birthdate();

        // Step 04: Update image if exists
        Image image = user.getImage().orElse( null );
        if( Objects.nonNull( bytes ) && bytes.length > 0 ){
            image = Optional.ofNullable( image ).orElse( this.imageFactory.create( contentType ).build() );
            this.imageEditor.update( image ).contentType( contentType ).apply();
            this.imageRepository.save( image );

            // Update image file
            this.imageFileManager.write( new ImageId( image.getId() ), bytes );
        }

        // Step 05: Update user
        final UserEditor.Editor userEditor = this.userEditor.update( user );
        userEditor.image( image ).username( username ).name( name ).surname( surname ).birthdate( birthdate );

        // Step 06: Check if user editor has changes
        if( userEditor.hasChanges() ){ // If editor has changes
            // Apply changes and save new data
            userEditor.apply();
            this.userRepository.save( user );
        }

        // Step 07: Check if image must be deleted
        if(( Objects.isNull( bytes ) || bytes.length == 0 ) && Objects.nonNull( image )){ // If image must be deleted
            // Delete image
            this.imageRepository.delete( image );
            final ImageId imageId = new ImageId( image.getId() );
            final File file = this.imageFileManager.get( imageId );
            this.imageFileManager.delete( file );
        }

        // Step 08: Convert to Result and return
        return this.userMapper.toInfoResult( user );
    }

// ------------------------------------------------------------------------------------------------------------------ \\

    @Override
    @SneakyThrows( IOException.class )
    @Transactional( rollbackOn = Exception.class )
    public UserInfoResult update( final UpdateUserCommand command ){
        // Step 01: Get authenticated user
        final Account requester = this.authenticationProvider.getAuthenticatedAccount().orElseThrow( AuthNotFoundException::new );
        final User user = requester.getOwner();

        // Step 02: Get provided data
        final byte[] bytes = command.image();
        final ImageContentType contentType = command.imageContentType();
        final UserUsername username = command.username();
        final UserName name = command.name();
        final UserSurname surname = command.surname();
        final LocalDate birthdate = command.birthdate();

        // Step 03: Update image if exists
        Image image = user.getImage().orElse( null );
        if( Objects.nonNull( bytes ) && bytes.length > 0 ){
            image = Optional.ofNullable( image ).orElse( this.imageFactory.create( contentType ).build() );
            this.imageEditor.update( image ).contentType( contentType ).apply();
            this.imageRepository.save( image );

            // Update image file
            this.imageFileManager.write( new ImageId( image.getId() ), bytes );
        }

        // Step 04: Update user
        final UserEditor.Editor userEditor = this.userEditor.update( user );
        userEditor.image( image ).username( username ).name( name ).surname( surname ).birthdate( birthdate );

        // Step 05: Check if user editor has changes
        if( userEditor.hasChanges() ){ // If editor has changes
            // Apply changes and save new data
            userEditor.apply();
            this.userRepository.save( user );
        }

        // Step 06: Check if image must be deleted
        if(( Objects.isNull( bytes ) || bytes.length == 0 ) && Objects.nonNull( image )){ // If image must be deleted
            // Delete image
            this.imageRepository.delete( image );
            final ImageId imageId = new ImageId( image.getId() );
            final File file = this.imageFileManager.get( imageId );
            this.imageFileManager.delete( file );
        }

        // Step 07: Convert to Result and return
        return this.userMapper.toInfoResult( user );
    }

// ------------------------------------------------------------------------------------------------------------------ \\

}
