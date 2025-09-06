package com.codenaiten.template.rest.web.rest.mapper;

import com.codenaiten.template.rest.app.old.dto.command.account.CreateAccountCommand;
import com.codenaiten.template.rest.app.old.dto.command.account.CreateImageCommand;
import com.codenaiten.template.rest.app.old.dto.command.account.FilterAccountCommand;
import com.codenaiten.template.rest.app.old.dto.command.account.UpdateAccountCommand;
import com.codenaiten.template.rest.app.old.dto.command.auth.LoginCommand;
import com.codenaiten.template.rest.app.old.dto.command.auth.RegisterCommand;
import com.codenaiten.template.rest.app.old.dto.command.user.FilterUserCommand;
import com.codenaiten.template.rest.app.old.dto.command.user.UpdateUserCommand;
import com.codenaiten.template.rest.app.old.mapper.OptionalMapper;
import com.codenaiten.template.rest.app.old.mapper.TemporalMapper;
import com.codenaiten.template.rest.app.old.vo.image.ImageContentType;
import com.codenaiten.template.rest.web.rest.dto.request.account.CreateAccountRequest;
import com.codenaiten.template.rest.web.rest.dto.request.account.FilterAccountRequest;
import com.codenaiten.template.rest.web.rest.dto.request.account.UpdateAccountRequest;
import com.codenaiten.template.rest.web.rest.dto.request.auth.LoginRequest;
import com.codenaiten.template.rest.web.rest.dto.request.auth.RegisterRequest;
import com.codenaiten.template.rest.web.rest.dto.request.user.FilterUserRequest;
import com.codenaiten.template.rest.web.rest.dto.request.user.UpdateUserRequest;
import lombok.SneakyThrows;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

@Mapper( componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
         uses = { OptionalMapper.class, TemporalMapper.class })
public interface CommandMapper {

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| AUTHENTICATION |--------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    RegisterCommand toCommand( RegisterRequest request );

    LoginCommand toCommand( LoginRequest request );

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| IMAGE |------------------------------------------------------------------------------------------------------ \\
// ------------------------------------------------------------------------------------------------------------------ \\

    @Mapping( target = "image", source = "file" )
    @Mapping( target = "contentType", source = "file" )
    @Mapping( target = "contentSize", source = "file" )
    CreateImageCommand toCommand( MultipartFile file );

    @SneakyThrows
    default byte[] toByteArray( final MultipartFile file ) {
        if( Objects.nonNull( file ) ) return file.getBytes();
        return new byte[ 0 ];
    }

    @SneakyThrows
    default ImageContentType toContentType(final MultipartFile file ) {
        if( Objects.nonNull( file ) ) return ImageContentType.of( file.getContentType() );
        return null;
    }

    @SneakyThrows
    default Long toContentSize( final MultipartFile file ) {
        if( Objects.nonNull( file ) ) return file.getSize();
        return null;
    }

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| ACCOUNT |---------------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    CreateAccountCommand toCommand( CreateAccountRequest request );

    UpdateAccountCommand toCommand( UpdateAccountRequest request );

    FilterAccountCommand toCommand( FilterAccountRequest request );

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| USER |------------------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    UpdateUserCommand toCommand( UpdateUserRequest request );

    FilterUserCommand toCommand( FilterUserRequest request );

// ------------------------------------------------------------------------------------------------------------------ \\

}
