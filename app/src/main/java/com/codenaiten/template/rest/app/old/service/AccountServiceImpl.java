package com.codenaiten.template.rest.app.old.service;

import com.codenaiten.template.rest.app.old.api.AccountService;
import com.codenaiten.template.rest.app.old.authentication.AuthenticationProvider;
import com.codenaiten.template.rest.app.old.authentication.PasswordEncoderManager;
import com.codenaiten.template.rest.app.old.dto.command.PageCommand;
import com.codenaiten.template.rest.app.old.dto.command.account.CreateAccountCommand;
import com.codenaiten.template.rest.app.old.dto.command.account.FilterAccountCommand;
import com.codenaiten.template.rest.app.old.dto.command.account.UpdateAccountCommand;
import com.codenaiten.template.rest.app.old.dto.result.AccountInfoResult;
import com.codenaiten.template.rest.app.old.dto.result.PageResult;
import com.codenaiten.template.rest.app.old.editor.AccountEditor;
import com.codenaiten.template.rest.app.old.entity.Account;
import com.codenaiten.template.rest.app.old.entity.Image;
import com.codenaiten.template.rest.app.old.entity.User;
import com.codenaiten.template.rest.app.old.exception.data.found.AccountNotFoundByIdException;
import com.codenaiten.template.rest.app.old.exception.data.found.ImageNotFoundByIdException;
import com.codenaiten.template.rest.app.old.exception.security.AuthNotFoundException;
import com.codenaiten.template.rest.app.old.exception.security.IncorrectPasswordException;
import com.codenaiten.template.rest.app.old.factory.AccountFactory;
import com.codenaiten.template.rest.app.old.factory.UserFactory;
import com.codenaiten.template.rest.app.old.file.ImageFileManager;
import com.codenaiten.template.rest.app.old.mapper.AccountMapper;
import com.codenaiten.template.rest.app.old.policy.*;
import com.codenaiten.template.rest.app.old.properties.AppProperties;
import com.codenaiten.template.rest.app.old.properties.LocaleProperties;
import com.codenaiten.template.rest.app.old.repository.AccountRepository;
import com.codenaiten.template.rest.app.old.repository.ImageRepository;
import com.codenaiten.template.rest.app.old.repository.UserRepository;
import com.codenaiten.template.rest.app.old.vo.Email;
import com.codenaiten.template.rest.app.old.vo.account.AccountId;
import com.codenaiten.template.rest.app.old.vo.account.AccountPassword;
import com.codenaiten.template.rest.app.old.vo.image.ImageId;
import com.codenaiten.template.rest.app.old.vo.user.UserName;
import com.codenaiten.template.rest.app.old.vo.user.UserSurname;
import com.codenaiten.template.rest.app.old.vo.user.UserUsername;
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

    /** Properties con información relacionada con los detalles de la aplicación */
    private final AppProperties appProperties;

    /** Properties con información relacionada con los detalles del lenguaje del sistema */
    private final LocaleProperties localeProperties;

    /** Provider relacionado con la autenticación de un usuario */
    private final AuthenticationProvider authenticationProvider;

    /** Manager relacionado con las operaciones relacionadas con el cifrado de contraseñas */
    private final PasswordEncoderManager passwordEncoderManager;

    /** Manager relacionado con las operaciones relacionadas con los ficheros de las imágenes */
    private final ImageFileManager imageFileManager;

    /** Repository relacionado con la entidad {@link Image} */
    private final ImageRepository imageRepository;

    /** Repository relacionado con la entidad {@link Account} */
    private final AccountRepository accountRepository;

    /** Repository relacionado con la entidad {@link User} */
    private final UserRepository userRepository;

    /** Mapper relacionado con la entidad {@link Account} */
    private final AccountMapper accountMapper;

