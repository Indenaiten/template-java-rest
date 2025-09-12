package com.codenaiten.template.rest.core.shared.spi;

import com.codenaiten.template.rest.core.shared.vo.EncodedPassword;

public interface PasswordEncoder {

//--------------------------------------------------------------------------------------------------------------------\\

    EncodedPassword encode( String password );

//--------------------------------------------------------------------------------------------------------------------\\

    boolean matches( String password, EncodedPassword passwordEncoded );

//--------------------------------------------------------------------------------------------------------------------\\

    boolean notMatches( String password, EncodedPassword passwordEncoded );

//--------------------------------------------------------------------------------------------------------------------\\

}
