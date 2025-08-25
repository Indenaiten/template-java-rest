package com.codenaiten.template.rest.app.service;

import com.codenaiten.template.rest.app.api.AuthenticationService;
import com.codenaiten.template.rest.app.authentication.AuthenticatedUser;
import com.codenaiten.template.rest.app.authentication.PasswordEncoderManager;
import com.codenaiten.template.rest.app.authentication.TokenJwtManager;
import com.codenaiten.template.rest.app.dto.command.LoginCommand;
import com.codenaiten.template.rest.app.dto.command.RefreshTokenCommand;
import com.codenaiten.template.rest.app.dto.command.RegisterCommand;
import com.codenaiten.template.rest.app.dto.result.AccountInfoResult;
import com.codenaiten.template.rest.app.dto.result.LoginResult;
import com.codenaiten.template.rest.app.dto.result.RefreshTokenResult;
import com.codenaiten.template.rest.app.entity.Account;
import com.codenaiten.template.rest.app.entity.User;
import com.codenaiten.template.rest.app.factory.AccountFactory;
import com.codenaiten.template.rest.app.factory.UserFactory;
import com.codenaiten.template.rest.app.mapper.AccountMapper;
import com.codenaiten.template.rest.app.repository.AccountRepository;
import com.codenaiten.template.rest.app.repository.UserRepository;
import com.codenaiten.template.rest.app.vo.Email;
import com.codenaiten.template.rest.app.vo.account.AccountPassword;
import com.codenaiten.template.rest.app.vo.user.UserId;
import com.codenaiten.template.rest.app.vo.user.UserName;
import com.codenaiten.template.rest.app.vo.user.UserSurname;
import com.codenaiten.template.rest.app.vo.user.UserUsername;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final PasswordEncoderManager passwordEncoderManager;
    private final AuthenticationManager authenticationManager;
    private final TokenJwtManager jwtTokenManager;

    private final AccountRepository accountRepository;
    private final UserRepository userRepository;
    private final AccountMapper accountMapper;

    private AccountFactory accountFactory;
    private UserFactory userFactory;

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| INITIALIZER |------------------------------------------------------------------------------------------------ \\
// ------------------------------------------------------------------------------------------------------------------ \\

    @PostConstruct
    public void init(){
        this.accountFactory = new AccountFactory( this.accountRepository, this.passwordEncoderManager );
        this.userFactory = new UserFactory( this.userRepository );
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
        final Account account = this.accountFactory.create( user, email, password ).build();

        // Step 03: Save user and account
        this.userRepository.save( user );
        this.accountRepository.save( account );

        // Step 04: Return result
        return this.accountMapper.toInfoResult( account );
    }

    @Override
    public LoginResult login( final LoginCommand command ){
        // Step 01: Get provided data
        final String login = command.login();
        final AccountPassword password = command.password();

        // Step 02: Authenticate user
        final Authentication auth = this.authenticationManager.authenticate( new UsernamePasswordAuthenticationToken( login, password ));

        // Step 03: Get authenticated user if authentication is successful
        final AuthenticatedUser user = Optional.ofNullable( auth )
                .map( Authentication::getPrincipal )
                .filter( AuthenticatedUser.class::isInstance )
                .map( AuthenticatedUser.class::cast )
                .orElseThrow( () -> new AuthenticationCredentialsNotFoundException( login ));

        // Step 04: Generate JWT tokens
        final AccountInfoResult info = user.getInfo();
        final String accessToken = this.jwtTokenManager.createAccessToken( info );
        final String refreshToken = this.jwtTokenManager.createRefreshToken( info );

        // Step 05: Return result
        return new LoginResult( info.id(), info.owner().id(), accessToken, refreshToken );
    }

    @Override
    @Transactional
    public void logout() {
        // Step 01: Get authenticated user
        final AuthenticatedUser authenticatedUser = this.getAuthenticatedUser();
        
        // Step 02: Get current tokens from security context (we'll need to get these from headers)
        // For now, we'll invalidate all tokens for the user as we don't have direct access to current tokens
        final UserId userId = UserId.of( authenticatedUser.getInfo().owner().id().toString() );
        
        // Step 03: Invalidate tokens
        this.jwtTokenManager.invalidateUserTokens( userId );
    }

    @Override
    @Transactional
    public void invalidate() {
        // Step 01: Get authenticated user
        final AuthenticatedUser authenticatedUser = this.getAuthenticatedUser();
        
        // Step 02: Invalidate all tokens for the user
        final UserId userId = UserId.of( authenticatedUser.getInfo().owner().id().toString() );
        this.jwtTokenManager.invalidateUserTokens( userId );
    }

    @Override
    @Transactional
    public RefreshTokenResult refresh( final RefreshTokenCommand command ) {
        // Step 01: Validate refresh token
        final String refreshToken = command.refreshToken();
        if ( !this.jwtTokenManager.checkRefreshToken( refreshToken )) {
            throw new AuthenticationCredentialsNotFoundException( "Invalid refresh token" );
        }

        // Step 02: Extract user information from refresh token
        final UserId userId = this.jwtTokenManager.getSubjectFromRefreshToken( refreshToken );
        
        // Step 03: Find user account
        final Account account = this.accountRepository.findByOwner_Id( UUID.fromString( userId.toString() ))
                .orElseThrow( () -> new AuthenticationCredentialsNotFoundException( "User not found" ));
        
        // Step 04: Generate new tokens
        final AccountInfoResult info = this.accountMapper.toInfoResult( account );
        final String newAccessToken = this.jwtTokenManager.createAccessToken( info );
        final String newRefreshToken = this.jwtTokenManager.createRefreshToken( info );

        // Step 05: Invalidate old refresh token
        this.jwtTokenManager.invalidateSpecificTokens( userId, null, refreshToken );

        // Step 06: Return result
        return new RefreshTokenResult( info.id(), info.owner().id(), newAccessToken, newRefreshToken );
    }

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| HELPER METHODS |--------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    private AuthenticatedUser getAuthenticatedUser() {
        return Optional.ofNullable( SecurityContextHolder.getContext().getAuthentication() )
                .map( Authentication::getPrincipal )
                .filter( AuthenticatedUser.class::isInstance )
                .map( AuthenticatedUser.class::cast )
                .orElseThrow( () -> new AuthenticationCredentialsNotFoundException( "No authenticated user found" ));
    }

// ------------------------------------------------------------------------------------------------------------------ \\

}
