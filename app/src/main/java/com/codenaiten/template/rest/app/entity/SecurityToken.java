package com.codenaiten.template.rest.app.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.Optional;
import java.util.UUID;

@Entity
@Table( name = "security_tokens" )
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class SecurityToken extends BaseEntity<UUID> {

    @Id
    private UUID id;

    @Column( name = "version", nullable = false, unique = true )
    private UUID version;

    @ManyToOne( fetch = FetchType.LAZY, optional = false )
    @OnDelete( action = OnDeleteAction.CASCADE )
    @JoinColumn( name = "account", nullable = false )
    private Account account;

    @Column( name = "ip", length = 256 )
    private String ip;

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| GETTERS |---------------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    public Optional<String> getIp(){
        return Optional.ofNullable( this.ip );
    }

// ------------------------------------------------------------------------------------------------------------------ \\

}
