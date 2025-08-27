package com.codenaiten.template.rest.app.entity;

import com.codenaiten.template.rest.app.vo.Email;
import com.codenaiten.template.rest.app.vo.account.AccountRole;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.Objects;
import java.util.Optional;
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

    @OneToOne( fetch = FetchType.EAGER )
    @OnDelete( action = OnDeleteAction.CASCADE )
    @JoinColumn( name = "owner", nullable = false, unique = true )
    private User owner;

    @Column( name = "lang" )
    private String lang;

    @Column( name = "role", nullable = false )
    private Integer role;

    @Column( name = "email", length = Email.MAX_SIZE, nullable = false, unique = true )
    private String email;

    @Column( name = "password", length = 256, nullable = false )
    private String password;

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| CHECKERS |--------------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    public boolean isOwner( final User user ){
        return Objects.equals( this.owner, user );
    }

    public boolean hasRole( final AccountRole role ){
        return Objects.equals( this.role, role.value() );
    }

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| GETTERS |---------------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    public Optional<String> getLang(){
        return Optional.ofNullable( this.lang );
    }

// ------------------------------------------------------------------------------------------------------------------ \\

}
