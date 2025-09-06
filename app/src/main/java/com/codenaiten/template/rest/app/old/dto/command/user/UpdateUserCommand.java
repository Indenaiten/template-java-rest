package com.codenaiten.template.rest.app.old.dto.command.user;

import com.codenaiten.template.rest.app.old.vo.image.ImageId;
import com.codenaiten.template.rest.app.old.vo.user.UserName;
import com.codenaiten.template.rest.app.old.vo.user.UserSurname;
import com.codenaiten.template.rest.app.old.vo.user.UserUsername;

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
