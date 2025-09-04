package com.codenaiten.template.rest.app.mapper;

import com.codenaiten.template.rest.app.vo.Email;
import com.codenaiten.template.rest.app.vo.Timestamp;
import com.codenaiten.template.rest.app.vo.ValueObject;
import com.codenaiten.template.rest.app.vo.account.AccountId;
import com.codenaiten.template.rest.app.vo.account.AccountPassword;
import com.codenaiten.template.rest.app.vo.account.AccountRole;
import com.codenaiten.template.rest.app.vo.image.ImageContentType;
import com.codenaiten.template.rest.app.vo.image.ImageId;
import com.codenaiten.template.rest.app.vo.user.UserId;
import com.codenaiten.template.rest.app.vo.user.UserName;
import com.codenaiten.template.rest.app.vo.user.UserSurname;
import com.codenaiten.template.rest.app.vo.user.UserUsername;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.Optional;
import java.util.UUID;

@Mapper( componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
         uses = { OptionalMapper.class, TemporalMapper.class })
public interface ValueObjectMapper {

    default <T extends Serializable> T toValue( final ValueObject<T> valueObject ){
        return Optional.ofNullable( valueObject ).map( ValueObject::value ).orElse( null );
    }

    default Email toEmail( final String value ){
        return Optional.ofNullable( value ).map( Email::new ).orElse( null );
    }

    default Timestamp toTimestamp( final LocalDateTime value ){
        return Optional.ofNullable( value ).map( Timestamp::new ).orElse( null );
    }

    default Locale toLocale( final String lang ){
        return Optional.ofNullable( lang ).map( Locale::forLanguageTag ).orElse( null );
    }

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| IMAGE |------------------------------------------------------------------------------------------------------ \\
// ------------------------------------------------------------------------------------------------------------------ \\

    default ImageId toImageId( final UUID value ){
        return Optional.ofNullable( value ).map( ImageId::new ).orElse( null );
    }

    default ImageContentType toImageContentType( final String contentType ){
        return Optional.ofNullable( contentType ).map( ImageContentType::of ).orElse( null );
    }

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| ACCOUNT |---------------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    default AccountId toAccountId( final UUID value ){
        return Optional.ofNullable( value ).map( AccountId::new ).orElse( null );
    }

    default AccountPassword toAccountPassword( final String value ){
        return Optional.ofNullable( value ).map( AccountPassword::new ).orElse( null );
    }

    default AccountRole toAccountRole( final Integer value ){
        return Optional.ofNullable( value ).map( AccountRole::new ).orElse( null );
    }

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| USER |------------------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    default UserId toUserId( final UUID value ){
        return Optional.ofNullable( value ).map( UserId::new ).orElse( null );
    }

    default UserUsername toUserUsername( final String value ){
        return Optional.ofNullable( value ).map( UserUsername::new ).orElse( null );
    }

    default UserName toUserName( final String value ){
        return Optional.ofNullable( value ).map( UserName::new ).orElse( null );
    }

    default UserSurname toUserSurname( final String value ){
        return Optional.ofNullable( value ).map( UserSurname::new ).orElse( null );
    }

// ------------------------------------------------------------------------------------------------------------------ \\

}
