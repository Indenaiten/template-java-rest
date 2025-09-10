package com.codenaiten.template.rest.core.feature.user.dto.input;

import com.codenaiten.template.rest.core.feature.account.vo.Language;
import com.codenaiten.template.rest.core.feature.media.vo.MediaId;
import com.codenaiten.template.rest.core.feature.user.vo.UserName;
import com.codenaiten.template.rest.core.feature.user.vo.UserRole;
import com.codenaiten.template.rest.core.feature.user.vo.UserSurname;
import com.codenaiten.template.rest.core.feature.user.vo.UserUsername;
import com.codenaiten.template.rest.core.shared.vo.Email;
import com.codenaiten.template.rest.core.shared.vo.Password;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Optional;

@Getter
@Setter
@Builder
public class UpdateUser {

    private MediaId image;
    private Email email;
    private UserUsername username;
    private UserRole role;
    private UserName name;
    private UserSurname surname;
    private LocalDate birthdate;
    private Password password;
    private Language language;

//--------------------------------------------------------------------------------------------------------------------\\
//---| GETTERS |------------------------------------------------------------------------------------------------------\\
//--------------------------------------------------------------------------------------------------------------------\\

    public Optional<MediaId> getImage(){
        return Optional.ofNullable( this.image );
    }

    public Optional<Email> getEmail() {
        return Optional.ofNullable( this.email );
    }

    public Optional<UserUsername> getUsername() {
        return Optional.ofNullable( this.username );
    }

    public Optional<UserRole> getRole(){
        return Optional.ofNullable( this.role );
    }

    public Optional<UserName> getName() {
        return Optional.ofNullable( this.name );
    }

    public Optional<UserSurname> getSurname(){
        return Optional.ofNullable( this.surname );
    }

    public Optional<LocalDate> getBirthdate() {
        return Optional.ofNullable( this.birthdate );
    }

    public Optional<Password> getPassword() {
        return Optional.ofNullable( this.password );
    }

    public Optional<Language> getLanguage(){
        return Optional.ofNullable( this.language );
    }

//--------------------------------------------------------------------------------------------------------------------\\

}