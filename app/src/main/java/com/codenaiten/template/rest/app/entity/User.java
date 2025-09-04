package com.codenaiten.template.rest.app.entity;

import com.codenaiten.template.rest.app.vo.user.UserName;
import com.codenaiten.template.rest.app.vo.user.UserSurname;
import com.codenaiten.template.rest.app.vo.user.UserUsername;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@Entity
@Table( name = "users" )
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class User extends BaseEntity<UUID> {

    @Id
    private UUID id;

    @OneToOne( fetch = FetchType.EAGER )
    @OnDelete( action = OnDeleteAction.SET_NULL )
    @JoinColumn( name = "image" )
    private Image image;

    @Column( name = "username", length = UserUsername.MAX_SIZE, nullable = false, unique = true )
    private String username;

    @Column( name = "name", length = UserName.MAX_SIZE, nullable = false )
    private String name;

    @Column( name = "surname", length = UserSurname.MAX_SIZE )
    private String surname;

    @Column( name = "birthdate", nullable = false )
    private LocalDate birthdate;

    @OneToOne( mappedBy = "owner", cascade = CascadeType.ALL )
    private Account account;

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| GETTERS |---------------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    /**
     * Obtiene la imagen del {@link User}.
     *
     * @return {@link Optional} con la {@link Image} que representa la imagen del {@link User} si existe,
     *         {@link Optional#empty()} en caso contrario.
     */
    public Optional<Image> getImage(){
        return Optional.ofNullable( this.image );
    }

    /**
     * Obtiene el apellido del {@link User}.
     *
     * @return {@link Optional} con el {@link String} que representa el apellido del {@link User} si existe,
     *         {@link Optional#empty()} en caso contrario.
     */
    public Optional<String> getSurname(){
        return Optional.ofNullable( this.surname );
    }

// ------------------------------------------------------------------------------------------------------------------ \\

}
