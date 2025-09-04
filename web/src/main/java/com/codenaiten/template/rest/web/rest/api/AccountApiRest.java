package com.codenaiten.template.rest.web.rest.api;

import com.codenaiten.template.rest.app.vo.account.AccountId;
import com.codenaiten.template.rest.web.rest.dto.ApiRestResponse;
import com.codenaiten.template.rest.web.rest.dto.request.account.*;
import com.codenaiten.template.rest.web.rest.dto.response.AccountInfoResponse;
import com.codenaiten.template.rest.web.rest.dto.response.AccountRoleInfoResponse;
import com.codenaiten.template.rest.web.rest.dto.stub.ApiRestResponseWithAccountInfoPageResponse;
import com.codenaiten.template.rest.web.rest.dto.stub.ApiRestResponseWithAccountInfoResponse;
import com.codenaiten.template.rest.web.rest.dto.stub.ApiRestResponseWithAccountRoleInfoListResponse;
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
import java.util.Locale;

@CommonErrors
@Tag( name = "Cuentas" )
@RequestMapping( "/api/account" )
public interface AccountApiRest {

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| GET MY ACCOUNT INFO |---------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    @Operation( operationId = "me",
                summary = "Recupera la información de la cuenta del usuario autenticado",
                description = "Permite recuperar la información de la cuenta del usuario autenticado"
    )
    @ApiResponse( responseCode = "200",
                  description = "Datos recuperados correctamente",
                  content = @Content( mediaType = MediaType.APPLICATION_JSON_VALUE,
                                      schema = @Schema( implementation = ApiRestResponseWithAccountInfoResponse.class )
                  )
    )
    @AuthenticationErrors
    @GetMapping( "/me" )
    ResponseEntity<ApiRestResponse<AccountInfoResponse>> me();

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| GET ACCOUNT INFO BY ID |------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    @Operation( operationId = "get",
                summary = "Recupera la información de una cuenta específica",
                description = "Permite recuperar la información de una cuenta específica por su ID"
    )
    @ApiResponse( responseCode = "200",
                  description = "Cuenta encontrada correctamente",
                  content = @Content( mediaType = MediaType.APPLICATION_JSON_VALUE,
                                      schema = @Schema( implementation = ApiRestResponseWithAccountInfoResponse.class )
                  )
    )
    @AuthenticationErrors
    @GetMapping("/{id}")
    ResponseEntity<ApiRestResponse<AccountInfoResponse>> get(
            @Parameter(description = "ID único de la cuenta", required = true)
            @PathVariable AccountId id
    );

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| SEARCH ACCOUNTS |-------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    @Operation( operationId = "search",
                summary = "Busca cuentas por criterios",
                description = "Permite buscar cuentas por diferentes criterios con paginación"
    )
    @ApiResponse( responseCode = "200",
                  description = "Resultados de búsqueda recuperados correctamente",
                  content = @Content( mediaType = MediaType.APPLICATION_JSON_VALUE,
                                      schema = @Schema( implementation = ApiRestResponseWithAccountInfoPageResponse.class )
                  )
    )
    @AuthenticationErrors
    @GetMapping({ "/search/all", "/search/all/{page}", "/search/all/{page}/{size}",
                  "/search/term/{term}", "/search/term/{term}/{page}", "/search/term/{term}/{page}/{size}" })
    ResponseEntity<ApiRestResponse<List<AccountInfoResponse>>> search(
            @Parameter( description = "Término de búsqueda" )
            @PathVariable( name = "term", required = false ) String search,

            @Parameter( description = "Número de página (comienza en 0)", example = "0" )
            @PathVariable( required = false ) Integer page,

            @Parameter( description = "Tamaño de la página", example = "25" )
            @PathVariable( required = false ) Integer size
    );

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| SEARCH ACCOUNTS |-------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    @Operation( operationId = "search",
                summary = "Busca cuentas por criterios",
                description = "Permite buscar cuentas por diferentes criterios con paginación"
    )
    @ApiResponse( responseCode = "200",
                  description = "Resultados de búsqueda recuperados correctamente",
                  content = @Content( mediaType = MediaType.APPLICATION_JSON_VALUE,
                                      schema = @Schema( implementation = ApiRestResponseWithAccountInfoPageResponse.class )
                  )
    )
    @AuthenticationErrors
    @PostMapping({ "/search", "/search/{page}", "/search/{page}/{size}" })
    ResponseEntity<ApiRestResponse<List<AccountInfoResponse>>> search(
            @Parameter( description = "Filtro de búsqueda" )
            @RequestBody( required = false ) FilterAccountRequest filter,

            @Parameter( description = "Número de página (comienza en 0)", example = "0" )
            @PathVariable( required = false ) Integer page,

            @Parameter( description = "Tamaño de la página", example = "25" )
            @PathVariable( required = false ) Integer size
    );

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| LIST SUPPORTED ROLES |--------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    @Operation( operationId = "getSupportedAccountRoles",
                summary = "Obtiene la lista de roles de cuenta de usuario",
                description = "Permite obtener una lista de los roles de cuenta de usuario soportados"
    )
    @ApiResponse( responseCode = "200",
                  description = "Lista de roles de cuenta de usuario soportados",
                  content = @Content( mediaType = MediaType.APPLICATION_JSON_VALUE,
                                      schema = @Schema( implementation = ApiRestResponseWithAccountRoleInfoListResponse.class )
                  )
    )
    @GetMapping( "/role/supported" )
    ResponseEntity<ApiRestResponse<List<AccountRoleInfoResponse>>> getSupportedAccountRoles();

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| CREATE ACCOUNT |--------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    @Operation( operationId = "create",
                summary = "Crea una nueva cuenta",
                description = "Permite crear una nueva cuenta de usuario en el sistema"
    )
    @ApiResponse( responseCode = "201",
                  description = "Cuenta creada correctamente",
                  content = @Content( mediaType = MediaType.APPLICATION_JSON_VALUE,
                                      schema = @Schema( implementation = ApiRestResponseWithAccountInfoResponse.class )
                  )
    )
    @AuthenticationErrors
    @PostMapping( consumes = MediaType.MULTIPART_FORM_DATA_VALUE )
    ResponseEntity<ApiRestResponse<AccountInfoResponse>> create(
            @ParameterObject
            @ModelAttribute CreateAccountRequest request,

            @Parameter( description = "Imágen de perfil del usuario" )
            @RequestPart( required = false ) MultipartFile image
    );

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| UPDATE ACCOUNT |--------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    @Operation( operationId = "update",
                summary = "Actualiza una cuenta específica",
                description = "Permite actualizar la información de una cuenta específica"
    )
    @ApiResponse( responseCode = "200",
                  description = "Cuenta actualizada correctamente",
                  content = @Content( mediaType = MediaType.APPLICATION_JSON_VALUE,
                                      schema = @Schema( implementation = ApiRestResponseWithAccountInfoResponse.class )
                  )
    )
    @AuthenticationErrors
    @PutMapping( "/{id}" )
    ResponseEntity<ApiRestResponse<AccountInfoResponse>> update(
            @Parameter( description = "ID único de la cuenta", required = true )
            @PathVariable AccountId id,

            @Parameter( description = "Datos para actualizar la cuenta", required = true )
            @RequestBody UpdateAccountRequest request
    );

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| UPDATE LANG |------------------------------------------------------------------------------------------------ \\
// ------------------------------------------------------------------------------------------------------------------ \\

