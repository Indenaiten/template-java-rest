package com.codenaiten.template.rest.app.repository;

import com.codenaiten.template.rest.app.entity.TokenVersion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TokenVersionRepository extends JpaRepository<TokenVersion, UUID> {

    Optional<TokenVersion> findByUserIdAndVersionAndTokenTypeAndIsInvalidatedFalse( UUID userId, Long version, String tokenType );
    
    List<TokenVersion> findByUserIdAndTokenTypeAndIsInvalidatedFalse( UUID userId, String tokenType );
    
    @Query("SELECT COALESCE(MAX(t.version), 0) FROM TokenVersion t WHERE t.userId = :userId AND t.tokenType = :tokenType")
    Optional<Long> findMaxVersionByUserIdAndTokenType( @Param("userId") UUID userId, @Param("tokenType") String tokenType );
    
    @Modifying
    @Query("UPDATE TokenVersion t SET t.isInvalidated = true, t.invalidatedAt = CURRENT_TIMESTAMP WHERE t.userId = :userId AND t.tokenType = :tokenType AND t.isInvalidated = false")
    void invalidateAllByUserIdAndTokenType( @Param("userId") UUID userId, @Param("tokenType") String tokenType );
    
    @Modifying
    @Query("UPDATE TokenVersion t SET t.isInvalidated = true, t.invalidatedAt = CURRENT_TIMESTAMP WHERE t.userId = :userId AND t.version = :version AND t.tokenType = :tokenType AND t.isInvalidated = false")
    void invalidateByUserIdAndVersionAndTokenType( @Param("userId") UUID userId, @Param("version") Long version, @Param("tokenType") String tokenType );

}