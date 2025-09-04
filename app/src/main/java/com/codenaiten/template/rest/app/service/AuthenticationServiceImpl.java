package com.codenaiten.template.rest.app.service;

import com.codenaiten.template.rest.app.api.AuthenticationService;
import com.codenaiten.template.rest.app.authentication.*;
import com.codenaiten.template.rest.app.dto.command.auth.LoginCommand;
import com.codenaiten.template.rest.app.dto.command.auth.RegisterCommand;
import com.codenaiten.template.rest.app.dto.result.AccountInfoResult;
import com.codenaiten.template.rest.app.dto.result.LoginResult;
import com.codenaiten.template.rest.app.entity.Account;
import com.codenaiten.template.rest.app.entity.Image;
import com.codenaiten.template.rest.app.entity.User;
import com.codenaiten.template.rest.app.exception.InvalidRefreshTokenException;
import com.codenaiten.template.rest.app.exception.data.found.AccountNotFoundByIdException;
import com.codenaiten.template.rest.app.exception.security.AuthNotFoundException;
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
import com.codenaiten.template.rest.app.vo.image.ImageContentType;
import com.codenaiten.template.rest.app.vo.image.ImageId;
import com.codenaiten.template.rest.app.vo.user.UserName;
import com.codenaiten.template.rest.app.vo.user.UserSurname;
import com.codenaiten.template.rest.app.vo.user.UserUsername;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    /** Properties con información relacionada con la configuración de la aplicación */
    private final AppProperties appProperties;

    /** Properties con información relacionada con la configuración del lenguaje del sistema */
    private final LocaleProperties localeProperties;

    /** Provider relacionado con la autenticación de un usuario */
    private final AuthenticationProvider authenticationProvider;

    /** Manager relacionado con las operaciones relacionados con los tokens de autenticación */
    private final TokenJwtManager tokenJwtManager;

    /** Manager relacionado con las operaciones relacionadas con la autenticación de los usuarios en el sistema */
    private final AuthenticationManager authenticationManager;

    /** Manager relacionado con las operaciones relacionadas con el cifrado de contraseñas */
    private final PasswordEncoderManager passwordEncoderManager;

    /** Manager relacionado con las operaciones relacionadas con el manejo de archivos de imagenes */
    private final ImageFileManager imageFileManager;

    /** Repository relacionado con las entidades de tipo {@link Image} */
    private final ImageRepository imageRepository;

    /** Repository relacionado con las entidades de tipo {@link User} */
    private final UserRepository userRepository;

    /** Repository relacionado con las entidades de tipo {@link Account} */
    private final AccountRepository accountRepository;

    /** Mapper principal de objetos relacionados con las {@link Account} */
    private final AccountMapper accountMapper;

// ------------------------------------------------------------------------------------------------------------------ \\

    /** Factory para crear entidades de tipo {@link Image} */
    private ImageFactory imageFactory;

    /** Factory para crear entidades de tipo {@link User} */
    private UserFactory userFactory;

    /** Factory para crear entidades de tipo {@link Account} */
    private AccountFactory accountFactory;

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

        // UserFactory
        var userUsernameUniquenessPolicy = new UserUsernameUniquenessPolicy( this.userRepository );
        var userMinimumAgePolicy = new UserMinimumAgePolicy( this.appProperties );
        this.userFactory = new UserFactory( userUsernameUniquenessPolicy, userMinimumAgePolicy );

        // AccountFactory
        var supportedLanguagePolicy = new LanguageSupportedPolicy( this.localeProperties );
        var accountEmailUniquenessPolicy = new AccountEmailUniquenessPolicy( this.accountRepository );
        var assignAccountRolePolicy = new AssignAccountRolePolicy( this.accountRepository );
        this.accountFactory = new AccountFactory( supportedLanguagePolicy, accountEmailUniquenessPolicy, assignAccountRolePolicy, this.passwordEncoderManager );
    }

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| IMPLEMENTED METHODS |---------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    @Override
    @SneakyThrows( IOException.class )
    @Transactional( rollbackOn = IOException.class)
    public AccountInfoResult register( final RegisterCommand command ) {
        // Step 01: Get provided data
        final byte[] bytes = command.image();
        final ImageContentType contentType = command.imageContentType();
        final Locale lang = command.lang();
        final UserUsername username = command.username();
        final Email email = command.email();
        final AccountPassword password = command.password();
        final UserName name = command.name();
        final UserSurname surname = command.surname();
        final LocalDate birthdate = command.birthdate();

        // Step 02: Create image, user and account
        Image image = null;
        if( Objects.nonNull( bytes ) && bytes.length > 0 ) image = this.imageFactory.create( contentType ).build();
        final User user = this.userFactory.create( username, name, birthdate ).image( image ).surname( surname ).build();
        final Account account = this.accountFactory.create( user, email, password ).lang( lang ).build();

        // Step 03: Save image, user and account
        if( Objects.nonNull( image )) this.imageRepository.save( image );
        this.userRepository.save( user );
        this.accountRepository.save( account );

        // Step 04: Save image file if exists image
        if( Objects.nonNull( image )) this.imageFileManager.write( new ImageId( image.getId() ), bytes );

        // Step 05: Return result
        return this.accountMapper.toInfoResult( account );
    }

