package com.codenaiten.template.rest.core.shared.dto;

import java.util.List;

public record PageInfo<T>(
    Long total,
    Integer totalPages,
    Integer page,
    Integer size,
    Boolean first,
    Boolean last,
    Boolean previous,
    Boolean next,
    List<T> content
){

//--------------------------------------------------------------------------------------------------------------------\\

}