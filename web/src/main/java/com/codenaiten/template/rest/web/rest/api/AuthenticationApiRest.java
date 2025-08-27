package com.codenaiten.template.rest.web.rest.api;

import com.codenaiten.template.rest.web.rest.dto.ApiRestResponse;
import com.codenaiten.template.rest.web.rest.dto.Empty;
import com.codenaiten.template.rest.web.rest.dto.request.LoginRequest;
import com.codenaiten.template.rest.web.rest.dto.request.RegisterRequest;
import com.codenaiten.template.rest.web.rest.dto.response.AccountInfoResponse;
import com.codenaiten.template.rest.web.rest.dto.response.LoginResponse;
import com.codenaiten.template.rest.web.rest.dto.stub.ApiRestResponseEmptyResponse;
import com.codenaiten.template.rest.web.rest.dto.stub.ApiRestResponseWithAccountInfoResponse;
import com.codenaiten.template.rest.web.rest.dto.stub.ApiRestResponseWithLoginResponse;
import com.codenaiten.template.rest.web.rest.exception.openapi.PrivateErrors;
import com.codenaiten.template.rest.web.rest.exception.openapi.PublicErrors;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag( name = "Autenticación" )
@RequestMapping( "/api/auth" )
@PublicErrors
public interface AuthenticationApiRest {

    String ACCESS_TOKEN_NAME = "token";
    String REFRESH_TOKEN_NAME = "refresh-token";

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| REGISTER |--------------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    @Operation( operationId = "register",
                summary = "Registrar un nuevo usuario",
                description = "Permite autenticar un usuario en el sistema"
    )
    @ApiResponse( responseCode = "201",
                  description = "Usuario registrado correctamente",
                  content = @Content( mediaType = MediaType.APPLICATION_JSON_VALUE,
                                      schema = @Schema( implementation = ApiRestResponseWithAccountInfoResponse.class )
                  )
    )
    @PostMapping( "/signup" )
    ResponseEntity<ApiRestResponse<AccountInfoResponse>> register(
            @Parameter( description = "Datos del usuario a registrar", required = true )
            @RequestBody RegisterRequest request
    );

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| LOGIN |------------------------------------------------------------------------------------------------------ \\
// ------------------------------------------------------------------------------------------------------------------ \\

