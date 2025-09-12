package com.codenaiten.template.rest.core.feature.account.dto.input;

import com.codenaiten.template.rest.core.shared.vo.Language;
import lombok.Builder;
import lombok.Setter;

import java.util.Optional;

@Setter
@Builder
public class UpdateAccountInput {

    private Language language;

//--------------------------------------------------------------------------------------------------------------------\\
//---| GETTERS |------------------------------------------------------------------------------------------------------\\
//--------------------------------------------------------------------------------------------------------------------\\

    public Optional<Language> getLanguage(){
        return Optional.ofNullable( this.language );
    }

//--------------------------------------------------------------------------------------------------------------------\\

}