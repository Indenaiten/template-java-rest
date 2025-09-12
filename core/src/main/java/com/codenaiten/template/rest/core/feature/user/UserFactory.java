package com.codenaiten.template.rest.core.feature.user;

import com.codenaiten.template.rest.core.feature.media.vo.MediaId;
import com.codenaiten.template.rest.core.feature.user.constraint.*;
import com.codenaiten.template.rest.core.feature.user.vo.*;
import com.codenaiten.template.rest.core.shared.constraint.ConstraintViolation;
import com.codenaiten.template.rest.core.shared.exception.ConstraintException;
import com.codenaiten.template.rest.core.shared.vo.Email;
import com.codenaiten.template.rest.core.shared.vo.EncodedPassword;
import com.codenaiten.template.rest.core.shared.vo.Timestamp;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.util.ArrayList;
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

    public User build( final UserIdConstraint userIdConstraint,
                       final UserEmailConstraint userEmailConstraint,
                       final UserUsernameConstraint userUsernameConstraint,
                       final UserBirthdateConstraint userBirthdateConstraint,
                       final UserImageConstraint userImageConstraint
    ){
        // Step 01: Create User
        final UserId id = UserId.random();
        final Timestamp now = Timestamp.now();
        final User user = new User( id, this.email, this.username, this.role, this.image, this.name, this.surname,
                this.birthdate, this.password, now, null );

        // Step 02: Validate User
        final List<ConstraintViolation<?>> violations = new ArrayList<>();
        userIdConstraint.check( user ).ifPresent( violations::add );
        userEmailConstraint.check( user ).ifPresent( violations::add );
        userUsernameConstraint.check( user ).ifPresent( violations::add );
        userBirthdateConstraint.check( user ).ifPresent( violations::add );
        userImageConstraint.check( user ).ifPresent( violations::add );
        if( !violations.isEmpty() ) throw new ConstraintException( violations );

        // Step 03: Return User
        return user;
    }

// ------------------------------------------------------------------------------------------------------------------ \\

}
