package com.codenaiten.template.rest.app.repository;

import com.codenaiten.template.rest.app.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface AccountRepository extends JpaRepository<Account, UUID> {

    boolean existsByEmail( String email );
    Optional<Account> findByEmail( String email );
    Optional<Account> findByOwner_Username( String username );
    Optional<Account> findByOwner_Id( UUID id );
}
