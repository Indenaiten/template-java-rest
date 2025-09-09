package com.codenaiten.template.rest.core.feature.user.dto;

import com.codenaiten.template.rest.core.feature.media.dto.MediaInfo;
import com.codenaiten.template.rest.core.feature.user.vo.*;
import com.codenaiten.template.rest.core.shared.vo.Email;
import com.codenaiten.template.rest.core.shared.vo.Timestamp;

import java.time.LocalDate;

public record UserPrivateInfo(
        UserId id,
        MediaInfo image,
        Email email,
        UserUsername username,
        UserRole role,
        UserName name,
        UserSurname surname,
        LocalDate birthdate,
        Timestamp createdAt,
        Timestamp updatedAt
){

// ------------------------------------------------------------------------------------------------------------------ \\

}