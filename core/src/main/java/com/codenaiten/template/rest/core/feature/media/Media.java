package com.codenaiten.template.rest.core.feature.media;

import com.codenaiten.template.rest.core.feature.media.vo.MediaContentType;
import com.codenaiten.template.rest.core.feature.media.vo.MediaId;
import com.codenaiten.template.rest.core.feature.user.User;
import com.codenaiten.template.rest.core.feature.user.vo.UserId;
import com.codenaiten.template.rest.core.shared.entity.BaseEntity;
import com.codenaiten.template.rest.core.shared.vo.common.Timestamp;
import lombok.Getter;

import java.util.Objects;
import java.util.Optional;

@Getter
public class Media extends BaseEntity<MediaId>{

    private MediaId id;
    private UserId owner;
    private String namespace;
    private MediaContentType contentType;
    private Long contentSize;

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| CONSTRUCTOR |------------------------------------------------------------------------------------------------ \\
// ------------------------------------------------------------------------------------------------------------------ \\

    public Media( final MediaId id, final UserId owner, final String namespace, final MediaContentType contentType,
                  final Long contentSize, final Timestamp createdAt, final Timestamp updatedAt ){
        super( id, createdAt, updatedAt );
        this.setOwner( owner );
        this.setNamespace( namespace );
        this.setContentType( contentType );
        this.setContentSize( contentSize );
    }

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| SETTERS |---------------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    protected void setOwner( final UserId owner ){
        if( Objects.isNull( owner ) ) throw new IllegalArgumentException( "Media Owner is required" );
        this.owner = owner;
    }

    public void setNamespace( final String namespace ){
        if( Objects.isNull( namespace )) throw new IllegalArgumentException( "Media Namespace is required" );
        this.namespace = namespace;
    }

    public void setContentType( final MediaContentType contentType ){
        if( Objects.isNull( contentType )) throw new IllegalArgumentException( "Media Content Type is required" );
        this.contentType = contentType;
    }

    public void setContentSize( final Long contentSize ){
        if( Objects.isNull( contentSize )) throw new IllegalArgumentException( "Media Content Size is required" );
        this.contentSize = contentSize;
    }

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| GETTERS |---------------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    public Optional<String> getNamespace(){
        return Optional.ofNullable( this.namespace );
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

    public boolean isType( final MediaContentType contentType ){
        return Objects.equals( this.contentType, contentType );
    }

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| FACTORY |---------------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    public static Media create( final UserId owner, final MediaContentType contentType, final Long contentSize ){
        final MediaId id = MediaId.random();
        final Timestamp createdAt = Timestamp.now();
        return new Media( id, owner, null, contentType, contentSize, createdAt, null );
    }

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| UPDATER |---------------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    public Updater update(){
        return new Updater();
    }


    public class Updater {
        private String namespace = Media.this.namespace;
        private MediaContentType contentType = Media.this.contentType;
        private Long contentSize = Media.this.contentSize;

    // -------------------------------------------------------------------------------------------------------------- \\

        public Updater namespace( final String namespace ){
            this.namespace = namespace;
            return this;
        }

        public Updater contentType( final MediaContentType contentType ){
            if( Objects.isNull( contentType )) throw new IllegalArgumentException( "Media Content Type must not be null" );
            this.contentType = contentType;
            return this;
        }

        public Updater contentSize( final Long contentSize ){
            if( Objects.isNull( contentSize )) throw new IllegalArgumentException( "Media Content Size must not be null" );
            this.contentSize = contentSize;
            return this;
        }

    // -------------------------------------------------------------------------------------------------------------- \\

        public boolean hasChanges() {
            return !Objects.equals( this.namespace, Media.this.namespace ) ||
                   !Objects.equals( this.contentType, Media.this.contentType ) ||
                   !Objects.equals( this.contentSize, Media.this.contentSize );
        }

        public void apply(){
            if( this.hasChanges() ){
                Media.this.setNamespace( this.namespace );
                Media.this.setContentType( this.contentType );
                Media.this.setContentSize( this.contentSize );
                Media.this.setUpdatedAt();
            }
        }
    }

// ------------------------------------------------------------------------------------------------------------------ \\

}
