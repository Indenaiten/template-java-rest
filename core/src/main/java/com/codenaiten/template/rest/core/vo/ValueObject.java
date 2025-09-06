package com.codenaiten.template.rest.core.vo;

import java.io.Serializable;

public interface ValueObject<T extends Serializable> extends Serializable {

    T value();

    default boolean different( Object obj ){
        return !this.equals( obj );
    }
}
