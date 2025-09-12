package com.codenaiten.template.rest.core.feature.user;

import com.codenaiten.template.rest.core.CoreMessageKey;
import com.codenaiten.template.rest.core.feature.media.vo.MediaId;
import com.codenaiten.template.rest.core.feature.user.vo.*;
import com.codenaiten.template.rest.core.shared.entity.BaseEntity;
import com.codenaiten.template.rest.core.shared.exception.ValidationException;
import com.codenaiten.template.rest.core.shared.vo.Email;
import com.codenaiten.template.rest.core.shared.vo.EncodedPassword;
import com.codenaiten.template.rest.core.shared.vo.Timestamp;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;

@Getter
public class User extends BaseEntity<UserId>{

    private Email email;
    private UserUsername username;
    private UserRole role;
    private @Setter MediaId image;
    private UserName name;
    private @Setter UserSurname surname;
    private LocalDate birthdate;
    private EncodedPassword password;

//--------------------------------------------------------------------------------------------------------------------\\
//---| CONSTRUCTOR |--------------------------------------------------------------------------------------------------\\
//--------------------------------------------------------------------------------------------------------------------\\

    public User( final UserId id, final Email email, final UserUsername username, final UserRole role,
                 final MediaId image, final UserName name, final UserSurname surname, final LocalDate birthdate,
                 final EncodedPassword password, final Timestamp createdAt, final Timestamp updatedAt ){
        super( id, createdAt, updatedAt );
        this.setEmail( email );
        this.setUsername( username );
        this.setRole( role );
        this.setImage( image );
        this.setSurname( surname );
        this.setName( name );
        this.setBirthdate( birthdate );
        this.setPassword( password );
    }

//--------------------------------------------------------------------------------------------------------------------\\
//---| COPY |---------------------------------------------------------------------------------------------------------\\
//--------------------------------------------------------------------------------------------------------------------\\

    @Override
    public User copy() {
        return new User( this.id, this.email, this.username, this.role, this.image, this.name, this.surname,
                this.birthdate, this.password, this.createdAt, this.updatedAt );
    }

//--------------------------------------------------------------------------------------------------------------------\\
//---| SETTERS |------------------------------------------------------------------------------------------------------\\
//--------------------------------------------------------------------------------------------------------------------\\

    public void setEmail( final Email email ){
        if( Objects.isNull( email )) throw new ValidationException( CoreMessageKey.ERROR_VALIDATION_USER_EMAIL_REQUIRED );
        this.email = email;
    }

    public void setUsername( final UserUsername username ){
        if( Objects.isNull( username )) throw new ValidationException( CoreMessageKey.ERROR_VALIDATION_USER_USERNAME_REQUIRED );
        this.username = username;
    }

    public void setRole( final UserRole role ){
        if( Objects.isNull( role )) throw new ValidationException( CoreMessageKey.ERROR_VALIDATION_USER_ROLE_REQUIRED );
        this.role = role;
    }

    public void setName( final UserName name ){
        if( Objects.isNull( name )) throw new ValidationException( CoreMessageKey.ERROR_VALIDATION_USER_NAME_REQUIRED );
        this.name = name;
    }

    public void setBirthdate( final LocalDate birthdate ){
        if( Objects.isNull( birthdate )) throw new ValidationException( CoreMessageKey.ERROR_VALIDATION_USER_BIRTHDATE_REQUIRED );
        this.birthdate = birthdate;
    }

    public void setPassword( final EncodedPassword password ){
        if( Objects.isNull( password )) throw new ValidationException( CoreMessageKey.ERROR_VALIDATION_USER_PASSWORD_REQUIRED );
        this.password = password;
    }

//--------------------------------------------------------------------------------------------------------------------\\
//---| GETTERS |------------------------------------------------------------------------------------------------------\\
//--------------------------------------------------------------------------------------------------------------------\\

    public Optional<MediaId> getImage() {
        return Optional.ofNullable( this.image );
    }

    public Optional<UserSurname> getSurname() {
        return Optional.ofNullable( this.surname );
    }

    public Integer getAge(){
        return this.birthdate.until( LocalDate.now() ).getYears();
    }

//--------------------------------------------------------------------------------------------------------------------\\
//---| CHECKERS |-----------------------------------------------------------------------------------------------------\\
//--------------------------------------------------------------------------------------------------------------------\\

    public boolean hasRole( final UserRole role ){
        return Objects.equals( this.role, role );
    }

//--------------------------------------------------------------------------------------------------------------------\\
//---| UPDATER |------------------------------------------------------------------------------------------------------\\
//--------------------------------------------------------------------------------------------------------------------\\

    public UserEditor update(){
        return new UserEditor( this );
    }

//--------------------------------------------------------------------------------------------------------------------\\
//---| CREATOR |------------------------------------------------------------------------------------------------------\\
//--------------------------------------------------------------------------------------------------------------------\\

    public static UserFactory create( final Email email, final UserUsername username, final UserRole role,
                                      final UserName name, final LocalDate birthdate, final EncodedPassword password ){
        return new UserFactory( email, username, role, name, birthdate, password );
    }

//--------------------------------------------------------------------------------------------------------------------\\

}

