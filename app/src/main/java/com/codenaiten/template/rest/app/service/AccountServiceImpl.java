package com.codenaiten.template.rest.app.service;

import com.codenaiten.template.rest.app.api.AccountService;
import com.codenaiten.template.rest.app.authentication.AuthenticationProvider;
import com.codenaiten.template.rest.app.dto.result.AccountInfoResult;
import com.codenaiten.template.rest.app.entity.Account;
import com.codenaiten.template.rest.app.exception.account.AccountNotFoundByIdException;
import com.codenaiten.template.rest.app.exception.auth.AuthNotFoundException;
import com.codenaiten.template.rest.app.mapper.AccountMapper;
import com.codenaiten.template.rest.app.repository.AccountRepository;
import com.codenaiten.template.rest.app.vo.account.AccountId;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AuthenticationProvider authenticationProvider;
    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| IMPLEMENTED METHODS |---------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    @Override
    public AccountInfoResult getMyAccountInfo(){
        final AccountId id = this.authenticationProvider.getAuthenticatedAccountId()
                .orElseThrow( AuthNotFoundException::new );
        final Account account = this.accountRepository.findById( id.value() )
                .orElseThrow( () -> new AccountNotFoundByIdException( id ));
        return this.accountMapper.toInfoResult( account );
    }

// ------------------------------------------------------------------------------------------------------------------ \\

}
