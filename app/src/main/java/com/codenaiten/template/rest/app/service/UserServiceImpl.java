package com.codenaiten.template.rest.app.service;

import com.codenaiten.template.rest.app.api.UserService;
import com.codenaiten.template.rest.app.authentication.AuthenticationProvider;
import com.codenaiten.template.rest.app.dto.command.PageCommand;
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
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    /** Properties con información relacionada con los detalles de la aplicación */
    private final AppProperties appProperties;

    /** Provider relacionado con la autenticación de un usuario */
    private final AuthenticationProvider authenticationProvider;

    /** Manager relacionado con las operaciones relacionadas con los ficheros de las imágenes */
    private final ImageFileManager imageFileManager;

    /** Repository relacionado con la entidad {@link Image} */
    private final ImageRepository imageRepository;

    /** Repository relacionado con la entidad {@link User} */
    private final UserRepository userRepository;

    /** Mapper relacionado con la entidad {@link Image} */
    private final ImageMapper imageMapper;

    /** Mapper relacionado con la entidad {@link User} */
    private final UserMapper userMapper;

// ------------------------------------------------------------------------------------------------------------------ \\

    /** Editor para la actualización de la entidad {@link User} */
    private UserEditor userEditor;

    /** Policy relacionado con las políticas de acceso a la entidad {@link Image} */
    private ImageAccessPolicy imageAccessPolicy;

    /** Policy relacionado con las políticas de acceso a la entidad {@link User} */
    private UserAccessPolicy userAccessPolicy;

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| INITIALIZER |------------------------------------------------------------------------------------------------ \\
// ------------------------------------------------------------------------------------------------------------------ \\

    /**
     * Inicializa las propiedades necesarias que no son beans de Spring.
     */
    @PostConstruct
    public void init(){
        // UserEditor
        var userUsernameUniquenessPolicy = new UserUsernameUniquenessPolicy( this.userRepository );
        var userMinimumAgePolicy = new UserMinimumAgePolicy( this.appProperties );
        this.userEditor = new UserEditor( userUsernameUniquenessPolicy, userMinimumAgePolicy );

        // Policies
        this.imageAccessPolicy = new ImageAccessPolicy();
        this.userAccessPolicy = new UserAccessPolicy();
    }

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| IMPLEMENTED METHODS |---------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    @Override
    public UserInfoResult get( final UserId id ) {
        // Step 01: Find User by ID
        final User user = this.userRepository.findById( id.value() )
                .orElseThrow( () -> new UserNotFoundByIdException( id ));

        // Step 02: Convert User & Return Result
        return this.userMapper.toInfoResult( user );
    }

// ------------------------------------------------------------------------------------------------------------------ \\

    @Override
    public UserInfoResult me() {
        // Step 01: Get Authenticated User
        final User requester = this.authenticationProvider.getAuthenticatedAccount()
                .map( Account::getOwner )
                .orElseThrow( AuthNotFoundException::new );

        // Step 02: Convert User & Return Result
        return this.userMapper.toInfoResult( requester );
    }

// ------------------------------------------------------------------------------------------------------------------ \\

    @Override
    public PageResult<UserInfoResult> search( final String search, final PageCommand pageableCommand ){
        // Step 01: Create Pageable
        final Pageable pageable = PageRequest.of( pageableCommand.page(), pageableCommand.size() );

        // Step 02: Search Users
        final Page<User> page;
        if( Objects.nonNull( search )) page = this.userRepository.search( search, pageable );
        else page = this.userRepository.findAll( pageable );

        // Step 03: Convert User List to Result List
        final List<UserInfoResult> content = page.getContent().stream().map( this.userMapper::toInfoResult ).toList();

        // Step 04: Create Page Result & Return Result
        return PageResult.of( page.getTotalElements(), page.getNumber(), page.getSize(), content );
    }

// ------------------------------------------------------------------------------------------------------------------ \\

    @Override
    public PageResult<UserInfoResult> search( final FilterUserCommand filter, final PageCommand pageableCommand ){
        // Step 01: Initialize Probe and Matcher
        final User probe = new User();
        final ExampleMatcher matcher = ExampleMatcher.matchingAll().withIgnoreNullValues().withIgnoreCase();

        // Step 02: Build Probes from Filter if exists
        if( Objects.nonNull( filter )){ // If Filter exists
            // Set Matchers
            matcher.withMatcher("username", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase() );
            matcher.withMatcher("name", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase() );
            matcher.withMatcher("surname", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase() );

            // Set Probe from Filter if exists data
            Optional.ofNullable( filter.username() ).ifPresent( probe::setUsername );
            Optional.ofNullable( filter.name() ).ifPresent( probe::setName );
            Optional.ofNullable( filter.surname() ).ifPresent( probe::setSurname );
        }

        // Step 03: Create Example from Probe & Matcher
        final Example<User> example = Example.of( probe, matcher );

        // Step 04: Create Pageable
        final Pageable pageable = PageRequest.of( pageableCommand.page(), pageableCommand.size() );

        // Step 05: Search Users
        final Page<User> data = this.userRepository.findAll( example, pageable );

        // Step 06: Convert User List to Result List
        final List<UserInfoResult> content = data.getContent().stream().map( this.userMapper::toInfoResult ).toList();

        // Step 07: Create Page Result & Return Result
        return PageResult.of( data.getTotalElements(), data.getNumber(), data.getSize(), content );
    }

