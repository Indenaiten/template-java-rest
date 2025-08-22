package com.codenaiten.template.rest.web.rest.controller;

import com.codenaiten.template.rest.app.api.AccountService;
import com.codenaiten.template.rest.app.dto.result.AccountInfoResult;
import com.codenaiten.template.rest.web.rest.api.AccountApiRest;
import com.codenaiten.template.rest.web.rest.dto.response.AccountInfoResponse;
import com.codenaiten.template.rest.web.rest.mapper.AccountWebMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@AllArgsConstructor
public class AccountRestController implements AccountApiRest {

    private final AccountService accountService;
    private final AccountWebMapper accountMapper;

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| IMPLEMENTED METHODS |---------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    @Override
    public ResponseEntity<AccountInfoResponse> getMyAccountInfo() {
        final AccountInfoResult result = this.accountService.getMyAccountInfo();
        final AccountInfoResponse response = this.accountMapper.toResponse( result );
        return ResponseEntity.status( HttpStatus.OK ).body( response );
    }

// ------------------------------------------------------------------------------------------------------------------ \\

}