// ------------------------------------------------------------------------------------------------------------------ \\

    /** Factory para la creación de la entidad {@link Account} */
    private AccountFactory accountFactory;

    /** Factory para la creación de la entidad {@link User} */
    private UserFactory userFactory;

    /** Editor para la actualización de la entidad {@link Account} */
    private AccountEditor accountEditor;

    /** Policy relacionado con las políticas de acceso a la entidad {@link Image} */
    private ImageAccessPolicy imageAccessPolicy;

    /** Policy relacionado con las políticas de acceso a la entidad {@link Account} */
    private AccountAccessPolicy accountAccessPolicy;

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| INITIALIZER |------------------------------------------------------------------------------------------------ \\
// ------------------------------------------------------------------------------------------------------------------ \\

    /**
     * Inicializa las propiedades necesarias que no son beans de Spring.
     */
    @PostConstruct
    public void init(){
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

        // Policies
        this.imageAccessPolicy = new ImageAccessPolicy();
        this.accountAccessPolicy = new AccountAccessPolicy();
    }

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| IMPLEMENTED METHODS |---------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    @Override
    public AccountInfoResult get( final AccountId id ){
        // Step 01: Get Authenticated Account
        final Account requester = this.authenticationProvider.getAuthenticatedAccount()
                .orElseThrow( AuthNotFoundException::new );

        // Step 02: Get Account by ID
        final Account account = this.accountRepository.findById( id.value() )
                .orElseThrow( () -> new AccountNotFoundByIdException( id ));

        // Step 03: Check if Authenticated Account has Read access
        this.accountAccessPolicy.checkRead( requester, account );

        // Step 04: Convert Account & Return Result
        return this.accountMapper.toInfoResult( account );
    }

// ------------------------------------------------------------------------------------------------------------------ \\

    @Override
    public AccountInfoResult me() {
        // Step 01: Get Authenticated Account
        final Account requester = this.authenticationProvider.getAuthenticatedAccount()
                .orElseThrow( AuthNotFoundException::new );

        // Step 02: Convert Account & Return Result
        return this.accountMapper.toInfoResult( requester );
    }

// ------------------------------------------------------------------------------------------------------------------ \\

    @Override
    public PageResult<AccountInfoResult> search( final String search, final PageCommand pageableCommand ){
        // Step 01: Get Authenticated Account
        final Account requester = this.authenticationProvider.getAuthenticatedAccount()
                .orElseThrow( AuthNotFoundException::new );

        // Step 02: Check if Authenticated Account has Query access
        this.accountAccessPolicy.checkQuery( requester );

        // Step 03: Create Pageable
        final Pageable pageable = PageRequest.of( pageableCommand.page(), pageableCommand.size() );

        // Step 04: Search Accounts
        final Page<Account> data;
        if( Objects.nonNull( search )) data = this.accountRepository.search( search, pageable );
        else data = this.accountRepository.findAll( pageable );

        // Step 05: Convert Account List to Result List
        final List<AccountInfoResult> content = data.getContent().stream()
                .map( this.accountMapper::toInfoResult ).toList();

        // Step 06: Create Page Result & Return Result
        return PageResult.of( data.getTotalElements(), data.getNumber(), data.getSize(), content );
    }

// ------------------------------------------------------------------------------------------------------------------ \\

    @Override
    public PageResult<AccountInfoResult> search( final FilterAccountCommand filter, final PageCommand pageableCommand ){
        // Step 01: Get Authenticated Account
        final Account requester = this.authenticationProvider.getAuthenticatedAccount()
                .orElseThrow( AuthNotFoundException::new );

        // Step 02: Check if Authenticated Account has Query access
        this.accountAccessPolicy.checkQuery( requester );

        // Step 03: Initialize Probe and Matcher
        final Account probe = new Account();
        final User userProbe = new User();
        final ExampleMatcher matcher = ExampleMatcher.matchingAll().withIgnoreNullValues().withIgnoreCase();

        // Step 04: Build Probes from Filter if exists
        if( Objects.nonNull( filter )){ // If Filter exists
            // Set Matchers
            matcher.withMatcher("lang", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase());
            matcher.withMatcher("role", ExampleMatcher.GenericPropertyMatchers.exact());
            matcher.withMatcher("email", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase());
            matcher.withMatcher("owner.username", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase());
            matcher.withMatcher("owner.name", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase());
            matcher.withMatcher("owner.surname", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase());

            // Set Probe from Filter if exists data
            Optional.ofNullable( filter.lang() ).ifPresent( probe::setLang );
            Optional.ofNullable( filter.role() ).ifPresent( probe::setRole );
            Optional.ofNullable( filter.email() ).ifPresent( probe::setEmail );

            // Set Probe for Owner from Filter if exists data
            Optional.ofNullable( filter.username() ).ifPresent(
                    value -> { userProbe.setUsername( value ); probe.setOwner( userProbe ); });
            Optional.ofNullable( filter.name() ).ifPresent(
                    value -> { userProbe.setName( value ); probe.setOwner( userProbe ); });
            Optional.ofNullable( filter.surname() ).ifPresent(
                    value -> { userProbe.setSurname( value ); probe.setOwner( userProbe ); });
        }

        // Step 05: Create Example from Probe & Matcher
        final Example<Account> example = Example.of( probe, matcher );

        // Step 06: Create Pageable
        final Pageable pageable = PageRequest.of( pageableCommand.page(), pageableCommand.size() );

        // Step 07: Search Accounts
        final Page<Account> data = this.accountRepository.findAll( example, pageable );

        // Step 08: Convert Account List to Result List
        final List<AccountInfoResult> content = data.getContent().stream()
                .map( this.accountMapper::toInfoResult ).toList();

        // Step 09: Create Page Result & Return Result
        return PageResult.of( data.getTotalElements(), data.getNumber(), data.getSize(), content );
    }

