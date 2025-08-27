package com.codenaiten.template.rest.app.dto.result;

import lombok.Getter;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Getter
public class PageResult<T extends Serializable> implements Serializable{

    @Serial
    private static final long serialVersionUID = 1L;

// ------------------------------------------------------------------------------------------------------------------ \\

    private final Long total;
    private final Long totalPages;
    private final Integer page;
    private final Integer size;
    private final List<T> content;

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| CONSTRUCTOR |------------------------------------------------------------------------------------------------ \\
// ------------------------------------------------------------------------------------------------------------------ \\

    public PageResult( final Long total, final Integer page, final Integer size, final List<T> content ){
        this.total = total;
        this.page = page;
        this.size = size;
        this.content = content;
        this.totalPages = ((total + size - 1) / size);
    }

// ------------------------------------------------------------------------------------------------------------------ \\

}