// ------------------------------------------------------------------------------------------------------------------ \\

    @Override
    @Transactional
    public UserInfoResult update( final UserId id, final UpdateUserCommand command ) {
        // Step 01: Get Authenticated Account
        final Account requester = this.authenticationProvider.getAuthenticatedAccount()
                .orElseThrow( AuthNotFoundException::new );

        // Step 02: Get Authenticated User
        final User user = requester.getOwner();

        // Step 03: Check if Authenticated Account has Write access
        this.userAccessPolicy.checkWrite( requester, user );

        // Step 04: Get Provided data
        final ImageId imageId = command.image();
        final UserUsername username = command.username();
        final UserName name = command.name();
        final UserSurname surname = command.surname();
        final LocalDate birthdate = command.birthdate();

        // Step 05: Check if Image ID is provided, if provided check if exists Image
        if( Objects.nonNull( imageId )){ // If provided
            // Get Image by ID
            final Image image = this.imageRepository.findById( imageId.value() )
                    .orElseThrow( () -> new ImageNotFoundByIdException( imageId ));

            // Check if Authenticated Account has Read access
            this.imageAccessPolicy.checkRead( requester, image );
        }

        // Step 06: Update User
        final UserEditor.Editor editor = this.userEditor.update( user );
        editor.image( imageId ).username( username ).name( name ).surname( surname ).birthdate( birthdate );

        // Step 07: Check if User Editor has changes
        if( editor.hasChanges() ){ // If User Editor has changes
            // Apply changes & Save User
            editor.apply();
            this.userRepository.save( user );
        }

        // Step 08: Convert User & Return Result
        return this.userMapper.toInfoResult( user );
    }

// ------------------------------------------------------------------------------------------------------------------ \\

    @Override
    @Transactional
    public UserInfoResult update( final UpdateUserCommand command ){
        // Step 01: Get Authenticated Account
        final Account requester = this.authenticationProvider.getAuthenticatedAccount()
                .orElseThrow( AuthNotFoundException::new );

        // Step 02: Get Authenticated User
        final User user = requester.getOwner();

        // Step 03: Get Provided data
        final ImageId imageId = command.image();
        final UserUsername username = command.username();
        final UserName name = command.name();
        final UserSurname surname = command.surname();
        final LocalDate birthdate = command.birthdate();

        // Step 04: Check if Image ID is provided, if provided check if exists Image
        if( Objects.nonNull( imageId )){ // If provided
            // Get Image by ID
            final Image image = this.imageRepository.findById( imageId.value() )
                    .orElseThrow( () -> new ImageNotFoundByIdException( imageId ));

            // Check if Authenticated Account has Read access
            this.imageAccessPolicy.checkRead( requester, image );
        }

        // Step 05: Update User
        final UserEditor.Editor editor = this.userEditor.update( user );
        editor.image( imageId ).username( username ).name( name ).surname( surname ).birthdate( birthdate );

        // Step 06: Check if User Editor has changes
        if( editor.hasChanges() ){ // If User Editor has changes
            // Apply changes & Save User
            editor.apply();
            this.userRepository.save( user );
        }

        // Step 05: Convert User & Return Result
        return this.userMapper.toInfoResult( user );
    }

// ------------------------------------------------------------------------------------------------------------------ \\

    @Override
    @SneakyThrows( IOException.class )
    public ImageContentResult getImageProfile(final UserId id ){
        // Step 01: Get Authenticated Account
        final Account requester = this.authenticationProvider.getAuthenticatedAccount()
                .orElseThrow( AuthNotFoundException::new );

        // Step 02: Find User by ID
        final User user = this.userRepository.findById( id.value() )
                .orElseThrow( () -> new UserNotFoundByIdException( id ));

        // Step 03: Check if Authenticated User has Image Profile
        final Optional<UUID> userImageProfile = user.getImage();
        if( userImageProfile.isEmpty() )
            throw new UserImageProfileNotExistsException( new UserId( requester.getId() ));

        // Step 04: Get Image Profile
        final ImageId imageId = new ImageId( userImageProfile.get() );
        final Image image = this.imageRepository.findById( imageId.value() )
                .orElseThrow( () -> new ImageNotFoundByIdException( imageId ));

        // Step 05: Create Image Info Result from Image
        final ImageInfoResult info = this.imageMapper.toInfoResult( image );

        // Step 06: Get Image content from Image File
        final File file = this.imageFileManager.get( imageId );
        final byte[] content = this.imageFileManager.read( file );

        // Step 07: Create Image Content Result from Image Info & Content
        return new ImageContentResult( info, content );
    }

// ------------------------------------------------------------------------------------------------------------------ \\

    @Override
    @SneakyThrows( IOException.class )
    public ImageContentResult getImageProfile(){
        // Step 01: Get Authenticated User
        final User requester = this.authenticationProvider.getAuthenticatedAccount()
                .map( Account::getOwner )
                .orElseThrow( AuthNotFoundException::new );

        // Step 02: Check if Authenticated User has Image Profile
        final Optional<UUID> userImageProfile = requester.getImage();
        if( userImageProfile.isEmpty() )
            throw new UserImageProfileNotExistsException( new UserId( requester.getId() ));

        // Step 03: Get Image Profile
        final ImageId imageId = new ImageId( userImageProfile.get() );
        final Image image = this.imageRepository.findById( imageId.value() )
                .orElseThrow( () -> new ImageNotFoundByIdException( imageId ));

        // Step 04: Create Image Info Result from Image
        final ImageInfoResult info = this.imageMapper.toInfoResult( image );

        // Step 05: Get Image content from Image File
        final File file = this.imageFileManager.get( imageId );
        final byte[] content = this.imageFileManager.read( file );

        // Step 06: Create Image Content Result from Image Info & Content
        return new ImageContentResult( info, content );
    }

// ------------------------------------------------------------------------------------------------------------------ \\

}
