package com.codenaiten.template.rest.core.layer.service;

import com.codenaiten.template.rest.core.layer.entity.User;
import com.codenaiten.template.rest.core.layer.vo.Email;
import com.codenaiten.template.rest.core.layer.vo.Password;
import com.codenaiten.template.rest.core.layer.vo.media.MediaId;
import com.codenaiten.template.rest.core.layer.vo.user.UserName;
import com.codenaiten.template.rest.core.layer.vo.user.UserRole;
import com.codenaiten.template.rest.core.layer.vo.user.UserSurname;
import com.codenaiten.template.rest.core.layer.vo.user.UserUsername;

import java.time.LocalDate;

public interface UpdateUserService {

//--------------------------------------------------------------------------------------------------------------------\\

    void updateEmail( User user, Email email );

//--------------------------------------------------------------------------------------------------------------------\\

    void updateUsername( User user, UserUsername username );

//--------------------------------------------------------------------------------------------------------------------\\

    void updateRole( User user, UserRole role );

//--------------------------------------------------------------------------------------------------------------------\\

    void updateImage( User user, MediaId image );

//--------------------------------------------------------------------------------------------------------------------\\

    void updateName( User user, UserName name );

//--------------------------------------------------------------------------------------------------------------------\\

    void updateSurname( User user, UserSurname surname );

//--------------------------------------------------------------------------------------------------------------------\\

    void updateBirthdate( User user, LocalDate birthdate );

//--------------------------------------------------------------------------------------------------------------------\\

    void updatePassword( User user, Password password );

//--------------------------------------------------------------------------------------------------------------------\\

}
