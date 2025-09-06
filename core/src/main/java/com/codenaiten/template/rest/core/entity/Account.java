package com.codenaiten.template.rest.core.entity;

import com.codenaiten.template.rest.core.vo.Email;
import com.codenaiten.template.rest.core.vo.EncodedPassword;
import com.codenaiten.template.rest.core.vo.Timestamp;
import com.codenaiten.template.rest.core.vo.account.AccountId;
import com.codenaiten.template.rest.core.vo.account.AccountRole;
import com.codenaiten.template.rest.core.vo.user.UserId;
import lombok.Getter;
import lombok.Setter;

import java.util.Locale;
import java.util.Objects;
import java.util.Optional;

@Getter
public class Account extends BaseEntity<AccountId> {

    private AccountId id;
    private UserId owner;
    private @Setter Locale lang;
    private AccountRole role;
    private Email email;
    private EncodedPassword password;

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| CONSTRUCTOR |------------------------------------------------------------------------------------------------ \\
// ------------------------------------------------------------------------------------------------------------------ \\

    public Account( final AccountId id, final UserId owner, final Locale lang, final AccountRole role, final Email email,
                    final EncodedPassword password, final Timestamp createdAt, final Timestamp updatedAt ){
        super( id, createdAt, updatedAt );
        this.setOwner( owner );
        this.setLang( lang );
        this.setRole( role );
        this.setEmail( email );
        this.setPassword( password );
    }

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| SETTERS |---------------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    protected void setOwner( final UserId owner ){
        if( Objects.isNull( owner ) ) throw new IllegalArgumentException( "Owner is required" );
        this.owner = owner;
    }

    public void setRole( final AccountRole role ){
        if( Objects.isNull( role )) throw new IllegalArgumentException( "Role is required" );
        this.role = role;
    }

    public void setEmail( final Email email ){
        if( Objects.isNull( email )) throw new IllegalArgumentException( "Email is required" );
        this.email = email;
    }

    public void setPassword( final EncodedPassword password ){
        if( Objects.isNull( password )) throw new IllegalArgumentException( "Encoded password is required" );
        this.password = password;
    }

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| GETTERS |---------------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    public Optional<Locale> getLang(){
        return Optional.ofNullable( this.lang );
    }

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| CHECKERS |--------------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    public boolean isOwner( final UserId user ){
        return Objects.equals( this.owner, user );
    }

    public boolean isOwner( final User user ){
        return this.isOwner( user.getId() );
    }

    public boolean hasRole( final AccountRole role ){
        return Objects.equals( this.role, role.value() );
    }

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| FACTORY |---------------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    public static Account create( final UserId owner, final AccountRole role, final Email email,
                                  final EncodedPassword password ){
        final AccountId id = AccountId.random();
        final Timestamp createdAt = Timestamp.now();
        return new Account( id, owner, null, role, email, password, createdAt, null );
    }

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| UPDATER |---------------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    public Updater update(){
        return new Updater();
    }


    public class Updater {
        private Locale lang = Account.this.lang;
        private AccountRole role = Account.this.role;
        private Email email = Account.this.email;
        private EncodedPassword password = Account.this.password;

    // -------------------------------------------------------------------------------------------------------------- \\

        public Updater Lang( final Locale lang ){
            this.lang = lang;
            return this;
        }

        public Updater Account( final AccountRole role ){
            if( Objects.isNull( role )) throw new IllegalArgumentException( "Role must not be null" );
            this.role = role;
            return this;
        }

        public Updater Email( final Email email ){
            if( Objects.isNull( email )) throw new IllegalArgumentException( "Email must not be null" );
            this.email = email;
            return this;
        }

        public Updater Password( final EncodedPassword password ){
            if( Objects.isNull( password )) throw new IllegalArgumentException( "Encoded password must not be null" );
            this.password = password;
            return this;
        }

    // -------------------------------------------------------------------------------------------------------------- \\

        public boolean hasChanges() {
            return !Objects.equals( this.lang, Account.this.lang ) ||
                   !Objects.equals( this.role, Account.this.role ) ||
                   !Objects.equals( this.email, Account.this.email ) ||
                   !Objects.equals( this.password, Account.this.password );
        }

        public void apply(){
            if( this.hasChanges() ){
                Account.this.setLang( this.lang );
                Account.this.setRole( this.role );
                Account.this.setEmail( this.email );
                Account.this.setPassword( this.password );
                Account.this.setUpdatedAt();
            }
        }
    }

// ------------------------------------------------------------------------------------------------------------------ \\

}
