package com.codenaiten.template.rest.core.feature.account.service;

import com.codenaiten.template.rest.core.feature.account.Account;
import com.codenaiten.template.rest.core.feature.account.AccountEditor;
import com.codenaiten.template.rest.core.feature.account.dto.input.UpdateAccountInput;
import com.codenaiten.template.rest.core.feature.account.exception.AccountNotFoundException;
import com.codenaiten.template.rest.core.feature.account.port.AccountRepository;
import com.codenaiten.template.rest.core.feature.account.vo.AccountId;
import com.codenaiten.template.rest.core.shared.spi.LanguageProvider;
import com.codenaiten.template.rest.core.shared.spi.LanguageResolver;
import com.codenaiten.template.rest.core.shared.vo.Language;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class UpdateAccountService {

    // Ports
    private final AccountRepository accountRepository;
    private final LanguageProvider languageProvider;
    private final LanguageResolver languageResolver;

//--------------------------------------------------------------------------------------------------------------------\\
//---| METHODS |------------------------------------------------------------------------------------------------------\\
//--------------------------------------------------------------------------------------------------------------------\\

    public Account update( final AccountId id, final UpdateAccountInput input ){
        // Step 01: Get Account to Update
        final Account account = this.accountRepository.find( id ).orElseThrow( () -> new AccountNotFoundException( id ));

        // Step 02: Get Default Account Data
        final Language defaultLang = this.languageProvider.getDefaultLanguage();

        // Step 03: Update Account with Editor
        final AccountEditor editor = account.update();
        editor.language( input.getLanguage().orElse( defaultLang ));

        // Step 04: Check if has changes
        if( editor.hasChanges() ){ // If has changes, apply changes
            editor.apply();

            // Save changes
            this.accountRepository.save( account );
            this.languageResolver.setLanguage( account.getLang() );
        }

        // Step 05: Return updated Account
        return account;
    }

//--------------------------------------------------------------------------------------------------------------------\\

}
