package com.codenaiten.template.rest.app.dto.command.account;

import com.codenaiten.template.rest.app.vo.image.ImageContentType;

public record CreateImageCommand(
        byte[] image,
        ImageContentType contentType,
        Long contentSize
){

// ------------------------------------------------------------------------------------------------------------------ \\

}
