package com.codenaiten.template.rest.core.feature.user.exception;

import com.codenaiten.template.rest.core.CoreMessageKey;
import com.codenaiten.template.rest.core.feature.user.vo.UserId;
import com.codenaiten.template.rest.core.shared.exception.LocalizedException;
import lombok.Getter;

import java.util.Optional;

@Getter
public class UserNotFoundException extends LocalizedException {

    public static final CoreMessageKey DEFAULT = CoreMessageKey.ERROR_USER_NOT_FOUND;
    public static final CoreMessageKey MESSAGE_KEY = CoreMessageKey.ERROR_USER_NOT_FOUND_BY_ID;

//--------------------------------------------------------------------------------------------------------------------\\

    private final UserId id;

//--------------------------------------------------------------------------------------------------------------------\\
//---| CONSTRUCTOR |--------------------------------------------------------------------------------------------------\\
//--------------------------------------------------------------------------------------------------------------------\\

    public UserNotFoundException( final UserId id ){
        super( MESSAGE_KEY, id );
        this.id = id;
    }

    public UserNotFoundException(){
        super( DEFAULT );
        this.id = null;
    }

    public UserNotFoundException( final Throwable cause, final UserId id ){
        super( cause, MESSAGE_KEY, id );
        this.id = id;
    }

    public UserNotFoundException( final Throwable cause ){
        super( cause, DEFAULT );
        this.id = null;
    }

//--------------------------------------------------------------------------------------------------------------------\\
//---| GETTER |-------------------------------------------------------------------------------------------------------\\
//--------------------------------------------------------------------------------------------------------------------\\

    public Optional<UserId> getId() {
        return Optional.ofNullable( this.id );
    }

//--------------------------------------------------------------------------------------------------------------------\\

}