    @Operation( operationId = "updateLang",
                summary = "Actualiza el lenguaje de la cuenta del usuario autenticado",
                description = "Permite al usuario autenticado actualizar el lenguaje de su cuenta"
    )
    @ApiResponse( responseCode = "200",
                  description = "Lenguaje actualizado correctamente",
                  content = @Content( mediaType = MediaType.APPLICATION_JSON_VALUE,
                                      schema = @Schema( implementation = ApiRestResponseWithAccountInfoResponse.class )
                  )
    )
    @AuthenticationErrors
    @PatchMapping( "/me/lang/{lang}" )
    ResponseEntity<ApiRestResponse<AccountInfoResponse>> updateLang(
            @Parameter( description = "Lenguaje que se va a establecer en la cuenta del usuario", required = true )
            @PathVariable Locale lang
    );

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| UPDATE EMAIL |----------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    @Operation( operationId = "updateEmail",
                summary = "Actualiza el email de la cuenta del usuario autenticado",
                description = "Permite al usuario autenticado actualizar el email de su cuenta proporcionando su contraseña actual"
    )
    @ApiResponse( responseCode = "200",
                  description = "Email actualizado correctamente",
                  content = @Content( mediaType = MediaType.APPLICATION_JSON_VALUE,
                                      schema = @Schema( implementation = ApiRestResponseWithAccountInfoResponse.class )
                  )
    )
    @AuthenticationErrors
    @PatchMapping( "/me/email" )
    ResponseEntity<ApiRestResponse<AccountInfoResponse>> updateEmail(
            @Parameter( description = "Datos para actualizar el email", required = true )
            @RequestBody UpdateAccountEmailRequest request
    );

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| UPDATE PASSWORD |-------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    @Operation( operationId = "updatePassword",
                summary = "Actualiza la contraseña de la cuenta del usuario autenticado",
                description = "Permite al usuario autenticado actualizar su contraseña proporcionando su contraseña actual"
    )
    @ApiResponse( responseCode = "200",
                  description = "Contraseña actualizada correctamente",
                  content = @Content( mediaType = MediaType.APPLICATION_JSON_VALUE,
                                      schema = @Schema( implementation = ApiRestResponseWithAccountInfoResponse.class )
                  )
    )
    @AuthenticationErrors
    @PatchMapping( "/me/password" )
    ResponseEntity<ApiRestResponse<AccountInfoResponse>> updatePassword(
            @Parameter( description = "Datos para actualizar la contraseña", required = true )
            @RequestBody UpdateAccountPasswordRequest request
    );

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| DELETE MY ACCOUNT |------------------------------------------------------------------------------------------ \\
// ------------------------------------------------------------------------------------------------------------------ \\

