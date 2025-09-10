package com.codenaiten.template.rest.core.feature.media.policy;

import com.codenaiten.template.rest.core.feature.media.Media;
import com.codenaiten.template.rest.core.feature.user.port.spi.UserRepository;
import com.codenaiten.template.rest.core.shared.exception.AppException;
import com.codenaiten.template.rest.core.shared.policy.Policy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Slf4j
@Component
@RequiredArgsConstructor
public class ValidMediaOwnerSpec implements Policy<Media> {

    private final UserRepository userRepository;

//--------------------------------------------------------------------------------------------------------------------\\
//---| IMPLEMENTED METHODS |------------------------------------------------------------------------------------------\\
//--------------------------------------------------------------------------------------------------------------------\\

    @Override
    public void check( final Media candidate ){
        if( Objects.isNull( candidate )) throw new IllegalArgumentException( "Media to check valid owner is required" );
        if( !this.userRepository.exists( candidate.getOwner() )) throw new AppException();
    }

//--------------------------------------------------------------------------------------------------------------------\\

}
