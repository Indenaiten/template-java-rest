package com.codenaiten.template.rest.app.service;

import com.codenaiten.template.rest.app.api.AccountService;
import com.codenaiten.template.rest.app.authentication.AuthenticationProvider;
import com.codenaiten.template.rest.app.authentication.PasswordEncoderManager;
import com.codenaiten.template.rest.app.dto.command.PageCommand;
import com.codenaiten.template.rest.app.dto.command.account.AccountCreateCommand;
import com.codenaiten.template.rest.app.dto.command.account.AccountUpdateCommand;
import com.codenaiten.template.rest.app.dto.result.AccountInfoResult;
import com.codenaiten.template.rest.app.dto.result.PageResult;
import com.codenaiten.template.rest.app.editor.AccountEditor;
import com.codenaiten.template.rest.app.entity.Account;
import com.codenaiten.template.rest.app.entity.User;
import com.codenaiten.template.rest.app.exception.data.found.AccountNotFoundByIdException;
import com.codenaiten.template.rest.app.exception.security.AuthNotFoundException;
import com.codenaiten.template.rest.app.exception.security.IncorrectPasswordException;
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
import com.codenaiten.template.rest.app.vo.account.AccountRole;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AppProperties appProperties;
    private final LocaleProperties localeProperties;

    private final AuthenticationProvider authenticationProvider;
    private final PasswordEncoderManager passwordEncoderManager;

    private final UserRepository userRepository;
    private final AccountRepository accountRepository;

    private final AccountMapper accountMapper;

