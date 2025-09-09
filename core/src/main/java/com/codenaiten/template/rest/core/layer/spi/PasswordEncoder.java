package com.codenaiten.template.rest.core.layer.spi;

import com.codenaiten.template.rest.core.layer.vo.EncodedPassword;
import com.codenaiten.template.rest.core.layer.vo.Password;

public interface PasswordEncoder {

//--------------------------------------------------------------------------------------------------------------------\\

    EncodedPassword encode( Password password );

//--------------------------------------------------------------------------------------------------------------------\\

    boolean matches( Password password, EncodedPassword passwordEncoded );

//--------------------------------------------------------------------------------------------------------------------\\

    boolean notMatches( Password password, EncodedPassword passwordEncoded );

//--------------------------------------------------------------------------------------------------------------------\\

}
