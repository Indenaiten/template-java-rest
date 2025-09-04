package com.codenaiten.template.rest.app.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.Objects;
import java.util.UUID;

@Entity
@Table( name = "images" )
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class Image extends BaseEntity<UUID> {

    @Id
    private UUID id;

    @OneToOne( fetch = FetchType.LAZY )
    @OnDelete( action = OnDeleteAction.CASCADE )
    @JoinColumn( name = "owner", nullable = false )
    private User owner;

    @Column( name = "content_type", nullable = false )
    private String contentType;

    @Column( name = "size", nullable = false )
    private Long size;

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| CHECKERS |--------------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    /**
     * Comprueba si un {@link User} es propietario de la {@link Image}.
     *
     * @param user {@link User} que se va a comprobar si es propietario.
     *
     * @return {@code true} si el {@link User} es propietario de la {@link Image}, {@code false} en caso contrario.
     */
    public boolean isOwner( final User user ){
        return Objects.equals( this.owner, user );
    }

// ------------------------------------------------------------------------------------------------------------------ \\

}
