package com.codenaiten.template.rest.core.feature.user.service;

import com.codenaiten.template.rest.core.feature.user.User;
import com.codenaiten.template.rest.core.feature.user.dto.input.UpdateUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class UpdateUserService {

//--------------------------------------------------------------------------------------------------------------------\\
//---| METHODS |------------------------------------------------------------------------------------------------------\\
//--------------------------------------------------------------------------------------------------------------------\\

    public User update( final UpdateUser input ){
        throw new UnsupportedOperationException();
    }

//--------------------------------------------------------------------------------------------------------------------\\

}
