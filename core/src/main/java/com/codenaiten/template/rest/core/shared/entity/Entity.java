package com.codenaiten.template.rest.core.shared.entity;

import com.codenaiten.template.rest.core.shared.vo.common.Timestamp;

import java.io.Serializable;
import java.util.Optional;

public interface Entity<T extends Serializable> {

    T getId();
    Timestamp getCreatedAt();
    Optional<Timestamp> getUpdatedAt();
    default boolean different( Object obj ){
        return !this.equals( obj );
    }
}
