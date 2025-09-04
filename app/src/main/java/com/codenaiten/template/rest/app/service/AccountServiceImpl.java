package com.codenaiten.template.rest.app.service;

import com.codenaiten.template.rest.app.api.AccountService;
import com.codenaiten.template.rest.app.authentication.AuthenticationProvider;
import com.codenaiten.template.rest.app.authentication.PasswordEncoderManager;
import com.codenaiten.template.rest.app.dto.command.PageableCommand;
import com.codenaiten.template.rest.app.dto.command.account.CreateAccountCommand;
import com.codenaiten.template.rest.app.dto.command.account.FilterAccountCommand;
import com.codenaiten.template.rest.app.dto.command.account.UpdateAccountCommand;
import com.codenaiten.template.rest.app.dto.result.AccountInfoResult;
import com.codenaiten.template.rest.app.dto.result.PageResult;
import com.codenaiten.template.rest.app.editor.AccountEditor;
import com.codenaiten.template.rest.app.entity.Account;
import com.codenaiten.template.rest.app.entity.Image;
import com.codenaiten.template.rest.app.entity.User;
import com.codenaiten.template.rest.app.exception.data.found.AccountNotFoundByIdException;
import com.codenaiten.template.rest.app.exception.data.found.ImageNotFoundByIdException;
import com.codenaiten.template.rest.app.exception.security.AuthNotFoundException;
import com.codenaiten.template.rest.app.exception.security.IncorrectPasswordException;
import com.codenaiten.template.rest.app.factory.AccountFactory;
import com.codenaiten.template.rest.app.factory.ImageFactory;
import com.codenaiten.template.rest.app.factory.UserFactory;
import com.codenaiten.template.rest.app.file.ImageFileManager;
import com.codenaiten.template.rest.app.mapper.AccountMapper;
import com.codenaiten.template.rest.app.policy.*;
import com.codenaiten.template.rest.app.properties.AppProperties;
import com.codenaiten.template.rest.app.properties.LocaleProperties;
import com.codenaiten.template.rest.app.repository.AccountRepository;
import com.codenaiten.template.rest.app.repository.ImageRepository;
import com.codenaiten.template.rest.app.repository.UserRepository;
import com.codenaiten.template.rest.app.vo.Email;
import com.codenaiten.template.rest.app.vo.account.AccountId;
import com.codenaiten.template.rest.app.vo.account.AccountPassword;
import com.codenaiten.template.rest.app.vo.account.AccountRole;
import com.codenaiten.template.rest.app.vo.image.ImageId;
import com.codenaiten.template.rest.app.vo.user.UserName;
import com.codenaiten.template.rest.app.vo.user.UserSurname;
import com.codenaiten.template.rest.app.vo.user.UserUsername;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    /** Properties con información relacionada con la configuración de la aplicación */
    private final AppProperties appProperties;

    /** Properties con información relacionada con la configuración del lenguaje del sistema */
    private final LocaleProperties localeProperties;

    /** Provider relacionado con la autenticación de un usuario */
    private final AuthenticationProvider authenticationProvider;

    /** Manager relacionado con las operaciones relacionadas con el cifrado de contraseñas */
    private final PasswordEncoderManager passwordEncoderManager;

    /** Manager relacionado con las operaciones relacionadas con el manejo de archivos de imagenes */
    private final ImageFileManager imageFileManager;

    /** Repository relacionado con las entidades de tipo {@link Image} */
    private final ImageRepository imageRepository;

    /** Repository relacionado con las entidades de tipo {@link Account} */
    private final AccountRepository accountRepository;

    /** Repository relacionado con las entidades de tipo {@link User} */
    private final UserRepository userRepository;

    /** Mapper principal de objetos relacionados con las {@link Account} */
    private final AccountMapper accountMapper;

