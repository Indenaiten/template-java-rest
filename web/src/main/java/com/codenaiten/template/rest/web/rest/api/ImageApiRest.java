package com.codenaiten.template.rest.web.rest.api;

import com.codenaiten.template.rest.app.old.vo.image.ImageId;
import com.codenaiten.template.rest.web.rest.dto.ApiRestResponse;
import com.codenaiten.template.rest.web.rest.dto.response.ImageInfoResponse;
import com.codenaiten.template.rest.web.rest.dto.stub.ApiRestResponseWithUserInfoResponse;
import com.codenaiten.template.rest.web.rest.exception.openapi.AuthenticationErrors;
import com.codenaiten.template.rest.web.rest.exception.openapi.CommonErrors;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

@CommonErrors
@Tag( name = "Imágenes" )
@RequestMapping( "/api/image" )
public interface ImageApiRest {

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| VIEW |------------------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    @Operation( summary = "Visualiza una imagen del sistema",
                description = "Permite visualizar una imagen del sistema por su identificador",
                operationId = "view",
                responses = @ApiResponse( responseCode = "200",
                                          description = "Imagen recuperada correctamente"
                )
    )
    @AuthenticationErrors
    @PostMapping( value = "/view/{id}" )
    ResponseEntity<byte[]> view(
            @Parameter( description = "Identificador de la imagen", required = true )
            @PathVariable ImageId id
    );

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| UPLOAD |----------------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    @Operation( summary = "Sube una imagen al sistema",
                description = "Permite subir una imagen al sistema",
                operationId = "upload",
                responses = @ApiResponse( responseCode = "200",
                                          description = "Imagen subida correctamente",
                                          content = @Content( mediaType = MediaType.APPLICATION_JSON_VALUE,
                                                              schema = @Schema( implementation = ApiRestResponseWithUserInfoResponse.class )
                                          )
                )
    )
    @AuthenticationErrors
    @PostMapping( value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE )
    ResponseEntity<ApiRestResponse<ImageInfoResponse>> upload(
            @Parameter( description = "Imagen a subir", required = true )
            @RequestPart( value = "file" ) MultipartFile file
    );

// ------------------------------------------------------------------------------------------------------------------ \\

}
