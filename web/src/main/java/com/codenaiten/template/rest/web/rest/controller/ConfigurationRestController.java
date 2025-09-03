package com.codenaiten.template.rest.web.rest.controller;

import com.codenaiten.template.rest.app.api.ConfigurationService;
import com.codenaiten.template.rest.app.i18n.MessageI18nManager;
import com.codenaiten.template.rest.web.rest.RestMessage;
import com.codenaiten.template.rest.web.rest.api.ConfigurationApiRest;
import com.codenaiten.template.rest.web.rest.dto.ApiRestResponse;
import com.codenaiten.template.rest.web.rest.dto.Empty;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.logging.LogLevel;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.LocaleResolver;

import java.util.List;
import java.util.Locale;

@Slf4j
@RestController
@AllArgsConstructor
public class ConfigurationRestController implements ConfigurationApiRest {

    /** Manager de mensajes internacionalizados del sistema */
    private final MessageI18nManager messageI18nManager;

    /** Service de configuraciones del sistema */
    private final ConfigurationService configurationService;

    /** Resolver de idioma para la respuesta HTTP */
    private final LocaleResolver localeResolver;

    /** Petición HTTP actual */
    private final HttpServletRequest httpServletRequest;

    /** Respuesta HTTP actual */
    private final HttpServletResponse httpServletResponse;

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| IMPLEMENTED METHODS |---------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    @Override
    public ResponseEntity<ApiRestResponse<Empty>> updateLang( final Locale lang ){
        // Step 01: Run use case
        this.configurationService.lang( lang );

        // Step 02: Get i18n success message
        final String message = this.messageI18nManager.getMessageAndLogger( RestMessage.SUCCESS_CONFIG_LANG, LogLevel.INFO, lang );

        // Step 03: Build body with wrapper response
        final ApiRestResponse<Empty> wrapper = ApiRestResponse.success().message( message ).build();

        // Step 04: Resolve locale with current locale
        this.localeResolver.setLocale( this.httpServletRequest, this.httpServletResponse, LocaleContextHolder.getLocale() );

        // Step 05: Return response
        return ResponseEntity.status( HttpStatus.OK ).body( wrapper );
    }

// ------------------------------------------------------------------------------------------------------------------ \\

    @Override
    public ResponseEntity<ApiRestResponse<String>> getLang(){
        // Step 01: Run use case
        final Locale result = this.configurationService.getLang();

        // Step 02: Get i18n success message
        final String message = this.messageI18nManager.getMessageAndLogger( RestMessage.SUCCESS_CONFIG_RETRIEVE_LANG_CONFIGURED, LogLevel.INFO );

        // Step 03: Build body with wrapper response
        final ApiRestResponse<String> wrapper = ApiRestResponse.success().message( message ).build( result.toLanguageTag() );

        // Step 04: Return response
        return ResponseEntity.status( HttpStatus.OK ).body( wrapper );
    }

// ------------------------------------------------------------------------------------------------------------------ \\

    @Override
    public ResponseEntity<ApiRestResponse<List<String>>> getSupportedLanguages(){
        // Step 01: Run use case
        final List<Locale> result = this.configurationService.getSupportedLanguages();

        // Step 02: Convert result to raw data
        final List<String> data = result.stream().map( Locale::toLanguageTag ).toList();

        // Step 03: Get i18n success message
        final String message = this.messageI18nManager.getMessageAndLogger( RestMessage.SUCCESS_CONFIG_LIST_LANG_SUPPORTED, LogLevel.INFO );

        // Step 04: Build body with wrapper response
        final ApiRestResponse<List<String>> wrapper = ApiRestResponse.success().message( message ).build( data );

        // Step 05: Return response
        return ResponseEntity.status( HttpStatus.OK ).body( wrapper );
    }

// ------------------------------------------------------------------------------------------------------------------ \\

}
