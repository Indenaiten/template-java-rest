package com.codenaiten.template.rest.app.properties;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Getter
@Component
@RequiredArgsConstructor
public class AppProperties extends Properties {

    @Value( "${app.user.age.min:18}" )
    private Integer userMinimumAge;

}
