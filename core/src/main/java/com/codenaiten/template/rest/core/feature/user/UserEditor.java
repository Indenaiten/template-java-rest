package com.codenaiten.template.rest.core.feature.user;

import com.codenaiten.template.rest.core.feature.media.vo.MediaId;
import com.codenaiten.template.rest.core.feature.user.constraint.UserBirthdateConstraint;
import com.codenaiten.template.rest.core.feature.user.constraint.UserEmailConstraint;
import com.codenaiten.template.rest.core.feature.user.constraint.UserImageConstraint;
import com.codenaiten.template.rest.core.feature.user.constraint.UserUsernameConstraint;
import com.codenaiten.template.rest.core.feature.user.vo.UserName;
import com.codenaiten.template.rest.core.feature.user.vo.UserRole;
import com.codenaiten.template.rest.core.feature.user.vo.UserSurname;
import com.codenaiten.template.rest.core.feature.user.vo.UserUsername;
import com.codenaiten.template.rest.core.shared.constraint.ConstraintViolation;
import com.codenaiten.template.rest.core.shared.exception.ConstraintException;
import com.codenaiten.template.rest.core.shared.vo.Email;
import com.codenaiten.template.rest.core.shared.vo.EncodedPassword;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
public class UserEditor {

    private final User update;
    private final User user;

// ------------------------------------------------------------------------------------------------------------------ \\

    private boolean updatedPassword = false;

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| CONSTRUCTOR |------------------------------------------------------------------------------------------------ \\
// ------------------------------------------------------------------------------------------------------------------ \\

    public UserEditor( final User user ){
        Objects.requireNonNull( user, "User to update in UserEditor is required" );
        this.update = user.copy();
        this.user = user;
    }

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| SETTERS |---------------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    public UserEditor email( final Email email ){
        Optional.ofNullable( email ).ifPresent( this.update::setEmail );
        return this;
    }

    public UserEditor username( final UserUsername username ){
        Optional.ofNullable( username ).ifPresent( this.update::setUsername );
        return this;
    }

    public UserEditor role( final UserRole role ){
        Optional.ofNullable( role ).ifPresent( this.update::setRole );
        return this;
    }

    public UserEditor image( final MediaId image ){
        this.update.setImage( image );
        return this;
    }

    public UserEditor name( final UserName name ){
        Optional.ofNullable( name ).ifPresent( this.update::setName );
        return this;
    }

    public UserEditor surname( final UserSurname surname ){
        this.update.setSurname( surname );
        return this;
    }

    public UserEditor birthdate( final LocalDate birthdate ){
        Optional.ofNullable( birthdate ).ifPresent( this.update::setBirthdate );
        return this;
    }

    public UserEditor password( final EncodedPassword password ){
        this.update.setPassword( password );
        this.updatedPassword = true;
        return this;
    }

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| CHECKERS |--------------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    public boolean hasChanges(){
        return this.updatedPassword ||
               !Objects.equals( this.user.getEmail(), this.update.getEmail() ) ||
               !Objects.equals( this.user.getUsername(), this.update.getUsername() ) ||
               !Objects.equals( this.user.getRole(), this.update.getRole() ) ||
               !Objects.equals( this.user.getImage(), this.update.getImage() ) ||
               !Objects.equals( this.user.getName(), this.update.getName() ) ||
               !Objects.equals( this.user.getSurname(), this.update.getSurname() ) ||
               !Objects.equals( this.user.getBirthdate(), this.update.getBirthdate() );
    }

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| APPLY METHOD |----------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    public boolean apply( final UserEmailConstraint userEmailConstraint,
                          final UserUsernameConstraint userUsernameConstraint,
                          final UserBirthdateConstraint userBirthdateConstraint,
                          final UserImageConstraint userImageConstraint
    ){
        // Step 01: Validate Info
        final List<ConstraintViolation<?>> violations = new ArrayList<>();

        // Validate User Email if changed
        if( !Objects.equals( this.user.getEmail(), this.update.getEmail() ))
            userEmailConstraint.check( this.update ).ifPresent( violations::add );

        // Validate User Username if changed
        if( !Objects.equals( this.user.getUsername(), this.update.getUsername() ))
            userUsernameConstraint.check( this.update ).ifPresent( violations::add );

        // Validate User Birthdate if changed
        if( !Objects.equals( this.user.getBirthdate(), this.update.getBirthdate() ))
            userBirthdateConstraint.check( this.update ).ifPresent( violations::add );

        // Validate User Image if changed
        if( !Objects.equals( this.user.getImage(), this.update.getImage() ))
            userImageConstraint.check( this.update ).ifPresent( violations::add );

        // Step 02: If has violations, throw exception
        if( !violations.isEmpty() ) throw new ConstraintException( violations );

        // Step 02: Check changes
        final boolean hasChanges = this.hasChanges();
        if( hasChanges ){ // If has changes, apply them
            this.user.setEmail( this.update.getEmail() );
            this.user.setUsername( this.update.getUsername() );
            this.user.setRole( this.update.getRole() );
            this.user.setImage( this.update.getImage().orElse( null ));
            this.user.setName( this.update.getName() );
            this.user.setSurname( this.update.getSurname().orElse( null ));
            this.user.setBirthdate( this.update.getBirthdate() );
            this.user.setPassword( this.update.getPassword() );

            // Updated UpdatedAt Timestamp
            this.user.setUpdatedAt();
        }

        // Step 04: Return true if has changes, false otherwise
        return hasChanges;
    }

// ------------------------------------------------------------------------------------------------------------------ \\

}
