package com.codenaiten.template.rest.app.dto.command.user;

import com.codenaiten.template.rest.app.vo.image.ImageId;
import com.codenaiten.template.rest.app.vo.user.UserName;
import com.codenaiten.template.rest.app.vo.user.UserSurname;
import com.codenaiten.template.rest.app.vo.user.UserUsername;

import java.time.LocalDate;

public record UpdateUserCommand(
        ImageId image,
        UserUsername username,
        UserName name,
        UserSurname surname,
        LocalDate birthdate
){

// ------------------------------------------------------------------------------------------------------------------ \\

}