// ------------------------------------------------------------------------------------------------------------------ \\

    @Override
    @Transactional
    public AccountInfoResult create( final CreateAccountCommand command ){
        // Step 01: Get Authenticated Account
        final Account requester = this.authenticationProvider.getAuthenticatedAccount()
                .orElseThrow( AuthNotFoundException::new );

        // Step 02: Check if Authenticated Account has Create access
        this.accountAccessPolicy.checkCreate( requester );

        // Step 03: Get Provided data
        final Locale lang = command.lang();
        final ImageId imageId = command.image();
        final AccountRole role = command.role();
        final UserUsername username = command.username();
        final Email email = command.email();
        final AccountPassword password = command.password();
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

        // Step 04: Create User & Account
        final User user = this.userFactory.create( username, name, birthdate ).surname( surname ).image( imageId ).build();
        final Account account = this.accountFactory.create( user, email, password ).role( role ).lang( lang ).build();

        // Step 05: Save User & Account
        this.userRepository.save( user );
        this.accountRepository.save( account );

        // Step 06: Convert Account & Return Result
        return this.accountMapper.toInfoResult( account );
    }

// ------------------------------------------------------------------------------------------------------------------ \\

    @Override
    @Transactional
    public AccountInfoResult update( final AccountId id, final UpdateAccountCommand command ){
        // Step 01: Get Authenticated Account
        final Account requester = this.authenticationProvider.getAuthenticatedAccount()
                .orElseThrow( AuthNotFoundException::new );

        // Step 02: Get Account by ID
        final Account account = this.accountRepository.findById( id.value() )
                .orElseThrow( () -> new AccountNotFoundByIdException( id ));

        // Step 03: Check if Authenticated Account has Write access
        this.accountAccessPolicy.checkWrite( requester, account );

        // Step 04: Get Provided data
        final Locale lang = command.lang();
        final AccountRole role = command.role();
        final Email email = command.email();
        final AccountPassword password = command.password();

        // Step 05: Update Account
        final AccountEditor.Editor editor = this.accountEditor.update( account );
        editor.lang( lang ).role( role ).email( email ).password( password );

        // Step 06: Check if Account Editor has changes
        if( editor.hasChanges() ){ // If Account Editor has changes
            // Apply changes & Save Account
            editor.apply();
            this.accountRepository.save( account );
        }

        // Step 07: Convert Account & Return Result
        return this.accountMapper.toInfoResult( account );
    }

// ------------------------------------------------------------------------------------------------------------------ \\

    @Override
    @Transactional
    public AccountInfoResult updateLang( final Locale lang ){
        // Step 01: Get Authenticated Account
        final Account requester = this.authenticationProvider.getAuthenticatedAccount()
                .orElseThrow( AuthNotFoundException::new );

        // Step 02: Update Lang Account
        final AccountEditor.Editor editor = this.accountEditor.update( requester );
        editor.lang( lang );

        // Step 03: Check if Account Editor has changes
        if( editor.hasChanges() ){ // If Account Editor has changes
            // Apply changes & Save Account
            editor.apply();
            this.accountRepository.save( requester );

            // Set Lang in LocaleContextHolder
            LocaleContextHolder.setLocale( lang );
        }

        // Step 04: Convert Account & Return Result
        return this.accountMapper.toInfoResult( requester );
    }

