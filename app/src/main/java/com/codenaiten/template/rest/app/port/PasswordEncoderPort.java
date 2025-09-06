package com.codenaiten.template.rest.app.port;

import com.codenaiten.template.rest.core.vo.EncodedPassword;
import com.codenaiten.template.rest.core.vo.Password;

public interface PasswordEncoderPort{
    EncodedPassword encode( Password password );
    boolean matches( Password password, EncodedPassword passwordEncoded );
    boolean notMatches( Password password, EncodedPassword passwordEncoded );
}
