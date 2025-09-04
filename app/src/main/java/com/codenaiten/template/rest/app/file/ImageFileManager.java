package com.codenaiten.template.rest.app.file;

import com.codenaiten.template.rest.app.vo.image.ImageId;

import java.io.File;
import java.io.IOException;

public interface ImageFileManager {

    File write( ImageId id, byte[] content ) throws IOException;
    File get( ImageId id ) throws IOException;
    byte[] read( File file ) throws IOException;
    void delete( File file ) throws IOException;
}
