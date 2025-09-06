package com.codenaiten.template.rest.app.old.properties;

import com.codenaiten.template.rest.app.old.entity.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Properties que contiene las propiedades de la aplicación.
 *
 * @see Properties
 */
@Slf4j
@Getter
@Component
@RequiredArgsConstructor
public class AppProperties extends Properties {

    /** Edad mínima del {@link User} para poder acceder al sistema */
    @Value( "${app.user.age.min:18}" )
    private Integer userMinimumAge;

}