// ------------------------------------------------------------------------------------------------------------------ \\

    /** Factory para crear entidades de tipo {@link Image} */
    private ImageFactory imageFactory;

    /** Factory de entidades de tipo {@link Account} */
    private AccountEditor accountEditor;

    /** Factory para crear entidades de tipo {@link Account} */
    private AccountFactory accountFactory;

    /** Factory para crear entidades de tipo {@link User} */
    private UserFactory userFactory;

    /** Policy relacionado con las políticas de acceso de las {@link Image} */
    private ImageAccessPolicy imageAccessPolicy;

    /** Policy relacionado con las políticas de acceso de las {@link Account} */
    private AccountAccessPolicy accountAccessPolicy;

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

        // AccountFactory, AccountEditor
        var supportedLanguagePolicy = new LanguageSupportedPolicy( this.localeProperties );
        var accountEmailUniquenessPolicy = new AccountEmailUniquenessPolicy( this.accountRepository );
        var assignAccountRolePolicy = new AssignAccountRolePolicy( this.accountRepository );
        this.accountFactory = new AccountFactory( supportedLanguagePolicy, accountEmailUniquenessPolicy, assignAccountRolePolicy, this.passwordEncoderManager );
        this.accountEditor = new AccountEditor( supportedLanguagePolicy, accountEmailUniquenessPolicy, assignAccountRolePolicy, this.passwordEncoderManager );

        // UserFactory
        var userUsernameUniquenessPolicy = new UserUsernameUniquenessPolicy( this.userRepository );
        var userMinimumAgePolicy = new UserMinimumAgePolicy( this.appProperties );
        this.userFactory = new UserFactory( userUsernameUniquenessPolicy, userMinimumAgePolicy );

        // ImageAccessPolicy & AccountAccessPolicy
        this.imageAccessPolicy = new ImageAccessPolicy();
        this.accountAccessPolicy = new AccountAccessPolicy();
    }

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| IMPLEMENTED METHODS |---------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    @Override
    public AccountInfoResult me() {
        // Step 01: Get authenticated account
        final Account requester = this.authenticationProvider.getAuthenticatedAccount().orElseThrow( AuthNotFoundException::new );

        // Step 02: Convert to Result and return
        return this.accountMapper.toInfoResult( requester );
    }

// ------------------------------------------------------------------------------------------------------------------ \\

    @Override
    public AccountInfoResult get( final AccountId id ){
        // Step 01: Get authenticated account
        final Account requester = this.authenticationProvider.getAuthenticatedAccount().orElseThrow( AuthNotFoundException::new );

        // Step 02: Get account by ID
        final Account account = this.accountRepository.findById( id.value() ).orElseThrow( () -> new AccountNotFoundByIdException( id ));

        // Step 03: Check if current account has read access
        this.accountAccessPolicy.checkRead( requester, account );

        // Step 04: Convert to Result and return
        return this.accountMapper.toInfoResult( account );
    }

// ------------------------------------------------------------------------------------------------------------------ \\

    @Override
    public PageResult<AccountInfoResult> search( final String search, final PageableCommand pageableCommand){
        // Step 01: Get authenticated account
        final Account requester = this.authenticationProvider.getAuthenticatedAccount().orElseThrow( AuthNotFoundException::new );

        // Step 02: Check if current account has query access
        this.accountAccessPolicy.checkQuery( requester );

        // Step 03: Create pageable
        final Pageable pageable = PageRequest.of( pageableCommand.page(), pageableCommand.size() );

        // Step 04: Search accounts
        final Page<Account> page;
        if( Objects.nonNull( search )) page = this.accountRepository.search( search, pageable );
        else page = this.accountRepository.findAll( pageable );

        // Step 05: Convert to Result
        final List<AccountInfoResult> content = page.getContent().stream().map( this.accountMapper::toInfoResult ).toList();

        // Step 06: Create page info and return
        return new PageResult<>( page.getTotalElements(), page.getNumber(), page.getSize(), content );
    }

