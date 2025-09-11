package com.codenaiten.template.rest.core.feature.user.service;

import com.codenaiten.template.rest.core.feature.user.User;
import com.codenaiten.template.rest.core.feature.user.UserEditor;
import com.codenaiten.template.rest.core.feature.user.constraint.UserBirthdateConstraint;
import com.codenaiten.template.rest.core.feature.user.constraint.UserEmailConstraint;
import com.codenaiten.template.rest.core.feature.user.constraint.UserImageConstraint;
import com.codenaiten.template.rest.core.feature.user.constraint.UserUsernameConstraint;
import com.codenaiten.template.rest.core.feature.user.dto.input.UpdateUser;
import com.codenaiten.template.rest.core.feature.user.exception.UserNotFoundException;
import com.codenaiten.template.rest.core.feature.user.port.spi.UserRepository;
import com.codenaiten.template.rest.core.feature.user.vo.UserId;
import com.codenaiten.template.rest.core.feature.user.vo.UserRole;
import com.codenaiten.template.rest.core.shared.spi.PasswordEncoder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class UpdateUserService {

    // Ports
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // User Constraints
    private final UserEmailConstraint userEmailConstraint;
    private final UserUsernameConstraint userUsernameConstraint;
    private final UserBirthdateConstraint userBirthdateConstraint;
    private final UserImageConstraint userImageConstraint;

//--------------------------------------------------------------------------------------------------------------------\\
//---| METHODS |------------------------------------------------------------------------------------------------------\\
//--------------------------------------------------------------------------------------------------------------------\\

    public User update( final UserId id, final UpdateUser input ){
        // Step 01: Get User to Update
        final User user = this.userRepository.find( id ).orElseThrow( () -> new UserNotFoundException( id ));

        // Step 02: Get Default User Data
        final UserRole defaultRole = this.userRepository.count() == 0 ? UserRole.ADMIN : UserRole.USER;

        // Step 03: Update User with Editor
        final UserEditor editor = user.update();
        editor.role( input.getRole().orElse( defaultRole ));
        editor.image( input.getImage().orElse( null ));
        editor.surname( input.getSurname().orElse( null ));
        input.getEmail().ifPresent( editor::email );
        input.getUsername().ifPresent( editor::username );
        input.getName().ifPresent( editor::name );
        input.getBirthdate().ifPresent( editor::birthdate );
        input.getPassword().map( this.passwordEncoder::encode ).ifPresent( editor::password );

        // Step 04: Check if has changes
        if( editor.hasChanges() ){ // If has changes, apply changes
            editor.apply( this.userEmailConstraint,
                          this.userUsernameConstraint,
                          this.userBirthdateConstraint,
                          this.userImageConstraint );

            // Save changes
            this.userRepository.save( user );
        }

        // Step 05: Return updated User
        return user;
    }

//--------------------------------------------------------------------------------------------------------------------\\

}
