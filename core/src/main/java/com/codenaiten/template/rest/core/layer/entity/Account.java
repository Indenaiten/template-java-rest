package com.codenaiten.template.rest.core.layer.entity;

import com.codenaiten.template.rest.core.layer.exception.AppException;
import com.codenaiten.template.rest.core.layer.vo.Timestamp;
import com.codenaiten.template.rest.core.layer.vo.account.AccountId;
import com.codenaiten.template.rest.core.layer.vo.user.UserId;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Locale;
import java.util.Objects;
import java.util.Optional;

@Setter
@Getter
@Builder
public class Account extends BaseEntity<AccountId>{

    private AccountId id;
    private UserId owner;
    private Locale lang;

//--------------------------------------------------------------------------------------------------------------------\\
//---| CONSTRUCTOR |--------------------------------------------------------------------------------------------------\\
//--------------------------------------------------------------------------------------------------------------------\\

    public Account( final AccountId id, final UserId owner, final Locale lang, final Timestamp createdAt,
                    final Timestamp updatedAt ){
        super( id, createdAt, updatedAt );
        this.setOwner( owner );
        this.setLang( lang );
    }

//--------------------------------------------------------------------------------------------------------------------\\
//---| CHECKERS |-----------------------------------------------------------------------------------------------------\\
//--------------------------------------------------------------------------------------------------------------------\\

    public boolean isOwner( final UserId user ){
        return Objects.equals( this.owner, user );
    }

    public boolean isOwner( final User user ){
        return this.isOwner( user.getId() );
    }

//--------------------------------------------------------------------------------------------------------------------\\
//---| SETTERS |------------------------------------------------------------------------------------------------------\\
//--------------------------------------------------------------------------------------------------------------------\\

    protected void setOwner( final UserId owner ){
        if( Objects.isNull( owner ) ) throw new AppException();
        this.owner = owner;
    }

//--------------------------------------------------------------------------------------------------------------------\\
//---| GETTERS |------------------------------------------------------------------------------------------------------\\
//--------------------------------------------------------------------------------------------------------------------\\

    public Optional<Locale> getLang(){
        return Optional.ofNullable( this.lang );
    }

//--------------------------------------------------------------------------------------------------------------------\\

}
