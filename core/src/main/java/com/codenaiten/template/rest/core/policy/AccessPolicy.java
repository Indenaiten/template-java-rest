package com.codenaiten.template.rest.core.policy;

import com.codenaiten.template.rest.core.entity.Account;
import com.codenaiten.template.rest.core.entity.Entity;

import java.io.Serializable;

public interface AccessPolicy<T extends Entity<? extends Serializable>> {
    default boolean canCreate( Account requester ) { return true; }
    default boolean canQuery( Account requester ) { return true; }
    default boolean canRead( Account requester, T candidate ) { return true; }
    default boolean canWrite( Account requester, T candidate ) { return true; }
    default boolean canDelete( Account requester, T candidate ) { return true; }
}
