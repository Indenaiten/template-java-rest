package com.codenaiten.template.rest.app.vo;

import java.io.Serializable;

public interface ValueObject<T extends Serializable>{

    T value();
}
