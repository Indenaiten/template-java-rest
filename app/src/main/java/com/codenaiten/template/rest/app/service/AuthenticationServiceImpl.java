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

    private final AppProperties appProperties;
    private final LocaleProperties localeProperties;

    private final AuthenticationProvider authenticationProvider;
    private final TokenJwtManager jwtTokenManager;

    private final PasswordEncoderManager passwordEncoderManager;
    private final AuthenticationManager authenticationManager;

    private final AccountRepository accountRepository;
    private final UserRepository userRepository;

    private final AccountMapper accountMapper;

// ------------------------------------------------------------------------------------------------------------------ \\

    private AccountFactory accountFactory;
    private UserFactory userFactory;

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| INITIALIZER |------------------------------------------------------------------------------------------------ \\
// ------------------------------------------------------------------------------------------------------------------ \\

    @PostConstruct
    public void init(){
        var supportedLanguagePolicy = new LanguageSupportedPolicy( this.localeProperties );
        var accountEmailUniquenessPolicy = new AccountEmailUniquenessPolicy( this.accountRepository );
        var assignAccountRolePolicy = new AssignAccountRolePolicy( this.accountRepository );
        this.accountFactory = new AccountFactory( supportedLanguagePolicy, accountEmailUniquenessPolicy, assignAccountRolePolicy, this.passwordEncoderManager );

        var userUsernameUniquenessPolicy = new UserUsernameUniquenessPolicy( this.userRepository );
        var userMinimumAgePolicy = new UserMinimumAgePolicy( this.appProperties );
        this.userFactory = new UserFactory( userUsernameUniquenessPolicy, userMinimumAgePolicy );
    }

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| IMPLEMENTED METHODS |---------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    @Override
    @Transactional
    public AccountInfoResult register( final RegisterCommand command ) {
        // Step 01: Get provided data
        final UserUsername username = command.username();
        final Email email = command.email();
        final AccountPassword password = command.password();
        final UserName name = command.name();
        final UserSurname surname = command.surname();
        final LocalDate birthdate = command.birthdate();

        // Step 02: Create user and account
        final User user = this.userFactory.create( username, name, birthdate ).surname( surname ).build();
        final Account account = this.accountFactory.create( user, email, password ).lang( command.lang() ).build();

        // Step 03: Save user and account
        this.userRepository.save( user );
        this.accountRepository.save( account );

        // Step 04: Return result
        return this.accountMapper.toInfoResult( account );
    }

    @Override
    public LoginResult login( final LoginCommand command, final String ip ){
        // Step 01: Get provided data
        final String login = command.login();
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
        final TokenInfo result = this.jwtTokenManager.create( account, ip );

        // Step 05: Set Language
        account.getLang().map( Locale::forLanguageTag ).ifPresent( LocaleContextHolder::setLocale );

        // Step 06: Return result
        return new LoginResult( account.getId(), account.getOwner().getId(), result );
    }

    @Override
    public LoginResult refresh( final String token, final String ip ){
        // Step 01: Validate token
        if( !this.jwtTokenManager.validateRefreshToken( token, ip )) throw new InvalidRefreshTokenException( token );

        // Step 02: Check if exitst Account ID & User ID
        final AccountId id = this.jwtTokenManager.getAccountId( token );
        final Account account = this.accountRepository.findById( id.value() ).orElseThrow( () -> new AccountNotFoundByIdException( id ));

        // Step 03: Generate new Tokens from refresh token
        final TokenInfo result = this.jwtTokenManager.refresh( token, ip );

        // Step 04: Return result
        return new LoginResult( account.getId(), account.getOwner().getId(), result );
    }

    @Override
    public void logout(){
        // Step 01: Get Token from Authentication Provider
        final String token = this.authenticationProvider.getAccessToken().orElseThrow( AuthNotFoundException::new );

        // Step 02: Invalidate Token
        this.jwtTokenManager.invalidate( token );
    }

    @Override
    public void invalidate( final List<String> ipList ){
        // Step 01: Get Authenticated User
        final Account requester = this.authenticationProvider.getAuthenticatedAccount()
                .orElseThrow( AuthNotFoundException::new );
        final AccountId id = new AccountId( requester.getId() );

        // Step 02: Invalidate Tokens
        this.jwtTokenManager.invalidate( id, ipList );
    }

    @Override
    public void invalidate() {
        // Step 01: Get Authenticated User
        final Account requester = this.authenticationProvider.getAuthenticatedAccount()
                .orElseThrow( AuthNotFoundException::new );
        final AccountId id = new AccountId( requester.getId() );

        // Step 02: Invalidate Tokens
        this.jwtTokenManager.invalidate( id );
    }

// ------------------------------------------------------------------------------------------------------------------ \\

}
