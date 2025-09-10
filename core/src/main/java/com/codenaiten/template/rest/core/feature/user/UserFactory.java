package com.codenaiten.template.rest.core.feature.user;

import com.codenaiten.template.rest.core.feature.media.vo.MediaId;
import com.codenaiten.template.rest.core.feature.user.validation.UserValidator;
import com.codenaiten.template.rest.core.feature.user.vo.*;
import com.codenaiten.template.rest.core.shared.exception.ConstraintException;
import com.codenaiten.template.rest.core.shared.exception.ValidationException;
import com.codenaiten.template.rest.core.shared.vo.Email;
import com.codenaiten.template.rest.core.shared.vo.EncodedPassword;
import com.codenaiten.template.rest.core.shared.vo.Timestamp;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class UserFactory {

    private final Email email;
    private final UserUsername username;
    private final UserRole role;
    private final UserName name;
    private final LocalDate birthdate;
    private final EncodedPassword password;

// -------------------------------------------------------------------------------------------------------------- \\

    private MediaId image;
    private UserSurname surname;

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| SETTERS |---------------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    public UserFactory image( final MediaId image ){
        this.image = image;
        return this;
    }

    public UserFactory surname( final UserSurname surname ){
        this.surname = surname;
        return this;
    }

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| BUILDER METHOD |--------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    public User build( final UserValidator validator ){
        // Step 01: Create User
        final UserId id = UserId.random();
        final Timestamp now = Timestamp.now();
        final User user = User.builder().id( id ).email( this.email ).username( this.username ).role( this.role )
                .image( this.image ).name( this.name ).surname( this.surname ).birthdate( this.birthdate )
                .password( this.password ).createdAt( now ).build();

        // Step 02: Validate User
        final List<ConstraintException> violations = validator.validate( user );
        if( !violations.isEmpty() ) throw new ValidationException( violations );

        // Step 03: Return User
        return user;
    }

// ------------------------------------------------------------------------------------------------------------------ \\

}
