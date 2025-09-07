package com.codenaiten.template.rest.app.feature.user.policy;

import com.codenaiten.template.rest.core.feature.account.Account;
import com.codenaiten.template.rest.core.feature.account.vo.AccountRole;
import com.codenaiten.template.rest.core.feature.user.User;
import com.codenaiten.template.rest.core.shared.policy.AccessPolicy;
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
    public boolean canCreate( final Account requester ){
        return Objects.nonNull( requester );
    }

    @Override
    public boolean canQuery( final Account requester ){
        return Objects.nonNull( requester );
    }

    @Override
    public boolean canRead( final Account requester, final User candidate ){
        return Objects.nonNull( requester );
    }

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
