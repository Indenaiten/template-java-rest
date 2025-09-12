package com.codenaiten.template.rest.core.feature.user.dto.input;

import com.codenaiten.template.rest.core.feature.media.vo.MediaId;
import com.codenaiten.template.rest.core.feature.user.vo.*;
import com.codenaiten.template.rest.core.shared.vo.Email;
import com.codenaiten.template.rest.core.shared.vo.Language;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Optional;

@Getter
@Setter
@RequiredArgsConstructor
public class CreateUser {

    private final Email email;
    private final UserUsername username;
    private final UserName name;
    private final LocalDate birthdate;
    private final UserPassword userPassword;

//--------------------------------------------------------------------------------------------------------------------\\

    private UserRole role;
    private MediaId image;
    private UserSurname surname;
    private Language language;

//--------------------------------------------------------------------------------------------------------------------\\
//---| GETTERS |------------------------------------------------------------------------------------------------------\\
//--------------------------------------------------------------------------------------------------------------------\\

    public Optional<Language> getLanguage(){
        return Optional.ofNullable( this.language );
    }

    public Optional<UserSurname> getSurname(){
        return Optional.ofNullable( this.surname );
    }

    public Optional<MediaId> getImage(){
        return Optional.ofNullable( this.image );
    }

    public Optional<UserRole> getRole(){
        return Optional.ofNullable( this.role );
    }

//--------------------------------------------------------------------------------------------------------------------\\

}