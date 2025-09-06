package com.codenaiten.template.rest.app.policy;

import com.codenaiten.template.rest.core.entity.Account;
import com.codenaiten.template.rest.core.entity.User;
import com.codenaiten.template.rest.core.policy.AccessPolicy;
import com.codenaiten.template.rest.core.vo.account.AccountRole;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Slf4j
@Component
@RequiredArgsConstructor
public class DefaultUserAccessPolicy implements AccessPolicy<User>{

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| IMPLEMENTED METHODS |---------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    @Override
    public boolean canWrite( final Account requester, final User candidate ){
        if( Objects.isNull( requester ) && Objects.isNull( candidate )) return false;
        return requester.hasRole( AccountRole.ADMIN ) || Objects.equals( requester.getOwner(), candidate.getId() );
    }

    @Override
    public boolean canDelete( final Account requester, final User candidate ){
        if( Objects.isNull( requester ) && Objects.isNull( candidate )) return false;
        return requester.hasRole( AccountRole.ADMIN ) || Objects.equals( requester.getOwner(), candidate.getId() );
    }

// ------------------------------------------------------------------------------------------------------------------ \\

}
