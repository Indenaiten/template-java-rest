package com.codenaiten.template.rest.core.shared.spi;

import com.codenaiten.template.rest.core.shared.vo.EncodedPassword;
import com.codenaiten.template.rest.core.shared.vo.Password;

public interface PasswordEncoder {

//--------------------------------------------------------------------------------------------------------------------\\

    EncodedPassword encode( Password password );

//--------------------------------------------------------------------------------------------------------------------\\

    boolean matches( Password password, EncodedPassword passwordEncoded );

//--------------------------------------------------------------------------------------------------------------------\\

    boolean notMatches( Password password, EncodedPassword passwordEncoded );

//--------------------------------------------------------------------------------------------------------------------\\

}
