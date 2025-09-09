package com.codenaiten.template.rest.core.feature.account.service;

import com.codenaiten.template.rest.core.feature.account.Account;
import com.codenaiten.template.rest.core.feature.account.vo.Language;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Slf4j
@Component
@RequiredArgsConstructor
public class UpdateAccountServiceImpl {

//--------------------------------------------------------------------------------------------------------------------\\
//---| IMPLEMENTED METHODS |------------------------------------------------------------------------------------------\\
//--------------------------------------------------------------------------------------------------------------------\\

    public void updateLang( final Account account, final Language lang ){
        if( Objects.isNull( account )) throw new IllegalArgumentException( "Account to update language is required" );
        if( !Objects.equals( account.getLang(), lang )) account.setLang( lang );
    }

//--------------------------------------------------------------------------------------------------------------------\\

}
