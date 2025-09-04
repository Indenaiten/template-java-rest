package com.codenaiten.template.rest.web.rest.converter;

import com.codenaiten.template.rest.app.mapper.ValueObjectMapper;
import com.codenaiten.template.rest.app.vo.account.AccountId;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * Convierte un {@link UUID} a un objeto de tipo {@link AccountId}.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class AccountIdConverter implements Converter<UUID, AccountId>{

    /** Mapper de objetos relacionados con los {@link ValueObjectMapper} */
    private final ValueObjectMapper valueObjectMapper;

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| IMPLEMENTED METHODS |---------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    @Override
    public AccountId convert( final UUID source ){
        return this.valueObjectMapper.toAccountId( source );
    }

// ------------------------------------------------------------------------------------------------------------------ \\

}
