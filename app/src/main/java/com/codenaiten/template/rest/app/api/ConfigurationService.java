package com.codenaiten.template.rest.app.api;

import java.util.List;
import java.util.Locale;

public interface ConfigurationService {

    void lang( Locale lang );
    Locale getLang();
    List<Locale> getSupportedLanguages();
}
