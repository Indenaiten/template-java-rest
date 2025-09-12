package com.codenaiten.template.rest.core.feature.user.service;

import com.codenaiten.template.rest.core.feature.account.Account;
import com.codenaiten.template.rest.core.feature.account.constraint.AccountIdConstraint;
import com.codenaiten.template.rest.core.feature.account.constraint.AccountOwnerConstraint;
import com.codenaiten.template.rest.core.feature.account.port.AccountRepository;
import com.codenaiten.template.rest.core.feature.media.vo.MediaId;
import com.codenaiten.template.rest.core.feature.user.User;
import com.codenaiten.template.rest.core.feature.user.constraint.*;
import com.codenaiten.template.rest.core.feature.user.dto.input.CreateUserInput;
import com.codenaiten.template.rest.core.feature.user.port.UserRepository;
import com.codenaiten.template.rest.core.feature.user.vo.UserName;
import com.codenaiten.template.rest.core.feature.user.vo.UserRole;
import com.codenaiten.template.rest.core.feature.user.vo.UserSurname;
import com.codenaiten.template.rest.core.feature.user.vo.UserUsername;
import com.codenaiten.template.rest.core.shared.spi.LanguageProvider;
import com.codenaiten.template.rest.core.shared.spi.LanguageResolver;
import com.codenaiten.template.rest.core.shared.spi.PasswordEncoder;
import com.codenaiten.template.rest.core.shared.vo.Email;
import com.codenaiten.template.rest.core.shared.vo.EncodedPassword;
import com.codenaiten.template.rest.core.shared.vo.Language;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Slf4j
@Component
@RequiredArgsConstructor
public class CreateUserService {

    // Ports
    private final UserRepository userRepository;
    private final AccountRepository accountRepository;
    private final LanguageProvider languageProvider;
    private final PasswordEncoder passwordEncoder;
    private final LanguageResolver languageResolver;

    // User Constraints
    private final UserIdConstraint userIdConstraint;
    private final UserEmailConstraint userEmailConstraint;
    private final UserUsernameConstraint userUsernameConstraint;
    private final UserBirthdateConstraint userBirthdateConstraint;
    private final UserImageConstraint userImageConstraint;

    // Account Validators
    private final AccountIdConstraint accountIdConstraint;
    private final AccountOwnerConstraint accountOwnerConstraint;

//--------------------------------------------------------------------------------------------------------------------\\
//---| RESULT |-------------------------------------------------------------------------------------------------------\\
//--------------------------------------------------------------------------------------------------------------------\\

    public record Result ( User user, Account account ){}

//--------------------------------------------------------------------------------------------------------------------\\
//---| METHODS |------------------------------------------------------------------------------------------------------\\
//--------------------------------------------------------------------------------------------------------------------\\

    public Result create( final CreateUserInput input ){
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
        final EncodedPassword password = this.passwordEncoder.encode( input.getUserPassword().value() );

        // Step 03: Create User with Factory
        final User user = User.create( email, username, role, name, birthdate, password ).image( image ).surname( surname )
                .build( this.userIdConstraint,
                        this.userEmailConstraint,
                        this.userUsernameConstraint,
                        this.userBirthdateConstraint,
                        this.userImageConstraint );

        // Step 04: Get Default Account Data
        final Language defaultLang = this.languageProvider.getDefaultLanguage();

        // Step 05: Get Account Data
        final Language lang = input.getLanguage().orElse( defaultLang );

        // Step 06: Create Account with Factory
        final Account account = Account.create( user.getId() ).language( lang )
                .build( this.accountIdConstraint, this.accountOwnerConstraint );

        // Step 07: Save User and Account
        this.userRepository.save( user );
        this.accountRepository.save( account );
        this.languageResolver.setLanguage( account.getLang() );

        // Step 08: Return Result
        return new Result( user, account );
    }

//--------------------------------------------------------------------------------------------------------------------\\

}
