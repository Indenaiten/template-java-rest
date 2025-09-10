package com.codenaiten.template.rest.core.feature.account;

import com.codenaiten.template.rest.core.feature.account.vo.AccountId;
import com.codenaiten.template.rest.core.feature.account.vo.Language;
import com.codenaiten.template.rest.core.feature.user.User;
import com.codenaiten.template.rest.core.feature.user.vo.UserId;
import com.codenaiten.template.rest.core.shared.entity.BaseEntity;
import com.codenaiten.template.rest.core.shared.exception.AppException;
import com.codenaiten.template.rest.core.shared.vo.Timestamp;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.Objects;
import java.util.Optional;

@Getter
@SuperBuilder
public class Account extends BaseEntity<AccountId>{

    private AccountId id;
    private User owner;
    private Language lang;

//--------------------------------------------------------------------------------------------------------------------\\
//---| CONSTRUCTOR |--------------------------------------------------------------------------------------------------\\
//--------------------------------------------------------------------------------------------------------------------\\

    public Account( final AccountId id, final User owner, final Language lang, final Timestamp createdAt,
                    final Timestamp updatedAt ){
        super( id, createdAt, updatedAt );
        this.setOwner( owner );
        this.setLang( lang );
    }

//--------------------------------------------------------------------------------------------------------------------\\
//---| CHECKERS |-----------------------------------------------------------------------------------------------------\\
//--------------------------------------------------------------------------------------------------------------------\\

    public boolean isOwner( final UserId user ){
        return Objects.equals( this.owner.getId(), user );
    }

    public boolean isOwner( final User user ){
        return this.isOwner( user.getId() );
    }

//--------------------------------------------------------------------------------------------------------------------\\
//---| SETTERS |------------------------------------------------------------------------------------------------------\\
//--------------------------------------------------------------------------------------------------------------------\\

    protected void setOwner( final User owner ){
        if( Objects.isNull( owner )) throw new AppException();
        this.owner = owner;
    }

    public void setLang( final Language lang ){
        if( Objects.isNull( lang )) throw new AppException();
        this.lang = lang;
    }

//--------------------------------------------------------------------------------------------------------------------\\
//---| GETTERS |------------------------------------------------------------------------------------------------------\\
//--------------------------------------------------------------------------------------------------------------------\\

    public Language getLang(){
        return Optional.ofNullable( this.lang ).orElse( Language.ES_ES );
    }

//--------------------------------------------------------------------------------------------------------------------\\
//---| CREATOR |------------------------------------------------------------------------------------------------------\\
//--------------------------------------------------------------------------------------------------------------------\\

    public static AccountFactory create( final User owner ){
        return new AccountFactory( owner );
    }

//--------------------------------------------------------------------------------------------------------------------\\

}
