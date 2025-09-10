package com.codenaiten.template.rest.core.feature.account.dto.output;

import com.codenaiten.template.rest.core.feature.account.vo.AccountId;
import com.codenaiten.template.rest.core.feature.account.vo.Language;
import com.codenaiten.template.rest.core.feature.user.dto.output.UserPrivateInfo;
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