// ------------------------------------------------------------------------------------------------------------------ \\

    private UserFactory userFactory;
    private AccountFactory accountFactory;

    private AccountEditor accountEditor;

    private AccountAccessPolicy accountAccessPolicy;

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| INITIALIZER |------------------------------------------------------------------------------------------------ \\
// ------------------------------------------------------------------------------------------------------------------ \\

    @PostConstruct
    public void init(){
        this.accountAccessPolicy = new AccountAccessPolicy();

        var supportedLanguagePolicy = new LanguageSupportedPolicy( this.localeProperties );
        var accountEmailUniquenessPolicy = new AccountEmailUniquenessPolicy( this.accountRepository );
        var assignAccountRolePolicy = new AssignAccountRolePolicy( this.accountRepository );
        this.accountFactory = new AccountFactory( supportedLanguagePolicy, accountEmailUniquenessPolicy, assignAccountRolePolicy, this.passwordEncoderManager );
        this.accountEditor = new AccountEditor( supportedLanguagePolicy, accountEmailUniquenessPolicy, assignAccountRolePolicy, this.passwordEncoderManager );

        var userUsernameUniquenessPolicy = new UserUsernameUniquenessPolicy( this.userRepository );
        var userMinimumAgePolicy = new UserMinimumAgePolicy( this.appProperties );
        this.userFactory = new UserFactory( userUsernameUniquenessPolicy, userMinimumAgePolicy );
    }

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| IMPLEMENTED METHODS |---------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    @Override
    public AccountInfoResult me() {
        final Account requester = this.authenticationProvider.getAuthenticatedAccount()
                .orElseThrow( AuthNotFoundException::new );

        return this.accountMapper.toInfoResult( requester );
    }

    @Override
    public AccountInfoResult get( final AccountId id ){
        final Account requester = this.authenticationProvider.getAuthenticatedAccount()
                .orElseThrow( AuthNotFoundException::new );
        final Account account = this.accountRepository.findById( id.value() )
                .orElseThrow( () -> new AccountNotFoundByIdException( id ));
        this.accountAccessPolicy.checkRead( requester, account );

        return this.accountMapper.toInfoResult( account );
    }

    @Override
    public PageResult<AccountInfoResult> getAll( final PageCommand command ){
        final Account requester = this.authenticationProvider.getAuthenticatedAccount()
                .orElseThrow( AuthNotFoundException::new );
        this.accountAccessPolicy.checkQuery( requester );

        final Pageable pageable = PageRequest.of( command.page(), command.size() );
        final Page<Account> accounts = this.accountRepository.findAll( pageable );
        final List<AccountInfoResult> content = accounts.getContent().stream().map( this.accountMapper::toInfoResult ).toList();
        return new PageResult<>( accounts.getTotalElements(), accounts.getNumber(), accounts.getSize(), content );
    }

    @Override
    public PageResult<AccountInfoResult> search( final String search, final PageCommand command ){
        final Account requester = this.authenticationProvider.getAuthenticatedAccount()
                .orElseThrow( AuthNotFoundException::new );
        this.accountAccessPolicy.checkQuery( requester );

        final Pageable pageable = PageRequest.of( command.page(), command.size() );
        final Page<Account> accounts = this.accountRepository.search( search, pageable );
        final List<AccountInfoResult> content = accounts.getContent().stream().map( this.accountMapper::toInfoResult ).toList();
        return new PageResult<>( accounts.getTotalElements(), accounts.getNumber(), accounts.getSize(), content );
    }

    @Override
    public List<AccountRole> getSupportedRoles() {
        return AccountRole.getSupportedRoles();
    }

    @Override
    public AccountInfoResult create( final AccountCreateCommand command ){
        final Account requester = this.authenticationProvider.getAuthenticatedAccount()
                .orElseThrow( AuthNotFoundException::new );
        this.accountAccessPolicy.checkCreate( requester );

        final User user = this.userFactory.create( command.username(), command.name(), command.birthdate() ).surname( command.surname() ).build();
        final Account account = this.accountFactory.create( user, command.email(), command.password() )
                .role( command.role() ).lang( command.lang() ).build();

        this.userRepository.save( user );
        this.accountRepository.save( account );

        return this.accountMapper.toInfoResult( account );
    }

    @Override
    public AccountInfoResult update( final AccountId id, final AccountUpdateCommand command ){
        final Account requester = this.authenticationProvider.getAuthenticatedAccount()
                .orElseThrow( AuthNotFoundException::new );
        final Account account = this.accountRepository.findById( id.value() )
                .orElseThrow( () -> new AccountNotFoundByIdException( id ));
        this.accountAccessPolicy.checkWrite( requester, account );

        final AccountEditor.Editor editor = this.accountEditor.update( account );
        editor.lang( command.lang() ).role( command.role() ).email( command.email() ).password( command.password() );

        if( editor.hasChanges() ){
            editor.apply();
            this.accountRepository.save( account );
            requester.getLang().map( Locale::forLanguageTag ).ifPresent( LocaleContextHolder::setLocale );
        }

        return this.accountMapper.toInfoResult( account );
    }

    @Override
    public AccountInfoResult updateLang( final Locale lang ){
        final Account requester = this.authenticationProvider.getAuthenticatedAccount()
                .orElseThrow( AuthNotFoundException::new );

        final AccountEditor.Editor editor = this.accountEditor.update( requester );
        editor.lang( lang );

        if( editor.hasChanges() ){
            editor.apply();
            this.accountRepository.save( requester );
            requester.getLang().map( Locale::forLanguageTag ).ifPresent( LocaleContextHolder::setLocale );
        }

        return this.accountMapper.toInfoResult( requester );
    }

    @Override
    public AccountInfoResult updateEmail( final AccountPassword password, final Email newEmail ){
        final Account requester = this.authenticationProvider.getAuthenticatedAccount()
                .orElseThrow( AuthNotFoundException::new );
        if( !this.passwordEncoderManager.check( password.value(), requester.getPassword() )) throw new IncorrectPasswordException();

        final AccountEditor.Editor editor = this.accountEditor.update( requester );
        editor.email( newEmail );

        if( editor.hasChanges() ){
            editor.apply();
            this.accountRepository.save( requester );
        }

        return this.accountMapper.toInfoResult( requester );
    }

    @Override
    public AccountInfoResult updatePassword( final AccountPassword password, final AccountPassword newPassword ){
        final Account requester = this.authenticationProvider.getAuthenticatedAccount()
                .orElseThrow( AuthNotFoundException::new );
        if( !this.passwordEncoderManager.check( password.value(), requester.getPassword() )) throw new IncorrectPasswordException();

        final AccountEditor.Editor editor = this.accountEditor.update( requester );
        editor.password( newPassword );

        if( editor.hasChanges() ){
            editor.apply();
            this.accountRepository.save( requester );
        }

        return this.accountMapper.toInfoResult( requester );
    }

    @Override
    public AccountInfoResult delete( final AccountId id ){
        final Account requester = this.authenticationProvider.getAuthenticatedAccount()
                .orElseThrow( AuthNotFoundException::new );
        final Account account = this.accountRepository.findById( id.value() )
                .orElseThrow( () -> new AccountNotFoundByIdException( id ));
        this.accountAccessPolicy.checkDelete( requester, account );

        this.userRepository.delete( account.getOwner() );
        return this.accountMapper.toInfoResult( account );
    }

    @Override
    public AccountInfoResult delete( final AccountPassword password ){
        final Account requester = this.authenticationProvider.getAuthenticatedAccount()
                .orElseThrow( AuthNotFoundException::new );
        if( !this.passwordEncoderManager.check( password.value(), requester.getPassword() )) throw new IncorrectPasswordException();

        this.userRepository.delete( requester.getOwner() );
        return this.accountMapper.toInfoResult( requester );
    }

// ------------------------------------------------------------------------------------------------------------------ \\

}
