package com.codenaiten.template.rest.app.service;

import com.codenaiten.template.rest.app.api.AuthenticationService;
import com.codenaiten.template.rest.app.authentication.*;
import com.codenaiten.template.rest.app.dto.command.auth.LoginCommand;
import com.codenaiten.template.rest.app.dto.command.auth.RegisterCommand;
import com.codenaiten.template.rest.app.dto.result.AccountInfoResult;
import com.codenaiten.template.rest.app.dto.result.LoginResult;
import com.codenaiten.template.rest.app.entity.Account;
import com.codenaiten.template.rest.app.entity.User;
import com.codenaiten.template.rest.app.exception.InvalidRefreshTokenException;
import com.codenaiten.template.rest.app.exception.data.found.AccountNotFoundByIdException;
import com.codenaiten.template.rest.app.exception.security.AuthNotFoundException;
import com.codenaiten.template.rest.app.factory.AccountFactory;
import com.codenaiten.template.rest.app.factory.UserFactory;
import com.codenaiten.template.rest.app.mapper.AccountMapper;
import com.codenaiten.template.rest.app.policy.*;
import com.codenaiten.template.rest.app.properties.AppProperties;
import com.codenaiten.template.rest.app.properties.LocaleProperties;
import com.codenaiten.template.rest.app.repository.AccountRepository;
import com.codenaiten.template.rest.app.repository.UserRepository;
import com.codenaiten.template.rest.app.vo.Email;
import com.codenaiten.template.rest.app.vo.account.AccountId;
import com.codenaiten.template.rest.app.vo.account.AccountPassword;
import com.codenaiten.template.rest.app.vo.user.UserName;
import com.codenaiten.template.rest.app.vo.user.UserSurname;
import com.codenaiten.template.rest.app.vo.user.UserUsername;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    /** Properties con información relacionada con los detalles de la aplicación */
    private final AppProperties appProperties;

    /** Properties con información relacionada con los detalles del lenguaje del sistema */
    private final LocaleProperties localeProperties;

    /** Provider relacionado con la autenticación de un usuario */
    private final AuthenticationProvider authenticationProvider;

    /** Manager relacionado con las operaciones relacionadas con los tokens de autenticación */
    private final TokenJwtManager tokenJwtManager;

    /** Manager relacionado con las operaciones relacionadas con la autenticación de los usuarios en el sistema */
    private final AuthenticationManager authenticationManager;

    /** Manager relacionado con las operaciones relacionadas con el cifrado de contraseñas */
    private final PasswordEncoderManager passwordEncoderManager;

    /** Repository relacionado con la entidad {@link User} */
    private final UserRepository userRepository;

    /** Repository relacionado con la entidad {@link Account} */
    private final AccountRepository accountRepository;

    /** Mapper relacionado con la entidad {@link Account} */
    private final AccountMapper accountMapper;

// ------------------------------------------------------------------------------------------------------------------ \\

    /** Factory para la creación de la entidad {@link User} */
    private UserFactory userFactory;

    /** Factory para la creación de la entidad {@link Account} */
    private AccountFactory accountFactory;

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| INITIALIZER |------------------------------------------------------------------------------------------------ \\
// ------------------------------------------------------------------------------------------------------------------ \\

    /**
     * Inicializa las propiedades necesarias que no son beans de Spring.
     */
    @PostConstruct
    public void init(){
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
    @Transactional
    public AccountInfoResult register( final RegisterCommand command ) {
        // Step 01: Get Provided data
        final Locale lang = command.lang();
        final UserUsername username = command.username();
        final Email email = command.email();
        final AccountPassword password = command.password();
        final UserName name = command.name();
        final UserSurname surname = command.surname();
        final LocalDate birthdate = command.birthdate();

        // Step 02: Create User & Account
        final User user = this.userFactory.create( username, name, birthdate ).surname( surname ).build();
        final Account account = this.accountFactory.create( user, email, password ).lang( lang ).build();

        // Step 03: Save User & Account
        this.userRepository.save( user );
        this.accountRepository.save( account );

        // Step 04: Convert Account & Return Result
        return this.accountMapper.toInfoResult( account );
    }

// ------------------------------------------------------------------------------------------------------------------ \\

    @Override
    public LoginResult login( final LoginCommand command, final String ip ){
        // Step 01: Get Provided data
        final String login = command.login().toLowerCase().trim();
        final AccountPassword password = command.password();

        // Step 02: Authenticate User
        final Authentication auth = this.authenticationManager.authenticate( new UsernamePasswordAuthenticationToken( login, password ));

        // Step 03: Create Authenticated User if Authentication is Successful
        final AuthenticatedUser authenticatedUser = Optional.ofNullable( auth )
                .map( Authentication::getPrincipal )
                .filter( AuthenticatedUser.class::isInstance )
                .map( AuthenticatedUser.class::cast )
                .orElseThrow( () -> new AuthenticationCredentialsNotFoundException( login ));

        // Step 04: Generate JWT token
        final Account account = authenticatedUser.getAccount();
        final TokenInfo result = this.tokenJwtManager.create( account, ip );

        // Step 05: Set Lang in LocaleContextHolder if exists
        account.getLang().map( Locale::forLanguageTag ).ifPresent( LocaleContextHolder::setLocale );

        // Step 06: Create Login Result & Return Result
        return new LoginResult( account.getId(), account.getOwner().getId(), result );
    }

// ------------------------------------------------------------------------------------------------------------------ \\

    @Override
    public LoginResult refresh( final String token, final String ip ){
        // Step 01: Validate Token as Refresh Token
        if( !this.tokenJwtManager.validateRefreshToken( token, ip )) throw new InvalidRefreshTokenException( token );

        // Step 02: Get Account By ID from Token
        final AccountId id = this.tokenJwtManager.getAccountId( token );

        // Step 03: Find Account By ID
        final Account account = this.accountRepository.findById( id.value() )
                .orElseThrow( () -> new AccountNotFoundByIdException( id ));

        // Step 04: Generate new Tokens from Refresh Token
        final TokenInfo result = this.tokenJwtManager.refresh( token, ip );

        // Step 05: Create Login Result & Return Result
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
    public void invalidate() {
        // Step 01: Get Authenticated Account
        final Account requester = this.authenticationProvider.getAuthenticatedAccount()
                .orElseThrow( AuthNotFoundException::new );

        // Step 02: Get Account ID from Authenticated Account
        final AccountId id = new AccountId( requester.getId() );

        // Step 03: Invalidate all Tokens from Account
        this.tokenJwtManager.invalidate( id );
    }

// ------------------------------------------------------------------------------------------------------------------ \\

    @Override
    public void invalidate( final List<String> ipList ){
        // Step 01: Get Authenticated Account
        final Account requester = this.authenticationProvider.getAuthenticatedAccount()
                .orElseThrow( AuthNotFoundException::new );

        // Step 02: Get Account ID from Authenticated Account
        final AccountId id = new AccountId( requester.getId() );

        // Step 03: Invalidate all Tokens by IP list
        this.tokenJwtManager.invalidate( id, ipList );
    }

// ------------------------------------------------------------------------------------------------------------------ \\

}
