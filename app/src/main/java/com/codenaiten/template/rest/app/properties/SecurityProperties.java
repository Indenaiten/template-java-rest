package com.codenaiten.template.rest.app.properties;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * Properties que contiene las propiedades relacionadas con la seguridad del sistema.
 *
 * @see Properties
 */
@Slf4j
@Getter
@Component
public class SecurityProperties extends Properties {

    /** {@link String} que contiene las rutas a ignorar por la seguridad separadas por comas para todos los métodos HTTP */
    @Value( "${app.security.ignore-paths.all:}" )
    private String ignorePaths;

    /** {@link String} que contiene las rutas a ignorar por la seguridad separadas por comas para el método HTTP GET */
    @Value( "${app.security.ignore-paths.get:}" )
    private String ignorePathsGet;

    /** {@link String} que contiene las rutas a ignorar por la seguridad separadas por comas para el método HTTP POST */
    @Value( "${app.security.ignore-paths.post:}" )
    private String ignorePathsPost;

    /** {@link String} que contiene las rutas a ignorar por la seguridad separadas por comas para el método HTTP PUT */
    @Value( "${app.security.ignore-paths.put:}" )
    private String ignorePathsPut;

    /** {@link String} que contiene las rutas a ignorar por la seguridad separadas por comas para el método HTTP PATCH */
    @Value( "${app.security.ignore-paths.patch:}" )
    private String ignorePathsPatch;

    /** {@link String} que contiene las rutas a ignorar por la seguridad separadas por comas para el método HTTP DELETE */
    @Value( "${app.security.ignore-paths.delete:}" )
    private String ignorePathsDelete;

    /** {@link String} que contiene las origenes de las peticiones CORS separadas por comas */
    @Value( "${app.security.cors.allowed-origins:}" )
    private String corsAllowedOrigins;

    /** {@link String} que contiene los métodos HTTP permitidos en las peticiones CORS separadas por comas */
    @Value( "${app.security.cors.allowed-methods:}" )
    private String corsAllowedMethods;

    /** {@link String} que contiene los encabezados HTTP permitidos en las peticiones CORS separados por comas */
    @Value( "${app.security.cors.allowed-headers:}" )
    private String corsAllowedHeaders;

    /** {@link String} que contiene los encabezados HTTP expuestos en las peticiones CORS separados por comas */
    @Value( "${app.security.cors.exposed-headers:}" )
    private String corsExposedHeaders;

    /** {@link Boolean} que indica si se permiten las credenciales en las peticiones CORS */
    @Value( "${app.security.cors.allow-credentials:}" )
    private Boolean corsAllowCredentials;

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| GETTERS |---------------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    /**
     * Obtiene las rutas a ignorar por la seguridad para todos los métodos HTTP.
     *
     * @return {@link List} de {@link String} con las rutas a ignorar por la seguridad para todos los métodos HTTP.
     */
    public List<String> getIgnorePaths() {
        return this.convertToList( this.ignorePaths );
    }

    /**
     * Obtiene las rutas a ignorar por la seguridad para el método HTTP GET.
     *
     * @return {@link List} de {@link String} con las rutas a ignorar por la seguridad para el método HTTP GET.
     */
    public List<String> getIgnorePathsGet() {
        return this.convertToList( this.ignorePathsGet );
    }

    /**
     * Obtiene las rutas a ignorar por la seguridad para el método HTTP POST.
     *
     * @return {@link List} de {@link String} con las rutas a ignorar por la seguridad para el método HTTP POST.
     */
    public List<String> getIgnorePathsPost() {
        return this.convertToList( this.ignorePathsPost );
    }

    /**
     * Obtiene las rutas a ignorar por la seguridad para el método HTTP PUT.
     *
     * @return {@link List} de {@link String} con las rutas a ignorar por la seguridad para el método HTTP PUT.
     */
    public List<String> getIgnorePathsPut() {
        return this.convertToList( this.ignorePathsPut );
    }

    /**
     * Obtiene las rutas a ignorar por la seguridad para el método HTTP PATCH.
     *
     * @return {@link List} de {@link String} con las rutas a ignorar por la seguridad para el método HTTP PATCH.
     */
    public List<String> getIgnorePathsPatch() {
        return this.convertToList( this.ignorePathsPatch );
    }

    /**
     * Obtiene las rutas a ignorar por la seguridad para el método HTTP DELETE.
     *
     * @return {@link List} de {@link String} con las rutas a ignorar por la seguridad para el método HTTP DELETE.
     */
    public List<String> getIgnorePathsDelete() {
        return this.convertToList( this.ignorePathsDelete );
    }

    /**
     * Obtiene las rutas permitidas de los orígenes de las peticiones CORS.
     *
     * @return {@link List} de {@link String} con las rutas permitidas de los orígenes de las peticiones CORS.
     */
    public List<String> getCorsAllowedOrigins() {
        return this.convertToList( this.corsAllowedOrigins );
    }

    /**
     * Obtiene los métodos HTTP permitidos en las peticiones CORS.
     *
     * @return {@link List} de {@link String} con los métodos HTTP permitidos en las peticiones CORS.
     */
    public List<String> getCorsAllowedMethods() {
        return this.convertToList( this.corsAllowedMethods );
    }

    /**
     * Obtiene los encabezados HTTP permitidos en las peticiones CORS.
     *
     * @return {@link List} de {@link String} con los encabezados HTTP permitidos en las peticiones CORS.
     */
    public List<String> getCorsAllowedHeaders() {
        return this.convertToList( this.corsAllowedHeaders );
    }

    /**
     * Obtiene los encabezados HTTP expuestos en las peticiones CORS.
     *
     * @return {@link List} de {@link String} con los encabezados HTTP expuestos en las peticiones CORS.
     */
    public List<String> getCorsExposedHeaders() {
        return this.convertToList( this.corsExposedHeaders );
    }

    /**
     * Obtiene si se permiten las credenciales en las peticiones CORS.
     *
     * @return {@link Optional} con el valor {@link Boolean} que indica si se permiten las credenciales en las peticiones
     * CORS.
     */
    public Optional<Boolean> getCorsAllowCredentials() {
        return this.getOptional( this.corsAllowCredentials );
    }

// ------------------------------------------------------------------------------------------------------------------ \\

}
