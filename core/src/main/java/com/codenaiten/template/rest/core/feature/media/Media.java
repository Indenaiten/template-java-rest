package com.codenaiten.template.rest.core.feature.media;

import com.codenaiten.template.rest.core.feature.media.vo.MediaContentSize;
import com.codenaiten.template.rest.core.feature.media.vo.MediaContentType;
import com.codenaiten.template.rest.core.feature.media.vo.MediaId;
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
public class Media extends BaseEntity<MediaId>{

    private UserId owner;
    private String namespace;
    private MediaContentType contentType;
    private MediaContentSize contentSize;

//--------------------------------------------------------------------------------------------------------------------\\
//---| CONSTRUCTOR |--------------------------------------------------------------------------------------------------\\
//--------------------------------------------------------------------------------------------------------------------\\

    public Media( final MediaId id, final UserId owner, final String namespace, final MediaContentType contentType,
                  final MediaContentSize contentSize, final Timestamp createdAt, final Timestamp updatedAt ){
        super( id, createdAt, updatedAt );
        this.setOwner( owner );
        this.setNamespace( namespace );
        this.setContentType( contentType );
        this.setContentSize( contentSize );
    }

//--------------------------------------------------------------------------------------------------------------------\\
//---| SETTERS |------------------------------------------------------------------------------------------------------\\
//--------------------------------------------------------------------------------------------------------------------\\

    protected void setOwner( final UserId owner ){
        if( Objects.isNull( owner ) ) throw new AppException();
        this.owner = owner;
    }

    public void setNamespace( final String namespace ){
        if( Objects.isNull( namespace )) throw new AppException();
        this.namespace = namespace;
    }

    public void setContentType( final MediaContentType contentType ){
        if( Objects.isNull( contentType )) throw new AppException();
        this.contentType = contentType;
    }

    public void setContentSize( final MediaContentSize contentSize ){
        if( Objects.isNull( contentSize )) throw new AppException();
        this.contentSize = contentSize;
    }

//--------------------------------------------------------------------------------------------------------------------\\
//---| GETTERS |------------------------------------------------------------------------------------------------------\\
//--------------------------------------------------------------------------------------------------------------------\\

    public Optional<String> getNamespace(){
        return Optional.ofNullable( this.namespace );
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

    public boolean isType( final MediaContentType contentType ){
        return Objects.equals( this.contentType, contentType );
    }

    public boolean isImageType(){
        return this.contentType.isImage();
    }

//--------------------------------------------------------------------------------------------------------------------\\

}
