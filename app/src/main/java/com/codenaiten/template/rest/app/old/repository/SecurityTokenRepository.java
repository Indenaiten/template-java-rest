package com.codenaiten.template.rest.app.old.repository;

import com.codenaiten.template.rest.app.old.entity.SecurityToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface SecurityTokenRepository extends JpaRepository<SecurityToken, UUID> {

    List<SecurityToken> findAllByAccount_Id( UUID id );
    List<SecurityToken> findAllByIp( String ip );
    void deleteAllByAccount_Id( UUID id );
    void deleteAllByIpInAndAccount_Id( List<String> ip, UUID accountId );
}
