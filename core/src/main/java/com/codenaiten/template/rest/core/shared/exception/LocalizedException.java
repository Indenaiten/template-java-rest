package com.codenaiten.template.rest.core.shared.exception;

import com.codenaiten.template.rest.core.CoreMessageKey;
import com.codenaiten.template.rest.core.shared.i18n.MessageLocalized;
import lombok.Getter;

@Getter
public class LocalizedException extends AppException implements MessageLocalized {

    public static final CoreMessageKey DEFAULT = CoreMessageKey.ERROR_GENERIC;

//--------------------------------------------------------------------------------------------------------------------\\

    private final CoreMessageKey messageKey;
    private final Object[] messageArgs;

//--------------------------------------------------------------------------------------------------------------------\\
//---| CONSTRUCTOR |--------------------------------------------------------------------------------------------------\\
//--------------------------------------------------------------------------------------------------------------------\\

    public LocalizedException(final CoreMessageKey messageKey, final Object... args ){
        super( messageKey.getMessage().formatted( args ));
        this.messageKey = messageKey;
        this.messageArgs = args;
    }

    public LocalizedException(){
        super( DEFAULT.getMessage() );
        this.messageKey = DEFAULT;
        this.messageArgs = new String[0];
    }

    public LocalizedException(final Throwable cause, final CoreMessageKey messageKey, final Object... args ){
        super( cause, messageKey.getMessage().formatted( args ) );
        this.messageKey = messageKey;
        this.messageArgs = args;
    }

    public LocalizedException( final Throwable cause ){
        super( cause, DEFAULT.getMessage() );
        this.messageKey = DEFAULT;
        this.messageArgs = new String[0];
    }

//--------------------------------------------------------------------------------------------------------------------\\

}
