package com.codenaiten.template.rest.core.shared.policy;

import com.codenaiten.template.rest.core.feature.account.Account;
import com.codenaiten.template.rest.core.shared.entity.Entity;

import java.io.Serializable;

public interface AccessPolicy<T extends Entity<? extends Serializable>> {
    boolean canCreate( Account requester );
    boolean canQuery( Account requester );
    boolean canRead( Account requester, T candidate );
    boolean canWrite( Account requester, T candidate );
    boolean canDelete( Account requester, T candidate );
}
