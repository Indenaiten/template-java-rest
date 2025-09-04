package com.codenaiten.template.rest.app.spec.access.image;

import com.codenaiten.template.rest.app.entity.Account;
import com.codenaiten.template.rest.app.entity.Image;
import com.codenaiten.template.rest.app.spec.Specification;
import com.codenaiten.template.rest.app.vo.account.AccountRole;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

/**
 * Specification que permite comprobar si se tiene permiso para acceder a la información de los recursos de tipo
 * {@link Image}.
 *
 * @see Specification
 * @see Account
 */
@Slf4j
@RequiredArgsConstructor
public class ImageReadSpec implements Specification<Account> {

    /** {@link Image} a la que se requiere acceder */
    private final Image image;

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| IMPLEMENTED METHODS |---------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    @Override
    public boolean test( final Account candidate ){
        if( Objects.isNull( this.image) || Objects.isNull( candidate )) return false;
        return candidate.hasRole( AccountRole.ADMIN ) || this.image.isOwner( candidate.getOwner() );
    }

// ------------------------------------------------------------------------------------------------------------------ \\

}
