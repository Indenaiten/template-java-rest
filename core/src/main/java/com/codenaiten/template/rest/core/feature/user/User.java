package com.codenaiten.template.rest.core.feature.user;


import com.codenaiten.template.rest.core.feature.media.vo.MediaId;
import com.codenaiten.template.rest.core.feature.user.vo.UserId;
import com.codenaiten.template.rest.core.feature.user.vo.UserName;
import com.codenaiten.template.rest.core.feature.user.vo.UserSurname;
import com.codenaiten.template.rest.core.feature.user.vo.UserUsername;
import com.codenaiten.template.rest.core.shared.entity.BaseEntity;
import com.codenaiten.template.rest.core.shared.vo.common.Timestamp;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;

@Getter
public class User extends BaseEntity<UserId>{

    private MediaId image;
    private UserUsername username;
    private UserName name;
    private @Setter UserSurname surname;
    private LocalDate birthdate;

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| CONSTRUCTOR |------------------------------------------------------------------------------------------------ \\
// ------------------------------------------------------------------------------------------------------------------ \\

    public User( final UserId id, final MediaId image, final UserUsername username, final UserName name,
                 final UserSurname surname, final LocalDate birthdate, final Timestamp createdAt,
                 final Timestamp updatedAt ){
        super( id, createdAt, updatedAt );
        this.setImage( image );
        this.setUsername( username );
        this.setName( name );
        this.setSurname( surname );
        this.setBirthdate( birthdate );
        this.setUpdatedAt( updatedAt );
    }

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| SETTERS |---------------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    public void setImage( final MediaId image ){
        if( Objects.isNull( image )) throw new IllegalArgumentException( "Image is required" );
        this.image = image;
    }

    public void setUsername( final UserUsername username ){
        if( Objects.isNull( username )) throw new IllegalArgumentException( "Username is required" );
        this.username = username;
    }

    public void setName( final UserName name ){
        if( Objects.isNull( name )) throw new IllegalArgumentException( "Name is required" );
        this.name = name;
    }

    public void setBirthdate( final LocalDate birthdate ){
        if( Objects.isNull( birthdate )) throw new IllegalArgumentException( "Birthdate is required" );
        this.birthdate = birthdate;
    }

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| GETTERS |---------------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    public Optional<MediaId> getImage() {
        return Optional.ofNullable( this.image );
    }

    public Optional<UserSurname> getSurname() {
        return Optional.ofNullable( this.surname );
    }

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| FACTORY |---------------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    public static User create( final UserUsername username, final UserName name, final LocalDate birthdate ){
        final UserId id = UserId.random();
        final Timestamp createdAt = Timestamp.now();
        return new User( id, null, username, name, null, birthdate, createdAt, null );
    }

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| UPDATER |---------------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    public Updater update(){
        return new Updater();
    }


    public class Updater {
        private MediaId image = User.this.image;
        private UserUsername username = User.this.username;
        private UserName name = User.this.name;
        private UserSurname surname = User.this.surname;
        private LocalDate birthdate = User.this.birthdate;

    // -------------------------------------------------------------------------------------------------------------- \\

        public Updater image( final MediaId image ){
            if( Objects.isNull( image )) throw new IllegalArgumentException( "User Image must not be null" );
            this.image = image;
            return this;
        }

        public Updater username( final UserUsername username ){
            if( Objects.isNull( username )) throw new IllegalArgumentException( "User Username must not be null" );
            this.username = username;
            return this;
        }

        public Updater name( final UserName name ){
            if( Objects.isNull( name )) throw new IllegalArgumentException( "User Name must not be null" );
            this.name = name;
            return this;
        }

        public Updater surname( final UserSurname surname ){
            this.surname = surname;
            return this;
        }

        public Updater birthdate( final LocalDate birthdate ){
            if( Objects.isNull( birthdate )) throw new IllegalArgumentException( "User Birthdate must not be null" );
            this.birthdate = birthdate;
            return this;
        }

    // -------------------------------------------------------------------------------------------------------------- \\

        public boolean hasChanges() {
            return !Objects.equals( this.image, User.this.image ) ||
                   !Objects.equals( this.username, User.this.username ) ||
                   !Objects.equals( this.name, User.this.name ) ||
                   !Objects.equals( this.surname, User.this.surname ) ||
                   !Objects.equals( this.birthdate, User.this.birthdate );
        }

        public void apply(){
            if( this.hasChanges() ){
                User.this.setImage( this.image );
                User.this.setUsername( this.username );
                User.this.setName( this.name );
                User.this.setSurname( this.surname );
                User.this.setBirthdate( this.birthdate );
                User.this.setUpdatedAt();
            }
        }
    }

// ------------------------------------------------------------------------------------------------------------------ \\

}
