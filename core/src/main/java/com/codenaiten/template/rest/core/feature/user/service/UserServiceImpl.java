package com.codenaiten.template.rest.core.feature.user.service;

import com.codenaiten.template.rest.core.feature.user.UserService;
import com.codenaiten.template.rest.core.feature.user.dto.UserPrivateInfo;
import com.codenaiten.template.rest.core.feature.user.dto.UserPublicInfo;
import com.codenaiten.template.rest.core.feature.user.vo.UserId;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    @Override
    public UserPublicInfo getPublicInfo( final UserId userId ){
        return null;
    }

    @Override
    public UserPrivateInfo getPrivateInfo( final UserId userId ){
        return null;
    }
}