// ------------------------------------------------------------------------------------------------------------------ \\

    @Override
    public PageResult<AccountInfoResult> search( final FilterAccountCommand filterCommand, final PageableCommand pageableCommand ){
        // Step 01: Get authenticated account
        final Account requester = this.authenticationProvider.getAuthenticatedAccount().orElseThrow( AuthNotFoundException::new );

        // Step 02: Check if current account has query access
        this.accountAccessPolicy.checkQuery( requester );

        // Step 03: Initialize probe and matcher
        final Account probe = new Account();
        final User probeUser = new User();
        final ExampleMatcher matcher = ExampleMatcher.matchingAll().withIgnoreNullValues().withIgnoreCase();

        // Step 04: Check if filter exists
        if( Objects.nonNull( filterCommand )){ // If filter exists
            // Set Matchers
            matcher.withMatcher("lang", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase());
            matcher.withMatcher("role", ExampleMatcher.GenericPropertyMatchers.exact());
            matcher.withMatcher("email", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase());
            matcher.withMatcher("owner.username", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase());
            matcher.withMatcher("owner.name", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase());
            matcher.withMatcher("owner.surname", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase());

            // Set Probes from Filter
            Optional.ofNullable( filterCommand.lang() ).ifPresent( probe::setLang );
            Optional.ofNullable( filterCommand.role() ).ifPresent( probe::setRole );
            Optional.ofNullable( filterCommand.email() ).ifPresent( probe::setEmail );
            Optional.ofNullable( filterCommand.username() ).ifPresent( probeUser::setUsername );
            Optional.ofNullable( filterCommand.name() ).ifPresent( probeUser::setName );
            Optional.ofNullable( filterCommand.surname() ).ifPresent( probeUser::setSurname );

            // Set Owner from Filter if exists data
            if( Objects.nonNull( probeUser.getUsername() ) ||
                Objects.nonNull( probeUser.getName() ) ||
                Objects.nonNull( probeUser.getSurname() )) {
                probe.setOwner( probeUser );
            }
        }

        // Step 05: Create example from probe and matcher
        final Example<Account> example = Example.of( probe, matcher );

        // Step 06: Create pageable
        final Pageable pageable = PageRequest.of( pageableCommand.page(), pageableCommand.size() );

        // Step 07: Search accounts with example and pageable
        final Page<Account> page = this.accountRepository.findAll( example, pageable );

        // Step 08: Convert to Result
        final List<AccountInfoResult> content = page.getContent().stream().map( this.accountMapper::toInfoResult ).toList();

        // Step 09: Create page info and return
        return new PageResult<>( page.getTotalElements(), page.getNumber(), page.getSize(), content );
    }

// ------------------------------------------------------------------------------------------------------------------ \\

    @Override
    public List<AccountRole> getSupportedRoles() {
        // Step 01: Get supported roles list
        return AccountRole.getSupportedRoles();
    }

// ------------------------------------------------------------------------------------------------------------------ \\

    @Override
    @Transactional
    public AccountInfoResult create( final CreateAccountCommand command ){
        // Step 01: Get authenticated account
        final Account requester = this.authenticationProvider.getAuthenticatedAccount().orElseThrow( AuthNotFoundException::new );

        // Step 02: Check if current account has create access
        this.accountAccessPolicy.checkCreate( requester );

        // Step 03: Get provided data
        final Locale lang = command.lang();
        final ImageId imageId = command.image();
        final AccountRole role = command.role();
        final UserUsername username = command.username();
        final Email email = command.email();
        final AccountPassword password = command.password();
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

        // Step 04: Create user and account
        final User user = this.userFactory.create( username, name, birthdate ).surname( surname ).image( imageId ).build();
        final Account account = this.accountFactory.create( user, email, password ).role( role ).lang( lang ).build();

        // Step 05: Save user and account
        this.userRepository.save( user );
        this.accountRepository.save( account );

        // Step 06: Convert to Result and return
        return this.accountMapper.toInfoResult( account );
    }

// ------------------------------------------------------------------------------------------------------------------ \\

    @Override
    @Transactional
    public AccountInfoResult update( final AccountId id, final UpdateAccountCommand command ){
        // Step 01: Get authenticated account
        final Account requester = this.authenticationProvider.getAuthenticatedAccount().orElseThrow( AuthNotFoundException::new );

        // Step 02: Get account by ID
        final Account account = this.accountRepository.findById( id.value() ).orElseThrow( () -> new AccountNotFoundByIdException( id ));

        // Step 03: Check if current account has write access
        this.accountAccessPolicy.checkWrite( requester, account );

        // Step 04: Update account
        final AccountEditor.Editor editor = this.accountEditor.update( account );
        editor.lang( command.lang() ).role( command.role() ).email( command.email() ).password( command.password() );

        // Step 05: Check if editor has changes
        if( editor.hasChanges() ){ // If editor has changes
            // Apply changes and save new data
            editor.apply();
            this.accountRepository.save( account );
        }

        // Step 06: Convert to Result and return
        return this.accountMapper.toInfoResult( account );
    }

// ------------------------------------------------------------------------------------------------------------------ \\

    @Override
    @Transactional
    public AccountInfoResult updateLang( final Locale lang ){
        // Step 01: Get authenticated account
        final Account requester = this.authenticationProvider.getAuthenticatedAccount().orElseThrow( AuthNotFoundException::new );

        // Step 02: Update lang account
        final AccountEditor.Editor editor = this.accountEditor.update( requester );
        editor.lang( lang );

        // Step 03: Check if editor has changes
        if( editor.hasChanges() ){ // If editor has changes
            // Apply changes and save new data
            editor.apply();
            this.accountRepository.save( requester );

            // Set lang in LocaleContextHolder
            requester.getLang().map( Locale::forLanguageTag ).ifPresent( LocaleContextHolder::setLocale );
        }

        // Step 04: Convert to Result and return
        return this.accountMapper.toInfoResult( requester );
    }

