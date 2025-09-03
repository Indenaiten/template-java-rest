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

    /**
     * Comprueba si un {@link User} es propietario de la @{link Account}.
     *
     * @param user {@link User} que se va a comprobar si es propietario.
     *
     * @return {@code true} si el {@link User} es propietario de la @{link Account}, {@code false} en caso contrario.
     */
    public boolean isOwner( final User user ){
        return Objects.equals( this.owner, user );
    }

    /**
     * Comprueba si un {@link Account} tiene un determinado {@link AccountRole}.
     *
     * @param role {@link AccountRole} que se va a comprobar si tiene.
     *
     * @return {@code true} si el {@link Account} tiene el {@link AccountRole} dado, {@code false} en caso contrario.
     */
    public boolean hasRole( final AccountRole role ){
        return Objects.equals( this.role, role.value() );
    }

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| GETTERS |---------------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    /**
     * Obtiene el idioma de la @{link Account}.
     *
     * @return {@link Optional} con el {@link String} que representa el idioma de la @{link Account} si existe,
     *         {@link Optional#empty()} en caso contrario.
     */
    public Optional<String> getLang(){
        return Optional.ofNullable( this.lang );
    }

// ------------------------------------------------------------------------------------------------------------------ \\

}
