package com.codenaiten.template.rest.web.rest.converter;

import com.codenaiten.template.rest.app.old.vo.account.AccountId;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

/**
 * Convierte un {@link UUID} a un objeto de tipo {@link AccountId}.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class AccountIdConverter implements Converter<UUID, AccountId>{

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| IMPLEMENTED METHODS |---------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    @Override
    public AccountId convert( final UUID source ){
        return Optional.ofNullable( source ).map( AccountId::new ).orElse( null );
    }

// ------------------------------------------------------------------------------------------------------------------ \\

}
