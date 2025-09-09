package com.codenaiten.template.rest.core.layer.service;

import com.codenaiten.template.rest.core.layer.entity.User;
import com.codenaiten.template.rest.core.layer.vo.Email;
import com.codenaiten.template.rest.core.layer.vo.Password;
import com.codenaiten.template.rest.core.layer.vo.media.MediaId;
import com.codenaiten.template.rest.core.layer.vo.user.UserName;
import com.codenaiten.template.rest.core.layer.vo.user.UserRole;
import com.codenaiten.template.rest.core.layer.vo.user.UserSurname;
import com.codenaiten.template.rest.core.layer.vo.user.UserUsername;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Optional;

public interface CreateUserService {

//--------------------------------------------------------------------------------------------------------------------\\

    User create(Input input );

//--------------------------------------------------------------------------------------------------------------------\\
//---| INPUT |--------------------------------------------------------------------------------------------------------\\
//--------------------------------------------------------------------------------------------------------------------\\

    @Getter
    @Setter
    @RequiredArgsConstructor
    class Input {

        private final Email email;
        private final UserUsername username;
        private final UserName name;
        private final LocalDate birthdate;
        private final Password password;
        private UserRole role;
        private MediaId image;
        private UserSurname surname;

    //----------------------------------------------------------------------------------------------------------------\\
    //---| GETTERS |--------------------------------------------------------------------------------------------------\\
    //----------------------------------------------------------------------------------------------------------------\\

        public Optional<UserRole> getRole() {
            return Optional.ofNullable( this.role );
        }

        public Optional<MediaId> getImage() {
            return Optional.ofNullable( this.image );
        }

        public Optional<UserSurname> getSurname() {
            return Optional.ofNullable( this.surname );
        }

    }

//--------------------------------------------------------------------------------------------------------------------\\

}