// ------------------------------------------------------------------------------------------------------------------ \\

    @Override
    @Transactional
    public AccountInfoResult updateEmail( final AccountPassword password, final Email newEmail ){
        // Step 01: Get authenticated account
        final Account requester = this.authenticationProvider.getAuthenticatedAccount().orElseThrow( AuthNotFoundException::new );

        // Step 02: Check if account password is correct
        if( !this.passwordEncoderManager.check( password.value(), requester.getPassword() )) throw new IncorrectPasswordException();

        // Step 03: Update email account
        final AccountEditor.Editor editor = this.accountEditor.update( requester );
        editor.email( newEmail );

        // Step 04: Check if editor has changes
        if( editor.hasChanges() ){ // If editor has changes
            // Apply changes and save new data
            editor.apply();
            this.accountRepository.save( requester );
        }

        // Step 05: Convert to Result and return
        return this.accountMapper.toInfoResult( requester );
    }

// ------------------------------------------------------------------------------------------------------------------ \\

    @Override
    @Transactional
    public AccountInfoResult updatePassword( final AccountPassword password, final AccountPassword newPassword ){
        // Step 01: Get authenticated account
        final Account requester = this.authenticationProvider.getAuthenticatedAccount().orElseThrow( AuthNotFoundException::new );

        // Step 02: Check if account password is correct
        if( !this.passwordEncoderManager.check( password.value(), requester.getPassword() )) throw new IncorrectPasswordException();

        // Step 03: Update password account
        final AccountEditor.Editor editor = this.accountEditor.update( requester );
        editor.password( newPassword );

        // Step 04: Check if editor has changes
        if( editor.hasChanges() ){ // If editor has changes
            // Apply changes and save new data
            editor.apply();
            this.accountRepository.save( requester );
        }

        // Step 05: Convert to Result and return
        return this.accountMapper.toInfoResult( requester );
    }

// ------------------------------------------------------------------------------------------------------------------ \\

    @Override
    @SneakyThrows( IOException.class )
    @Transactional( rollbackFor = IOException.class )
    public AccountInfoResult delete( final AccountId id ){
        // Step 01: Get authenticated account
        final Account requester = this.authenticationProvider.getAuthenticatedAccount().orElseThrow( AuthNotFoundException::new );

        // Step 02: Get account by ID
        final Account account = this.accountRepository.findById( id.value() ).orElseThrow( () -> new AccountNotFoundByIdException( id ));

        // Step 03: Check if current account has delete access
        this.accountAccessPolicy.checkDelete( requester, account );

        // Step 04: Get Data
        final User user = account.getOwner();
        final ImageId imageId = new ImageId( user.getId() );

        // Step 05: Delete account
        this.userRepository.delete( user );

        // Step 06: Delete Image profile
        this.imageRepository.deleteById( imageId.value() );
        final File file = this.imageFileManager.get( imageId );
        this.imageFileManager.delete( file );

        // Step 07: Convert to Result and return
        return this.accountMapper.toInfoResult( account );
    }

// ------------------------------------------------------------------------------------------------------------------ \\

    @Override
    @SneakyThrows( IOException.class )
    @Transactional( rollbackFor = IOException.class )
    public AccountInfoResult delete( final AccountPassword password ){
        // Step 01: Get authenticated account
        final Account requester = this.authenticationProvider.getAuthenticatedAccount().orElseThrow( AuthNotFoundException::new );

        // Step 02: Check if account password is correct
        if( !this.passwordEncoderManager.check( password.value(), requester.getPassword() )) throw new IncorrectPasswordException();

        // Step 03: Get Data
        final User user = requester.getOwner();
        final ImageId imageId = new ImageId( user.getId() );

        // Step 04: Delete account
        this.userRepository.delete( user );

        // Step 05: Delete Image profile
        this.imageRepository.deleteById( imageId.value() );
        final File file = this.imageFileManager.get( imageId );
        this.imageFileManager.delete( file );

        // Step 06: Convert to Result and return
        return this.accountMapper.toInfoResult( requester );
    }

// ------------------------------------------------------------------------------------------------------------------ \\

}
