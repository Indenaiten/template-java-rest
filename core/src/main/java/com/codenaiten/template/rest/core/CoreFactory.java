package com.codenaiten.template.rest.core;

import com.codenaiten.template.rest.core.feature.account.constraint.AccountIdConstraint;
import com.codenaiten.template.rest.core.feature.account.constraint.AccountOwnerConstraint;
import com.codenaiten.template.rest.core.feature.account.port.AccountRepository;
import com.codenaiten.template.rest.core.feature.media.constraint.MediaIdConstraint;
import com.codenaiten.template.rest.core.feature.media.constraint.MediaOwnerConstraint;
import com.codenaiten.template.rest.core.feature.media.port.MediaRepository;
import com.codenaiten.template.rest.core.feature.user.constraint.*;
import com.codenaiten.template.rest.core.feature.user.port.MinimumUserAgeProvider;
import com.codenaiten.template.rest.core.feature.user.port.UserRepository;
import com.codenaiten.template.rest.core.feature.user.service.CreateUserService;
import com.codenaiten.template.rest.core.feature.user.service.UpdateUserService;
import com.codenaiten.template.rest.core.shared.spi.LanguageProvider;
import com.codenaiten.template.rest.core.shared.spi.LanguageResolver;
import com.codenaiten.template.rest.core.shared.spi.PasswordEncoder;

public abstract class CoreFactory {

//--------------------------------------------------------------------------------------------------------------------\\
//---| COMMON |-------------------------------------------------------------------------------------------------------\\
//--------------------------------------------------------------------------------------------------------------------\\

    // Common - Ports
    abstract LanguageProvider languageProvider();
    abstract LanguageResolver languageResolver();
    abstract PasswordEncoder passwordEncoder();

//--------------------------------------------------------------------------------------------------------------------\\
//---| USER |---------------------------------------------------------------------------------------------------------\\
//--------------------------------------------------------------------------------------------------------------------\\

    // User - Ports
    abstract UserRepository userRepository();
    abstract MinimumUserAgeProvider minimumUserAgeProvider();

//--------------------------------------------------------------------------------------------------------------------\\

    // User Constraint - UserIdConstraint
    protected UserIdConstraint userIdConstraint(){
        return new UserIdConstraint( this.userRepository() );
    }

    // User Constraint - UserEmailConstraint
    protected UserEmailConstraint userEmailConstraint(){
        return new UserEmailConstraint( this.userRepository() );
    }

    // User Constraint - UserUsernameConstraint
    protected UserUsernameConstraint userUsernameConstraint(){
        return new UserUsernameConstraint( this.userRepository() );
    }

    // User Constraint - UserImageConstraint
    protected UserImageConstraint userImageConstraint(){
        return new UserImageConstraint( this.mediaRepository() );
    }

    // User Constraint - UserBirthdateConstraint
    protected UserBirthdateConstraint userBirthdateConstraint(){
        return new UserBirthdateConstraint( this.minimumUserAgeProvider() );
    }

//--------------------------------------------------------------------------------------------------------------------\\

    // User Service - CreateUserService
    public CreateUserService createUserService(){
        return new CreateUserService( this.userRepository(), this.accountRepository(), this.languageProvider(),
                this.passwordEncoder(), this.userIdConstraint(), this.userEmailConstraint(), this.userUsernameConstraint(),
                this.userBirthdateConstraint(), this.userImageConstraint(), this.accountIdConstraint(), this.accountOwnerConstraint() );
    }

    // User Service - UpdateUserService
    protected UpdateUserService updateUserService(){
        return new UpdateUserService( this.userRepository(), this.passwordEncoder(),  this.userEmailConstraint(),
                this.userUsernameConstraint(), this.userBirthdateConstraint(), this.userImageConstraint() );
    }

//--------------------------------------------------------------------------------------------------------------------\\
//---| ACCOUNT |------------------------------------------------------------------------------------------------------\\
//--------------------------------------------------------------------------------------------------------------------\\

    // Account - Ports
    abstract AccountRepository accountRepository();

//--------------------------------------------------------------------------------------------------------------------\\

    // Account Constraint - AccountIdConstraint
    protected AccountIdConstraint accountIdConstraint(){
        return new AccountIdConstraint( this.accountRepository() );
    }

    // Account Constraint - AccountOwnerConstraint
    protected AccountOwnerConstraint accountOwnerConstraint(){
        return new AccountOwnerConstraint( this.userRepository(), this.accountRepository() );
    }

//--------------------------------------------------------------------------------------------------------------------\\
//---| MEDIA |--------------------------------------------------------------------------------------------------------\\
//--------------------------------------------------------------------------------------------------------------------\\

    // Media - Ports
    abstract MediaRepository mediaRepository();

//--------------------------------------------------------------------------------------------------------------------\\

    // Media Constraint - MediaIdConstraint
    protected MediaIdConstraint mediaIdConstraint(){
        return new MediaIdConstraint( this.mediaRepository() );
    }

    // Media Constraint - MediaOwnerConstraint
    protected MediaOwnerConstraint mediaOwnerConstraint(){
        return new MediaOwnerConstraint( this.userRepository() );
    }

//--------------------------------------------------------------------------------------------------------------------\\

}