    @Operation( operationId = "login",
                summary = "Autenticar un usuario",
                description = """
                        Autentica un usuario en el sistema usando credenciales (email y contraseña).
                        
                        ### Respuesta exitosa:
                        - **Header `securityToken`**: Contiene el access securityToken JWT para futuras peticiones autenticadas
                        - **Cookie `securityToken`**: Cookie httpOnly con el access securityToken JWT para futuras peticiones autenticadas
                        - **Cookie `refresh-securityToken`**: Cookie httpOnly con el refresh securityToken para renovación automática
                        - **Body**: Información básica de la cuenta y usuario
                        
                        ### Uso del access securityToken:
                        Gestionado automáticamente mediante cookies o incluye el securityToken en el header `securityToken` para futuras
                         peticiones autenticadas:
                        ```
                        GET /api/secured-endpoint
                        securityToken: eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
                        ```
                        
                        ### Renovación automática:
                        El refresh securityToken se gestiona automáticamente mediante cookies. Cuando el access securityToken expire,
                        utiliza el endpoint `/refresh` para obtener nuevos tokens.
                        """
    )
    @ApiResponse( responseCode = "200",
                  description = "Autenticación exitosa. Revisa el header 'securityToken' y la cookie 'refresh-securityToken'.",
                  content = @Content( mediaType = MediaType.APPLICATION_JSON_VALUE,
                                      schema = @Schema( implementation = ApiRestResponseWithLoginResponse.class )),
                  headers = @Header( name = ACCESS_TOKEN_NAME,
                                     description = "Access securityToken JWT para autenticación",
                                     schema = @Schema( type = "string", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c" )
                  )
    )
    @PostMapping( "/login" )
    ResponseEntity<ApiRestResponse<LoginResponse>> login(
            @Parameter( description = "Credenciales del usuario", required = true )
            @RequestBody LoginRequest request
    );

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| REFRESH TOKEN |---------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    @Operation( operationId = "refresh",
                summary = "Refrescar tokens de acceso",
                description = """
                        Genera nuevos tokens de acceso y refresh usando el securityToken de renovación almacenado en cookie.
                        
                        ### ¿Cuándo usar este endpoint?
                        - Cuando recibas error 401 (Unauthorized) en peticiones autenticadas
                        - Antes de que expire el access securityToken (proactivamente)
                        - Cuando implementes renovación automática en el frontend
                        
                        ### Requisitos:
                        - Cookie `refresh-securityToken` debe estar presente (se establece automáticamente en login)
                        - El refresh securityToken debe ser válido y no estar expirado
                        - **NO** requiere header `securityToken` (el access securityToken puede estar expirado)
                        
                        ### Respuesta exitosa:
                        - **Header `securityToken`**: Contiene el access securityToken JWT para futuras peticiones autenticadas
                        - **Cookie `securityToken`**: Cookie httpOnly con el access securityToken JWT para futuras peticiones autenticadas
                        - **Cookie `refresh-securityToken`**: Cookie httpOnly con el refresh securityToken para renovación automática
                        - **Body**: Información básica de la cuenta y usuario
                        
                        ### Flujo típico:
                        1. Cliente realiza petición autenticada con access securityToken expirado
                        2. Servidor responde con 401 Unauthorized
                        3. Cliente llama automáticamente a `/refresh`
                        4. Servidor devuelve nuevos tokens
                        5. Cliente reintenta la petición original con el nuevo access securityToken
                        
                        ### Casos de error:
                        - **401**: Refresh securityToken inválido, expirado o no presente
                        - **403**: Cuenta deshabilitada o tokens revocados
                        
                        ### Configuración para testing en Swagger UI:
                        1. Primero realiza login para obtener la cookie `refresh-securityToken`
                        2. La cookie se establece automáticamente en el navegador
                        3. Puedes llamar a refresh sin headers adicionales
                        4. El nuevo access securityToken aparecerá en el header de respuesta
                        """
    )
    @ApiResponse( responseCode = "200",
                  description = "Tokens renovados correctamente. Nuevo access securityToken en header 'securityToken' y refresh securityToken en cookie.",
                  headers = @Header( name = ACCESS_TOKEN_NAME,
                                     description = "Nuevo access securityToken JWT",
                                     required = true,
                                     schema = @Schema( implementation = ApiRestResponseWithLoginResponse.class )
                  )
    )
    @ApiResponse( responseCode = "401",
                  description = "Token inválido o ausente, se requiere autenticación",
                  content = @Content( mediaType = MediaType.APPLICATION_JSON_VALUE,
                                      schema = @Schema( implementation = ApiRestResponseEmptyResponse.class )
                  )
    )
    @PostMapping( "/refresh" )
    ResponseEntity<ApiRestResponse<LoginResponse>> refresh(
            @Parameter( description = "El refresh securityToken para actualizar los tokens de acceso", required = true, hidden = true )
            @CookieValue( name = REFRESH_TOKEN_NAME ) String securityToken
    );

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| LOGOUT |----------------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    @Operation( operationId = "logout",
                summary = "Cerrar sesión",
                description = """
                        Invalida la sesión del usuario autenticado y limpia todas las cookies relacionadas.
                        
                        ### Acciones realizadas:
                        - Invalida todos los tokens de la cuenta en el servidor
                        - Limpia la cookie `refresh-securityToken` del navegador
                        - El access securityToken actual queda invalidado inmediatamente
                        
                        ### Requisitos:
                        - Debe estar autenticado (incluir header `securityToken`)
                        
                        ### Después del logout:
                        - Todas las futuras peticiones con el securityToken actual fallarán
                        - Se debe realizar login nuevamente para acceder a recursos protegidos
                        """
    )
    @ApiResponse( responseCode = "200",
                  description = "Sesión cerrada correctamente.",
                  content = @Content( mediaType = MediaType.APPLICATION_JSON_VALUE,
                                      schema = @Schema( implementation = ApiRestResponseEmptyResponse.class ))
    )
    @PrivateErrors
    @PostMapping( "/logout" )
    ResponseEntity<ApiRestResponse<Empty>> logout();

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| INVALIDATE |------------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    @Operation( operationId = "invalidate",
                summary = "Invalidar todos los tokens",
                description = """
                    Invalida **todos los tokens de acceso y de renovación** asociados al usuario autenticado.
    
                    ### Acciones realizadas:
                    - Todos los `access tokens` emitidos quedan revocados inmediatamente.
                    - Todos los `refresh tokens` emitidos quedan invalidados y eliminados.
                    - Obliga a que el usuario realice login nuevamente para generar nuevos tokens.
    
                    ### Cuándo usarlo:
                    - Cierre de sesión forzado desde otro dispositivo.
                    - Revocación masiva por razones de seguridad.
                    - Cuando un usuario sospecha que sus credenciales han sido comprometidas.
                    """
    )
    @ApiResponse( responseCode = "200",
                  description = "Tokens invalidados correctamente",
                  content = @Content( mediaType = MediaType.APPLICATION_JSON_VALUE,
                                      schema = @Schema( implementation = ApiRestResponseEmptyResponse.class ))
    )
    @PrivateErrors
    @PostMapping( "/invalidate" )
    ResponseEntity<ApiRestResponse<Empty>> invalidate();

// ------------------------------------------------------------------------------------------------------------------ \\

}
