package com.codenaiten.template.rest.app.old.policy;

import com.codenaiten.template.rest.app.old.AppMessage;
import com.codenaiten.template.rest.app.old.entity.Account;
import com.codenaiten.template.rest.app.old.entity.Image;
import com.codenaiten.template.rest.app.old.exception.security.access.ReadAccessException;
import com.codenaiten.template.rest.app.old.spec.access.image.ImageReadSpec;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Policy que permite controlar el acceso a los recursos de tipo {@link Image}.
 */
@Slf4j
@RequiredArgsConstructor
public class ImageAccessPolicy {

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| METHODS |---------------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    /**
     * Permite comprobar si se tiene permiso para leer los recursos de tipo {@link Image}.
     *
     * @param requester {@link Account} que representa la cuenta de usuario que se va a evaluar si tiene permiso.
     * @param image {@link Image} que representa la imagen que se va a consultar.
     *
     * @throws ReadAccessException si no tiene permiso para leer el recurso.
     *
     * @see ImageReadSpec
     */
    public void checkRead( final Account requester, final Image image ){
        if( new ImageReadSpec( image ).not().test( requester ))
            throw new ReadAccessException( AppMessage.ERROR_SECURITY_IMAGE_READ_NOT_ALLOWED, image.getId(), requester.getId() );
    }

// ------------------------------------------------------------------------------------------------------------------ \\

}
