package com.codenaiten.template.rest.app.old.file.impl;

import com.codenaiten.template.rest.app.old.file.ImageFileManager;
import com.codenaiten.template.rest.app.old.vo.image.ImageId;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Slf4j
@Component
@RequiredArgsConstructor
public class DefaultImageFileManager implements ImageFileManager {

    private static final Path IMAGE_DIRECTORY = Path.of( "./storage/images" );

    @Override
    public File write( final ImageId id, final byte[] content ) throws IOException {
        final Path filepath = IMAGE_DIRECTORY.resolve( id.toString() );
        Files.write( filepath, content );
        return filepath.toFile();
    }

    @Override
    public File get( final ImageId id ) throws IOException {
        final Path filePath = IMAGE_DIRECTORY.resolve( id.toString() );
        return filePath.toFile();
    }

    @Override
    public byte[] read( final File file ) throws IOException {
        return Files.readAllBytes( file.toPath() );
    }

    @Override
    public void delete(final File file) throws IOException {
        Files.delete( file.toPath() );
    }
}
