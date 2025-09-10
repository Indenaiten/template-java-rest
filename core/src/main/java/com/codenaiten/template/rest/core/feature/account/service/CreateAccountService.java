package com.codenaiten.template.rest.core.feature.account.service;

import com.codenaiten.template.rest.core.feature.account.Account;
import com.codenaiten.template.rest.core.feature.account.spi.AccountRepository;
import com.codenaiten.template.rest.core.feature.account.validation.AccountValidator;
import com.codenaiten.template.rest.core.feature.account.vo.Language;
import com.codenaiten.template.rest.core.feature.media.vo.MediaId;
import com.codenaiten.template.rest.core.feature.user.User;
import com.codenaiten.template.rest.core.feature.user.dto.input.CreateAccount;
import com.codenaiten.template.rest.core.feature.user.port.spi.UserRepository;
import com.codenaiten.template.rest.core.feature.user.validation.UserValidator;
import com.codenaiten.template.rest.core.feature.user.vo.UserName;
import com.codenaiten.template.rest.core.feature.user.vo.UserRole;
import com.codenaiten.template.rest.core.feature.user.vo.UserSurname;
import com.codenaiten.template.rest.core.feature.user.vo.UserUsername;
import com.codenaiten.template.rest.core.shared.spi.PasswordEncoder;
import com.codenaiten.template.rest.core.shared.vo.Email;
import com.codenaiten.template.rest.core.shared.vo.EncodedPassword;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Slf4j
@Component
@RequiredArgsConstructor
public class CreateAccountService {

    private final UserRepository userRepository;
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserValidator userValidator;
    private final AccountValidator accountValidator;

//--------------------------------------------------------------------------------------------------------------------\\
//---| METHODS |------------------------------------------------------------------------------------------------------\\
//--------------------------------------------------------------------------------------------------------------------\\

    public Account create( final CreateAccount input ){
        // Step 01: Get Default User Data
        final UserRole defaultRole = this.userRepository.count() == 0 ? UserRole.ADMIN : UserRole.USER;

        // Step 02: Get User Data
        final Email email = input.getEmail();
        final UserUsername username = input.getUsername();
        final UserRole role = input.getRole().orElse( defaultRole );
        final MediaId image = input.getImage().orElse( null );
        final UserName name = input.getName();
        final UserSurname surname = input.getSurname().orElse( null );
        final LocalDate birthdate = input.getBirthdate();
        final EncodedPassword password = this.passwordEncoder.encode( input.getPassword() );

        // Step 03: Create User with Factory
        final User user = User.create( email, username, role, name, birthdate, password ).image( image )
                .surname( surname ).build( this.userValidator );

        // Step 04: Get Default Account Data
        final Language defaultLang = Language.of( LocaleContextHolder.getLocale() );

        // Step 05: Get Account Data
        final Language lang = input.getLanguage().orElse( defaultLang );

        // Step 06: Create Account with Factory
        final Account account = Account.create( user ).language( lang ).build( this.accountValidator );

        // Step 07: Save User and Account
        this.userRepository.save( user );
        this.accountRepository.save( account );

        // Step 08: Return new Account
        return account;
    }

//--------------------------------------------------------------------------------------------------------------------\\

}
