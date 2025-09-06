package com.codenaiten.template.rest.app.old.dto.command.account;

import com.codenaiten.template.rest.app.old.vo.image.ImageContentType;

public record CreateImageCommand(
        byte[] image,
        ImageContentType contentType,
        Long contentSize
){

// ------------------------------------------------------------------------------------------------------------------ \\

}
