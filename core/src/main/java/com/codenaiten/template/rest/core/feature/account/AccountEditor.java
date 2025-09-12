package com.codenaiten.template.rest.core.feature.account;

import com.codenaiten.template.rest.core.shared.vo.Language;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
public class AccountEditor {

    private final Account update;
    private final Account account;

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| CONSTRUCTOR |------------------------------------------------------------------------------------------------ \\
// ------------------------------------------------------------------------------------------------------------------ \\

    public AccountEditor( final Account account ){
        Objects.requireNonNull(account, "Account to update in Editor is required" );
        this.update = account.copy();
        this.account = account;
    }

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| SETTERS |---------------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    public AccountEditor language( final Language language ){
        Optional.ofNullable( language ).ifPresent( this.update::setLang );
        return this;
    }

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| CHECKERS |--------------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    public boolean hasChanges(){
        return !Objects.equals( this.account.getLang(), this.update.getLang() );
    }

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| APPLY METHOD |----------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    public boolean apply(){
        // Step 01: Check changes
        final boolean hasChanges = this.hasChanges();
        if( hasChanges ){ // If has changes, apply them
            this.account.setLang( this.update.getLang() );

            // Update UpdatedAt Timestamp
            this.account.setUpdatedAt();
        }

        // Step 02: Return true if has changes, false otherwise
        return hasChanges;
    }

// ------------------------------------------------------------------------------------------------------------------ \\

}
