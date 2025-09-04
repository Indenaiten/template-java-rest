package com.codenaiten.template.rest.web.rest.api;

import com.codenaiten.template.rest.app.vo.user.UserId;
import com.codenaiten.template.rest.web.rest.dto.ApiRestResponse;
import com.codenaiten.template.rest.web.rest.dto.request.user.FilterUserRequest;
import com.codenaiten.template.rest.web.rest.dto.request.user.UpdateUserRequest;
import com.codenaiten.template.rest.web.rest.dto.response.UserInfoResponse;
import com.codenaiten.template.rest.web.rest.dto.stub.ApiRestResponseWithUserInfoPageResponse;
import com.codenaiten.template.rest.web.rest.dto.stub.ApiRestResponseWithUserInfoResponse;
import com.codenaiten.template.rest.web.rest.exception.openapi.AuthenticationErrors;
import com.codenaiten.template.rest.web.rest.exception.openapi.CommonErrors;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@CommonErrors
@Tag( name = "Usuarios" )
@RequestMapping( "/api/user" )
public interface UserApiRest {

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| GET MY USER INFO |------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    @Operation( summary = "Recupera la información del usuario autenticado",
                description = "Permite recuperar la información del usuario autenticado",
                operationId = "me",
                responses = @ApiResponse( responseCode = "200",
                                          description = "Datos recuperados correctamente",
                                          content = @Content( mediaType = MediaType.APPLICATION_JSON_VALUE,
                                                              schema = @Schema( implementation = ApiRestResponseWithUserInfoResponse.class )
                                          )
                )
    )
    @AuthenticationErrors
    @GetMapping( "/me" )
    ResponseEntity<ApiRestResponse<UserInfoResponse>> me();

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| GET USER INFO BY ID |---------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    @Operation( operationId = "get",
                summary = "Recupera la información de un usuario específico",
                description = "Permite recuperar la información de un usuario específico por su ID"
    )
    @ApiResponse( responseCode = "200",
                  description = "Usuario encontrado correctamente",
                  content = @Content( mediaType = MediaType.APPLICATION_JSON_VALUE,
                                      schema = @Schema( implementation = ApiRestResponseWithUserInfoResponse.class )
                  )
    )
    @AuthenticationErrors
    @GetMapping("/{id}")
    ResponseEntity<ApiRestResponse<UserInfoResponse>> get(
            @Parameter( description = "ID único del usuario", required = true )
            @PathVariable UserId id
    );

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| SEARCH USERS |----------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    @Operation( operationId = "search",
            summary = "Busca usuarios por criterios",
            description = "Permite buscar usuarios por diferentes criterios con paginación"
    )
    @ApiResponse( responseCode = "200",
            description = "Resultados de búsqueda recuperados correctamente",
            content = @Content( mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema( implementation = ApiRestResponseWithUserInfoPageResponse.class )
            )
    )
    @AuthenticationErrors
    @GetMapping({ "/search/all", "/search/all/{page}", "/search/all/{page}/{size}",
                  "/search/term/{term}", "/search/term/{term}/{page}", "/search/term/{term}/{page}/{size}" })
    ResponseEntity<ApiRestResponse<List<UserInfoResponse>>> search(
            @Parameter( description = "Término de búsqueda", required = true )
            @PathVariable( name = "term", required = false ) String search,

            @Parameter( description = "Número de página (comienza en 0)", example = "0" )
            @PathVariable( required = false ) Integer page,

            @Parameter( description = "Tamaño de la página", example = "25" )
            @PathVariable( required = false ) Integer size
    );

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| SEARCH USERS |----------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    @Operation( operationId = "search",
                summary = "Busca usuarios por criterios",
                description = "Permite buscar usuarios por diferentes criterios con paginación"
    )
    @ApiResponse( responseCode = "200",
                  description = "Resultados de búsqueda recuperados correctamente",
                  content = @Content( mediaType = MediaType.APPLICATION_JSON_VALUE,
                                      schema = @Schema( implementation = ApiRestResponseWithUserInfoPageResponse.class )
                  )
    )
    @AuthenticationErrors
    @PostMapping({ "/search", "/search/{page}", "/search/{page}/{size}" })
    ResponseEntity<ApiRestResponse<List<UserInfoResponse>>> search(
            @Parameter( description = "Filtro de búsqueda" )
            @RequestBody( required = false ) FilterUserRequest filter,

            @Parameter( description = "Número de página (comienza en 0)", example = "0" )
            @PathVariable( required = false ) Integer page,

            @Parameter( description = "Tamaño de la página", example = "25" )
            @PathVariable( required = false ) Integer size
    );

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| UPDATE USER BY ID |------------------------------------------------------------------------------------------ \\
// ------------------------------------------------------------------------------------------------------------------ \\

    @Operation( operationId = "update",
            summary = "Actualiza un usuario específico",
            description = "Permite actualizar la información de un usuario específico por su ID"
    )
    @ApiResponse( responseCode = "200",
            description = "Usuario actualizado correctamente",
            content = @Content( mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema( implementation = ApiRestResponseWithUserInfoResponse.class )
            )
    )
    @AuthenticationErrors
    @PutMapping( value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE )
    ResponseEntity<ApiRestResponse<UserInfoResponse>> update(
            @Parameter( description = "ID único del usuario", required = true )
            @PathVariable UserId id,

            @ParameterObject
            @ModelAttribute UpdateUserRequest request,

            @Parameter( description = "Imágen de perfil del usuario" )
            @RequestPart( required = false ) MultipartFile image
    );

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| UPDATE MY USER INFO |---------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    @Operation( operationId = "update",
                summary = "Actualiza la información del usuario autenticado",
                description = "Permite al usuario autenticado actualizar su propia información"
    )
    @ApiResponse( responseCode = "200",
                  description = "Información de usuario actualizada correctamente",
                  content = @Content( mediaType = MediaType.APPLICATION_JSON_VALUE,
                                      schema = @Schema( implementation = ApiRestResponseWithUserInfoResponse.class )
                  )
    )
    @AuthenticationErrors
    @PutMapping( value = "/me", consumes = MediaType.MULTIPART_FORM_DATA_VALUE )
    ResponseEntity<ApiRestResponse<UserInfoResponse>> update(
            @ParameterObject
            @ModelAttribute UpdateUserRequest request,

            @Parameter( description = "Imágen de perfil del usuario" )
            @RequestPart( required = false ) MultipartFile image
    );

// ------------------------------------------------------------------------------------------------------------------ \\

}
