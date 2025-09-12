package com.codenaiten.template.rest.core.feature.media.exception;

import com.codenaiten.template.rest.core.CoreMessageKey;
import com.codenaiten.template.rest.core.feature.media.vo.MediaId;
import com.codenaiten.template.rest.core.shared.exception.LocalizedException;
import lombok.Getter;

import java.util.Optional;

@Getter
public class MediaNotFoundException extends LocalizedException {

    public static final CoreMessageKey DEFAULT = CoreMessageKey.ERROR_MEDIA_NOT_FOUND;
    public static final CoreMessageKey MESSAGE_KEY = CoreMessageKey.ERROR_MEDIA_NOT_FOUND_BY_ID;

//--------------------------------------------------------------------------------------------------------------------\\

    private final MediaId id;

//--------------------------------------------------------------------------------------------------------------------\\
//---| CONSTRUCTOR |--------------------------------------------------------------------------------------------------\\
//--------------------------------------------------------------------------------------------------------------------\\

    public MediaNotFoundException( final MediaId id ){
        super( MESSAGE_KEY, id );
        this.id = id;
    }

    public MediaNotFoundException(){
        super( DEFAULT );
        this.id = null;
    }

    public MediaNotFoundException( final Throwable cause, final MediaId id ){
        super( cause, MESSAGE_KEY, id );
        this.id = id;
    }

    public MediaNotFoundException( final Throwable cause ){
        super( cause, DEFAULT );
        this.id = null;
    }

//--------------------------------------------------------------------------------------------------------------------\\
//---| GETTER |-------------------------------------------------------------------------------------------------------\\
//--------------------------------------------------------------------------------------------------------------------\\

    public Optional<MediaId> getId() {
        return Optional.ofNullable( this.id );
    }

//--------------------------------------------------------------------------------------------------------------------\\

}
