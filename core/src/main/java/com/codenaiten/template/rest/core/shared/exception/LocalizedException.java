package com.codenaiten.template.rest.core.shared.exception;

import com.codenaiten.template.rest.core.shared.AppMessageKey;
import lombok.Getter;

@Getter
public class LocalizedException extends AppException {

    public static final AppMessageKey DEFAULT = AppMessageKey.ERROR_GENERIC;

//--------------------------------------------------------------------------------------------------------------------\\

    private final AppMessageKey messageKey;
    private final Object[] args;

//--------------------------------------------------------------------------------------------------------------------\\
//---| CONSTRUCTOR |--------------------------------------------------------------------------------------------------\\
//--------------------------------------------------------------------------------------------------------------------\\

    public LocalizedException( final AppMessageKey messageKey, final Object... args ){
        super( messageKey.getMessage().formatted( args ));
        this.messageKey = messageKey;
        this.args = args;
    }

    public LocalizedException(){
        super( DEFAULT.getMessage() );
        this.messageKey = DEFAULT;
        this.args = new String[0];
    }

//--------------------------------------------------------------------------------------------------------------------\\

}
