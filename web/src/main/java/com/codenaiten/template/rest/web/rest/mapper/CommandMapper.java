package com.codenaiten.template.rest.web.rest.mapper;

import com.codenaiten.template.rest.app.dto.command.account.CreateAccountCommand;
import com.codenaiten.template.rest.app.dto.command.account.FilterAccountCommand;
import com.codenaiten.template.rest.app.dto.command.account.UpdateAccountCommand;
import com.codenaiten.template.rest.app.dto.command.auth.LoginCommand;
import com.codenaiten.template.rest.app.dto.command.auth.RegisterCommand;
import com.codenaiten.template.rest.app.dto.command.user.FilterUserCommand;
import com.codenaiten.template.rest.app.dto.command.user.UpdateUserCommand;
import com.codenaiten.template.rest.app.mapper.OptionalMapper;
import com.codenaiten.template.rest.app.mapper.TemporalMapper;
import com.codenaiten.template.rest.app.mapper.ValueObjectMapper;
import com.codenaiten.template.rest.app.vo.image.ImageContentType;
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
         uses = { OptionalMapper.class, TemporalMapper.class, ValueObjectMapper.class })
public interface CommandMapper {

    @SneakyThrows
    default byte[] toByteArray( final MultipartFile file ) {
        if( Objects.nonNull( file ) ) return file.getBytes();
        return new byte[ 0 ];
    }

    @SneakyThrows
    default ImageContentType toContentType( final MultipartFile file ) {
        if( Objects.nonNull( file ) ) return ImageContentType.of( file.getContentType() );
        return null;
    }

    @SneakyThrows
    default Long toContentSize( final MultipartFile file ) {
        if( Objects.nonNull( file ) ) return file.getSize();
        return null;
    }

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| AUTHENTICATION |--------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    @Mapping( target = "image", source = "image" )
    @Mapping( target = "imageContentType", source = "image" )
    @Mapping( target = "imageSize", source = "image" )
    @Mapping( target = "lang", source = "request.lang" )
    @Mapping( target = "username", source = "request.username" )
    @Mapping( target = "email", source = "request.email" )
    @Mapping( target = "name", source = "request.name" )
    @Mapping( target = "surname", source = "request.surname" )
    @Mapping( target = "birthdate", source = "request.birthdate" )
    @Mapping( target = "password", source = "request.password" )
    RegisterCommand toCommand( RegisterRequest request, MultipartFile image );

    LoginCommand toCommand( LoginRequest request );

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| ACCOUNT |---------------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    @Mapping( target = "image", source = "image" )
    @Mapping( target = "imageContentType", source = "image" )
    @Mapping( target = "imageSize", source = "image" )
    @Mapping( target = "lang", source = "request.lang" )
    @Mapping( target = "role", source = "request.role" )
    @Mapping( target = "username", source = "request.username" )
    @Mapping( target = "email", source = "request.email" )
    @Mapping( target = "name", source = "request.name" )
    @Mapping( target = "surname", source = "request.surname" )
    @Mapping( target = "birthdate", source = "request.birthdate" )
    @Mapping( target = "password", source = "request.password" )
    CreateAccountCommand toCommand( CreateAccountRequest request, MultipartFile image );

    UpdateAccountCommand toCommand( UpdateAccountRequest request );

    FilterAccountCommand toCommand( FilterAccountRequest request );

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| USER |------------------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    UpdateUserCommand toCommand( UpdateUserRequest request );

    FilterUserCommand toCommand( FilterUserRequest request );

// ------------------------------------------------------------------------------------------------------------------ \\

}
