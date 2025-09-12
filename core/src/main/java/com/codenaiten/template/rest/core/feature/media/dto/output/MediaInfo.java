package com.codenaiten.template.rest.core.feature.media.dto.output;

import com.codenaiten.template.rest.core.feature.media.vo.MediaContentSize;
import com.codenaiten.template.rest.core.feature.media.vo.MediaContentType;
import com.codenaiten.template.rest.core.feature.media.vo.MediaId;
import com.codenaiten.template.rest.core.feature.user.vo.UserId;
import com.codenaiten.template.rest.core.shared.vo.Timestamp;

public record MediaInfo(
        MediaId id,
        UserId owner,
        MediaContentType contentType,
        MediaContentSize size,
        Timestamp createdAt,
        Timestamp updatedAt
){

// ------------------------------------------------------------------------------------------------------------------ \\

}