package com.codenaiten.template.rest.app.entity;

import com.codenaiten.template.rest.app.vo.Email;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.UUID;

@Entity
@Table( name = "accounts" )
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class Account extends BaseEntity<UUID> {

    @Id
    private UUID id;

    @OneToOne( fetch = FetchType.EAGER, optional = false )
    @JoinColumn( name = "owner", nullable = false, unique = true )
    private User owner;

    @Column( name = "email", length = Email.MAX_SIZE, nullable = false, unique = true )
    private String email;

    @Column( name = "password", length = 256, nullable = false )
    private String password;

    @OneToMany( mappedBy = "account", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true )
    private List<SecurityToken> securityTokens;

}
