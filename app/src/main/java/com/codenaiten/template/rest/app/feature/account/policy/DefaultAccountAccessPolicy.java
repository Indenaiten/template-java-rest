package com.codenaiten.template.rest.app.feature.account.policy;

import com.codenaiten.template.rest.core.feature.account.Account;
import com.codenaiten.template.rest.core.feature.account.vo.AccountRole;
import com.codenaiten.template.rest.core.shared.policy.AccessPolicy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Slf4j
@Component
@RequiredArgsConstructor
public class DefaultAccountAccessPolicy implements AccessPolicy<Account>{

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| IMPLEMENTED METHODS |---------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    @Override
    public boolean canCreate( final Account requester ){
        if( Objects.isNull( requester )) return false;
        return requester.hasRole( AccountRole.ADMIN );
    }

    @Override
    public boolean canQuery( final Account requester ){
        if( Objects.isNull( requester )) return false;
        return requester.hasRole( AccountRole.ADMIN );
    }

    @Override
    public boolean canRead( final Account requester, final Account candidate ){
        if( Objects.isNull( requester ) && Objects.isNull( candidate )) return false;
        return requester.hasRole( AccountRole.ADMIN ) || candidate.isOwner( requester.getOwner() );
    }

    @Override
    public boolean canWrite( final Account requester, final Account candidate ){
        if( Objects.isNull( requester ) && Objects.isNull( candidate )) return false;
        return requester.hasRole( AccountRole.ADMIN ) || candidate.isOwner( requester.getOwner() );
    }

    @Override
    public boolean canDelete( final Account requester, final Account candidate ){
        if( Objects.isNull( requester ) && Objects.isNull( candidate )) return false;
        return requester.hasRole( AccountRole.ADMIN ) || candidate.isOwner( requester.getOwner() );
    }

// ------------------------------------------------------------------------------------------------------------------ \\

}
