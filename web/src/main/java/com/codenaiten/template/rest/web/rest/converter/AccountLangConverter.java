package com.codenaiten.template.rest.web.rest.converter;

import java.util.Locale;
import java.util.Optional;

//@Slf4j
//@Component
//@RequiredArgsConstructor
public class AccountLangConverter /*implements Converter<String, Locale>*/{

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| IMPLEMENTED METHODS |---------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

//    @Override
    public Locale convert( final String source ){
        return Optional.ofNullable( source ).map( Locale::forLanguageTag ).orElse( null );
    }

// ------------------------------------------------------------------------------------------------------------------ \\

}