// ------------------------------------------------------------------------------------------------------------------ \\

    @Override
    public LoginResult login( final LoginCommand command, final String ip ){
        // Step 01: Get provided data
        final String login = command.login().toLowerCase().trim();
        final AccountPassword password = command.password();

        // Step 02: Authenticate user
        final Authentication auth = this.authenticationManager.authenticate( new UsernamePasswordAuthenticationToken( login, password ));

        // Step 03: Get authenticated user if authentication is successful
        final AuthenticatedUser authenticatedUser = Optional.ofNullable( auth )
                .map( Authentication::getPrincipal )
                .filter( AuthenticatedUser.class::isInstance )
                .map( AuthenticatedUser.class::cast )
                .orElseThrow( () -> new AuthenticationCredentialsNotFoundException( login ));

        // Step 04: Generate JWT token
        final Account account = authenticatedUser.getAccount();
        final TokenInfo result = this.tokenJwtManager.create( account, ip );

        // Step 05: Set Language
        account.getLang().map( Locale::forLanguageTag ).ifPresent( LocaleContextHolder::setLocale );

        // Step 06: Return result
        return new LoginResult( account.getId(), account.getOwner().getId(), result );
    }

// ------------------------------------------------------------------------------------------------------------------ \\

    @Override
    public LoginResult refresh( final String token, final String ip ){
        // Step 01: Validate token
        if( !this.tokenJwtManager.validateRefreshToken( token, ip )) throw new InvalidRefreshTokenException( token );

        // Step 02: Check if exitst Account ID & User ID
        final AccountId id = this.tokenJwtManager.getAccountId( token );
        final Account account = this.accountRepository.findById( id.value() ).orElseThrow( () -> new AccountNotFoundByIdException( id ));

        // Step 03: Generate new Tokens from refresh token
        final TokenInfo result = this.tokenJwtManager.refresh( token, ip );

        // Step 04: Return result
        return new LoginResult( account.getId(), account.getOwner().getId(), result );
    }

// ------------------------------------------------------------------------------------------------------------------ \\

    @Override
    public void logout(){
        // Step 01: Get Token from Authentication Provider
        final String token = this.authenticationProvider.getAccessToken().orElseThrow( AuthNotFoundException::new );

        // Step 02: Invalidate Token
        this.tokenJwtManager.invalidate( token );
    }

// ------------------------------------------------------------------------------------------------------------------ \\

    @Override
    public void invalidate( final List<String> ipList ){
        // Step 01: Get Authenticated User
        final Account requester = this.authenticationProvider.getAuthenticatedAccount().orElseThrow( AuthNotFoundException::new );
        final AccountId id = new AccountId( requester.getId() );

        // Step 02: Invalidate Tokens
        this.tokenJwtManager.invalidate( id, ipList );
    }

// ------------------------------------------------------------------------------------------------------------------ \\

    @Override
    public void invalidate() {
        // Step 01: Get Authenticated User
        final Account requester = this.authenticationProvider.getAuthenticatedAccount()
                .orElseThrow( AuthNotFoundException::new );
        final AccountId id = new AccountId( requester.getId() );

        // Step 02: Invalidate Tokens
        this.tokenJwtManager.invalidate( id );
    }

// ------------------------------------------------------------------------------------------------------------------ \\

}
