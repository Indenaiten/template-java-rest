package com.codenaiten.template.rest.core.feature.media.service;

import com.codenaiten.template.rest.core.feature.media.Media;

public interface MediaService{
    void create( Media media );
    void update( Media media );
    void delete( Media media );
}
