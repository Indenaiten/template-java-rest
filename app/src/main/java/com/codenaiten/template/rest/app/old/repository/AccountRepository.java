package com.codenaiten.template.rest.app.old.repository;

import com.codenaiten.template.rest.app.old.entity.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface AccountRepository extends JpaRepository<Account, UUID> {

    boolean existsByEmail( String email );
    Optional<Account> findByEmail( String email );
    Optional<Account> findByOwner_Username( String username );
    Optional<Account> findByOwner_Id( UUID id );

    @Query("""
        SELECT acc 
        FROM Account acc 
        JOIN acc.owner usr 
        WHERE acc.email LIKE %:search% OR 
              usr.username LIKE %:search% OR 
              usr.name LIKE %:search% OR 
              usr.surname LIKE %:search%
    """)
    Page<Account> search( String search, Pageable pageable );
}
