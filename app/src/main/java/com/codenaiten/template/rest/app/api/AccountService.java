package com.codenaiten.template.rest.app.api;

import com.codenaiten.template.rest.app.dto.command.PageCommand;
import com.codenaiten.template.rest.app.dto.command.account.AccountCreateCommand;
import com.codenaiten.template.rest.app.dto.command.account.AccountUpdateCommand;
import com.codenaiten.template.rest.app.dto.result.AccountInfoResult;
import com.codenaiten.template.rest.app.dto.result.PageResult;
import com.codenaiten.template.rest.app.vo.Email;
import com.codenaiten.template.rest.app.vo.account.AccountId;
import com.codenaiten.template.rest.app.vo.account.AccountPassword;
import com.codenaiten.template.rest.app.vo.account.AccountRole;

import java.util.List;
import java.util.Locale;

public interface AccountService {

    AccountInfoResult me();
    AccountInfoResult get( AccountId id );
    PageResult<AccountInfoResult> getAll( PageCommand command );
    PageResult<AccountInfoResult> search( String search, PageCommand command );
    List<AccountRole> getSupportedRoles();
    AccountInfoResult create( AccountCreateCommand command );
    AccountInfoResult update( AccountId id, AccountUpdateCommand command );
    AccountInfoResult updateLang( Locale lang );
    AccountInfoResult updateEmail( AccountPassword password, Email newEmail );
    AccountInfoResult updatePassword( AccountPassword password, AccountPassword newPassword );
    AccountInfoResult delete( AccountId id );
    AccountInfoResult delete( AccountPassword password );
}
