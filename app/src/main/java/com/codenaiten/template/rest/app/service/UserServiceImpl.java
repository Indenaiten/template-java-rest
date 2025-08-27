package com.codenaiten.template.rest.app.service;

import com.codenaiten.template.rest.app.api.UserService;
import com.codenaiten.template.rest.app.authentication.AuthenticationProvider;
import com.codenaiten.template.rest.app.dto.command.PageCommand;
import com.codenaiten.template.rest.app.dto.command.user.UserUpdateCommand;
import com.codenaiten.template.rest.app.dto.result.PageResult;
import com.codenaiten.template.rest.app.dto.result.UserInfoResult;
import com.codenaiten.template.rest.app.editor.UserEditor;
import com.codenaiten.template.rest.app.entity.Account;
import com.codenaiten.template.rest.app.entity.User;
import com.codenaiten.template.rest.app.exception.data.found.UserNotFoundByIdException;
import com.codenaiten.template.rest.app.exception.security.AuthNotFoundException;
import com.codenaiten.template.rest.app.mapper.UserMapper;
import com.codenaiten.template.rest.app.policy.UserAccessPolicy;
import com.codenaiten.template.rest.app.policy.UserMinimumAgePolicy;
import com.codenaiten.template.rest.app.policy.UserUsernameUniquenessPolicy;
import com.codenaiten.template.rest.app.properties.AppProperties;
import com.codenaiten.template.rest.app.repository.UserRepository;
import com.codenaiten.template.rest.app.vo.user.UserId;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final AppProperties appProperties;

    private final AuthenticationProvider authenticationProvider;

    private final UserRepository userRepository;

    private final UserMapper userMapper;

// ------------------------------------------------------------------------------------------------------------------ \\

    private UserEditor userEditor;
    private UserAccessPolicy userAccessPolicy;

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| INITIALIZER |------------------------------------------------------------------------------------------------ \\
// ------------------------------------------------------------------------------------------------------------------ \\

    @PostConstruct
    public void init(){
        this.userAccessPolicy = new UserAccessPolicy();

        var userUsernameUniquenessPolicy = new UserUsernameUniquenessPolicy( this.userRepository );
        var userMinimumAgePolicy = new UserMinimumAgePolicy( this.appProperties );
        this.userEditor = new UserEditor( userUsernameUniquenessPolicy, userMinimumAgePolicy );
    }

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| IMPLEMENTED METHODS |---------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\


    @Override
    public UserInfoResult me() {
        final Account requester = this.authenticationProvider.getAuthenticatedAccount()
                .orElseThrow( AuthNotFoundException::new );
        final User user = requester.getOwner();

        return this.userMapper.toInfoResult( user );
    }

    @Override
    public UserInfoResult get( final UserId id ) {
        final User user = this.userRepository.findById( id.value() )
                .orElseThrow( () -> new UserNotFoundByIdException( id ));

        return this.userMapper.toInfoResult( user );
    }

    @Override
    public PageResult<UserInfoResult> getAll( final PageCommand command ){
        final Pageable pageable = PageRequest.of( command.page(), command.size() );
        final Page<User> page = this.userRepository.findAll( pageable );
        final List<UserInfoResult> content = page.getContent().stream().map( this.userMapper::toInfoResult ).toList();
        return new PageResult<>( page.getTotalElements(), page.getNumber(), page.getSize(), content );
    }

    @Override
    public PageResult<UserInfoResult> search( final String search, final PageCommand command ){
        final Pageable pageable = PageRequest.of( command.page(), command.size() );
        final Page<User> page = this.userRepository.search( search, pageable );
        final List<UserInfoResult> content = page.getContent().stream().map( this.userMapper::toInfoResult ).toList();
        return new PageResult<>( page.getTotalElements(), page.getNumber(), page.getSize(), content );
    }

    @Override
    public UserInfoResult update( final UserId id, final UserUpdateCommand command ) {
        final Account requester = this.authenticationProvider.getAuthenticatedAccount()
                .orElseThrow( AuthNotFoundException::new );
        final User user = requester.getOwner();
        this.userAccessPolicy.checkWrite( requester, user );

        final UserEditor.Editor editor = this.userEditor.update( user );
        editor.username( command.username() ).name( command.name() ).surname( command.surname() ).birthdate( command.birthdate() );

        if( editor.hasChanges() ){
            editor.apply();
            this.userRepository.save( user );
        }

        return this.userMapper.toInfoResult( user );
    }

    @Override
    public UserInfoResult update( final UserUpdateCommand command ){
        final Account requester = this.authenticationProvider.getAuthenticatedAccount()
                .orElseThrow( AuthNotFoundException::new );
        final User user = requester.getOwner();

        final UserEditor.Editor editor = this.userEditor.update( user );
        editor.username( command.username() ).name( command.name() ).surname( command.surname() ).birthdate( command.birthdate() );

        if( editor.hasChanges() ){
            editor.apply();
            this.userRepository.save( user );
        }

        return this.userMapper.toInfoResult( user );
    }

// ------------------------------------------------------------------------------------------------------------------ \\

}
