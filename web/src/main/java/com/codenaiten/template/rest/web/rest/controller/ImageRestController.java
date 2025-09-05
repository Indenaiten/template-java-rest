package com.codenaiten.template.rest.web.rest.controller;

import com.codenaiten.template.rest.app.api.ImageService;
import com.codenaiten.template.rest.app.dto.command.account.CreateImageCommand;
import com.codenaiten.template.rest.app.dto.result.ImageContentResult;
import com.codenaiten.template.rest.app.dto.result.ImageInfoResult;
import com.codenaiten.template.rest.app.i18n.MessageI18nManager;
import com.codenaiten.template.rest.app.vo.image.ImageId;
import com.codenaiten.template.rest.web.rest.RestMessage;
import com.codenaiten.template.rest.web.rest.api.ImageApiRest;
import com.codenaiten.template.rest.web.rest.dto.ApiRestResponse;
import com.codenaiten.template.rest.web.rest.dto.response.ImageInfoResponse;
import com.codenaiten.template.rest.web.rest.mapper.CommandMapper;
import com.codenaiten.template.rest.web.rest.mapper.ResponseMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.logging.LogLevel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@AllArgsConstructor
public class ImageRestController implements ImageApiRest {

    /** Manager de mensajes internacionalizados del sistema */
    private final MessageI18nManager messageI18nManager;

    /** Service de imágenes del sistema */
    private final ImageService imageService;

    /** Mapper de objetos relacionados con los DTO de Request/Command */
    private final CommandMapper commandMapper;

    /** Mapper de objetos relacionados con los DTO de Result/Response */
    private final ResponseMapper responseMapper;

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| IMPLEMENTED METHODS |---------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    @Override
    public ResponseEntity<byte[]> view( final ImageId id ){
        // Step 01: Run use case
        final ImageContentResult result = this.imageService.getContent( id );

        // Step 02: Return response
        final ImageInfoResult info = result.info();
        return ResponseEntity.status( HttpStatus.OK )
                .contentType( MediaType.parseMediaType( info.contentType() ))
                .contentLength( info.size() )
                .body( result.bytes() );
    }

// ------------------------------------------------------------------------------------------------------------------ \\

    @Override
    public ResponseEntity<ApiRestResponse<ImageInfoResponse>> upload( final MultipartFile file ){
        // Step 01: Create command
        final CreateImageCommand command = this.commandMapper.toCommand( file );

        // Step 02: Run use case
        final ImageInfoResult result = this.imageService.create( command );

        // Step 03: Convert result to response
        final ImageInfoResponse response = this.responseMapper.toResponse( result );

        // Step 04: Get i18n success message
        final String message = this.messageI18nManager.getMessageAndLogger( RestMessage.SUCCESS_IMAGE_CREATE, LogLevel.INFO, result.id() );

        // Step 05: Build body with wrapper response
        final ApiRestResponse<ImageInfoResponse> wrapper = ApiRestResponse.success().message( message ).build( response );

        // Step 06: Return response
        return ResponseEntity.status( HttpStatus.OK ).body( wrapper );
    }

// ------------------------------------------------------------------------------------------------------------------ \\

}
