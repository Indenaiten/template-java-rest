package com.codenaiten.template.rest.app.dto.command.account;

public record FilterAccountCommand(
        String username,
        Integer role,
        String lang,
        String email,
        String name,
        String surname
){

// ------------------------------------------------------------------------------------------------------------------ \\

}
