package com.codenaiten.template.rest.core.feature.user.dto.output;

import com.codenaiten.template.rest.core.feature.media.vo.MediaId;
import com.codenaiten.template.rest.core.feature.user.vo.UserId;
import com.codenaiten.template.rest.core.feature.user.vo.UserName;
import com.codenaiten.template.rest.core.feature.user.vo.UserSurname;
import com.codenaiten.template.rest.core.feature.user.vo.UserUsername;
import com.codenaiten.template.rest.core.shared.vo.Timestamp;

public record UserPublicInfo(
        UserId id,
        MediaId image,
        UserUsername username,
        UserName name,
        UserSurname surname,
        Timestamp createdAt
){

// ------------------------------------------------------------------------------------------------------------------ \\

}