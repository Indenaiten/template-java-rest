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

    private final ConfigurationService configurationService;
    private final MessageI18nManager messageI18nManager;
    private final LocaleResolver localeResolver;
    private final HttpServletRequest httpServletRequest;
    private final HttpServletResponse httpServletResponse;

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| IMPLEMENTED METHODS |---------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    @Override
    public ResponseEntity<ApiRestResponse<Empty>> configLang( final Locale lang ){
        this.configurationService.lang( lang );
        final String message = this.messageI18nManager.getMessageAndLogger( RestMessage.SUCCESS_CONFIG_LANG, LogLevel.INFO, lang );
        final ApiRestResponse<Empty> wrapper = ApiRestResponse.success().message( message ).build();
        this.localeResolver.setLocale( this.httpServletRequest, this.httpServletResponse, LocaleContextHolder.getLocale() );
        return ResponseEntity.status( HttpStatus.OK ).body(wrapper);
    }

    @Override
    public ResponseEntity<ApiRestResponse<String>> getLang(){
        final Locale result = this.configurationService.getLang();
        final String message = this.messageI18nManager.getMessageAndLogger( RestMessage.SUCCESS_CONFIG_RETRIEVE_LANG_CONFIGURED, LogLevel.INFO );
        final ApiRestResponse<String> wrapper = ApiRestResponse.success().message( message ).data( result.toLanguageTag() );
        return ResponseEntity.status( HttpStatus.OK ).body( wrapper );
    }

    @Override
    public ResponseEntity<ApiRestResponse<List<String>>> getSupportedLanguages(){
        final List<Locale> result = this.configurationService.getSupportedLanguages();
        final List<String> data = result.stream().map( Locale::toLanguageTag ).toList();
        final String message = this.messageI18nManager.getMessageAndLogger( RestMessage.SUCCESS_CONFIG_LIST_LANG_SUPPORTED, LogLevel.INFO );
        final ApiRestResponse<List<String>> wrapper = ApiRestResponse.success().message( message ).data( data );
        return ResponseEntity.status( HttpStatus.OK ).body( wrapper );
    }

// ------------------------------------------------------------------------------------------------------------------ \\

}