// ------------------------------------------------------------------------------------------------------------------ \\

    @Override
    @Transactional
    public AccountInfoResult updateEmail( final AccountPassword password, final Email newEmail ){
        // Step 01: Get Authenticated Account
        final Account requester = this.authenticationProvider.getAuthenticatedAccount()
                .orElseThrow( AuthNotFoundException::new );

        // Step 02: Check if Account Password is correct
        if( this.passwordEncoderManager.notMatches( password.value(), requester.getPassword() ))
            throw new IncorrectPasswordException();

        // Step 03: Update Email Account
        final AccountEditor.Editor editor = this.accountEditor.update( requester );
        editor.email( newEmail );

        // Step 04: Check if Account Editor has changes
        if( editor.hasChanges() ){ // If Account Editor has changes
            // Apply changes & Save Account
            editor.apply();
            this.accountRepository.save( requester );
        }

        // Step 05: Convert Account & Return Result
        return this.accountMapper.toInfoResult( requester );
    }

// ------------------------------------------------------------------------------------------------------------------ \\

    @Override
    @Transactional
    public AccountInfoResult updatePassword( final AccountPassword password, final AccountPassword newPassword ){
        // Step 01: Get Authenticated Account
        final Account requester = this.authenticationProvider.getAuthenticatedAccount()
                .orElseThrow( AuthNotFoundException::new );

        // Step 02: Check if Account Password is correct
        if( this.passwordEncoderManager.notMatches( password.value(), requester.getPassword() ))
            throw new IncorrectPasswordException();

        // Step 03: Update Password Account
        final AccountEditor.Editor editor = this.accountEditor.update( requester );
        editor.password( newPassword );

        // Step 04: Check if Account Editor has changes
        if( editor.hasChanges() ){ // If Account Editor has changes
            // Apply changes & Save Account
            editor.apply();
            this.accountRepository.save( requester );
        }

        // Step 05: Convert Account & Return Result
        return this.accountMapper.toInfoResult( requester );
    }

// ------------------------------------------------------------------------------------------------------------------ \\

    @Override
    @SneakyThrows( IOException.class )
    @Transactional( rollbackFor = IOException.class )
    public AccountInfoResult delete( final AccountId id ){
        // Step 01: Get Authenticated Account
        final Account requester = this.authenticationProvider.getAuthenticatedAccount()
                .orElseThrow( AuthNotFoundException::new );

        // Step 02: Get Account by ID
        final Account account = this.accountRepository.findById( id.value() )
                .orElseThrow( () -> new AccountNotFoundByIdException( id ));

        // Step 03: Check if Authenticated Account has Delete access
        this.accountAccessPolicy.checkDelete( requester, account );

        // Step 04: Get User & ImageId data
        final User user = account.getOwner();
        final ImageId imageId = new ImageId( user.getId() );

        // Step 05: Delete User & Account
        this.userRepository.delete( user );

        // Step 06: Delete Image Profile data
        this.imageRepository.deleteById( imageId.value() );
        final File file = this.imageFileManager.get( imageId );
        this.imageFileManager.delete( file );

        // Step 07: Convert Account & Return Result
        return this.accountMapper.toInfoResult( account );
    }

// ------------------------------------------------------------------------------------------------------------------ \\

    @Override
    @SneakyThrows( IOException.class )
    @Transactional( rollbackFor = IOException.class )
    public AccountInfoResult delete( final AccountPassword password ){
        // Step 01: Get Authenticated Account
        final Account requester = this.authenticationProvider.getAuthenticatedAccount()
                .orElseThrow( AuthNotFoundException::new );

        // Step 02: Check if Account Password is correct
        if( this.passwordEncoderManager.notMatches( password.value(), requester.getPassword() ))
            throw new IncorrectPasswordException();

        // Step 03: Get User & ImageId data
        final User user = requester.getOwner();
        final ImageId imageId = new ImageId( user.getId() );

        // Step 04: Delete User & Account
        this.userRepository.delete( user );

        // Step 05: Delete Image Profile data
        this.imageRepository.deleteById( imageId.value() );
        final File file = this.imageFileManager.get( imageId );
        this.imageFileManager.delete( file );

        // Step 06: Convert Account & Return Result
        return this.accountMapper.toInfoResult( requester );
    }

// ------------------------------------------------------------------------------------------------------------------ \\

    @Override
    public List<AccountRole> getSupportedRoles() {
        // Step 01: Return Supported Account Roles
        return AccountRole.getSupportedRoles();
    }

// ------------------------------------------------------------------------------------------------------------------ \\

}
