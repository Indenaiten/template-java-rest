package com.codenaiten.template.rest.app.old.repository;

import com.codenaiten.template.rest.app.old.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    boolean existsByUsername( String username );

    @Query("""
        SELECT usr 
        FROM User usr  
        WHERE usr.username LIKE %:search% OR 
              usr.name LIKE %:search% OR 
              usr.surname LIKE %:search%
    """)
    Page<User> search( String search, Pageable pageable );
}
