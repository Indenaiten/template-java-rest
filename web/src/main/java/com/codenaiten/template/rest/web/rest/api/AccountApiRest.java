package com.codenaiten.template.rest.web.rest.api;

import com.codenaiten.template.rest.app.vo.account.AccountId;
import com.codenaiten.template.rest.web.rest.dto.ApiRestResponse;
import com.codenaiten.template.rest.web.rest.dto.request.*;
import com.codenaiten.template.rest.web.rest.dto.response.AccountInfoResponse;
import com.codenaiten.template.rest.web.rest.dto.response.AccountRoleInfoResponse;
import com.codenaiten.template.rest.web.rest.dto.response.PageResponse;
import com.codenaiten.template.rest.web.rest.dto.stub.ApiRestResponseWithAccountInfoPageResponse;
import com.codenaiten.template.rest.web.rest.dto.stub.ApiRestResponseWithAccountInfoResponse;
import com.codenaiten.template.rest.web.rest.dto.stub.ApiRestResponseWithAccountRoleInfoListResponse;
import com.codenaiten.template.rest.web.rest.exception.openapi.PrivateErrors;
import com.codenaiten.template.rest.web.rest.exception.openapi.PublicErrors;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;

@Tag( name = "Cuentas" )
@RequestMapping( "/api/account" )
@PublicErrors
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
    @PrivateErrors
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
    @PrivateErrors
    @GetMapping("/{id}")
    ResponseEntity<ApiRestResponse<AccountInfoResponse>> get(
            @Parameter(description = "ID único de la cuenta", required = true)
            @PathVariable AccountId id
    );

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| GET ALL ACCOUNTS |------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    @Operation( operationId = "getAll",
                summary = "Recupera todas las cuentas con paginación",
                description = "Permite recuperar todas las cuentas del sistema con paginación"
    )
    @ApiResponse( responseCode = "200",
                  description = "Lista de cuentas recuperada correctamente",
                  content = @Content( mediaType = MediaType.APPLICATION_JSON_VALUE,
                                      schema = @Schema( implementation = ApiRestResponseWithAccountInfoPageResponse.class )
                  )
    )
    @PrivateErrors
    @GetMapping({ "/all", "/all/{page}", "/all/{page}/{size}" })
    ResponseEntity<ApiRestResponse<PageResponse<AccountInfoResponse>>> getAll(
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
    @PrivateErrors
    @GetMapping({ "/search/{search}", "/search/{search}/{page}", "/search/{search}/{page}/{size}" })
    ResponseEntity<ApiRestResponse<PageResponse<AccountInfoResponse>>> search(
            @Parameter( description = "Término de búsqueda", required = true )
            @PathVariable String search,

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
    @PrivateErrors
    @PostMapping
    ResponseEntity<ApiRestResponse<AccountInfoResponse>> create(
            @Parameter( description = "Datos para crear la cuenta", required = true )
            @RequestBody AccountCreateRequest request
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
    @PrivateErrors
    @PutMapping( "/{id}" )
    ResponseEntity<ApiRestResponse<AccountInfoResponse>> update(
            @Parameter( description = "ID único de la cuenta", required = true )
            @PathVariable AccountId id,

            @Parameter( description = "Datos para actualizar la cuenta", required = true )
            @RequestBody AccountUpdateRequest request
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
    @PrivateErrors
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
    @PrivateErrors
    @PatchMapping( "/me/email" )
    ResponseEntity<ApiRestResponse<AccountInfoResponse>> updateEmail(
            @Parameter( description = "Datos para actualizar el email", required = true )
            @RequestBody AccountEmailUpdateRequest request
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
    @PrivateErrors
    @PatchMapping( "/me/password" )
    ResponseEntity<ApiRestResponse<AccountInfoResponse>> updatePassword(
            @Parameter( description = "Datos para actualizar la contraseña", required = true )
            @RequestBody AccountPasswordUpdateRequest request
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
    @PrivateErrors
    @DeleteMapping( "/{id}" )
    ResponseEntity<ApiRestResponse<AccountInfoResponse>> delete(
            @Parameter( description = "ID único de la cuenta", required = true )
            @PathVariable AccountId id
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
    @PrivateErrors
    @DeleteMapping( "/me" )
    ResponseEntity<ApiRestResponse<AccountInfoResponse>> delete(
            @Parameter( description = "Datos para confirmar la eliminación de la cuenta", required = true )
            @RequestBody AccountDeleteRequest request
    );

// ------------------------------------------------------------------------------------------------------------------ \\

}