    @Operation( operationId = "delete",
                summary = "Elimina la cuenta del usuario autenticado",
                description = "Permite al usuario autenticado eliminar su propia cuenta proporcionando su contraseña"
    )
    @ApiResponse( responseCode = "200",
                  description = "Cuenta eliminada correctamente",
                  content = @Content( mediaType = MediaType.APPLICATION_JSON_VALUE,
                                      schema = @Schema( implementation = ApiRestResponseWithAccountInfoResponse.class )
                  )
    )
    @AuthenticationErrors
    @DeleteMapping( "/me" )
    ResponseEntity<ApiRestResponse<AccountInfoResponse>> delete(
            @Parameter( description = "Datos para confirmar la eliminación de la cuenta", required = true )
            @RequestBody DeleteAccountRequest request
    );

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| DELETE ACCOUNT BY ID |--------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    @Operation( operationId = "delete",
                summary = "Elimina una cuenta específica por ID",
                description = "Permite eliminar una cuenta específica del sistema por su ID"
    )
    @ApiResponse( responseCode = "200",
                  description = "Cuenta eliminada correctamente",
                  content = @Content( mediaType = MediaType.APPLICATION_JSON_VALUE,
                                      schema = @Schema( implementation = ApiRestResponseWithAccountInfoResponse.class )
                  )
    )
    @AuthenticationErrors
    @DeleteMapping( "/{id}" )
    ResponseEntity<ApiRestResponse<AccountInfoResponse>> delete(
            @Parameter( description = "ID único de la cuenta", required = true )
            @PathVariable AccountId id
    );

// ------------------------------------------------------------------------------------------------------------------ \\

}
