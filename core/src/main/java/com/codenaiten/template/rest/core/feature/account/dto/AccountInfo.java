package com.codenaiten.template.rest.core.feature.account.dto;

import com.codenaiten.template.rest.core.feature.account.vo.AccountId;
import com.codenaiten.template.rest.core.feature.account.vo.Language;
import com.codenaiten.template.rest.core.feature.user.dto.UserPrivateInfo;
import com.codenaiten.template.rest.core.shared.vo.Timestamp;

public record AccountInfo(
        AccountId id,
        UserPrivateInfo owner,
        Language lang,
        Timestamp createdAt,
        Timestamp updatedAt
){

// ------------------------------------------------------------------------------------------------------------------ \\

}
