package com.codenaiten.template.rest.app.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Optional;

@MappedSuperclass
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode
public abstract class BaseEntity<T extends Serializable> {

    @Column( name = "created_at", nullable = false, updatable = false )
    protected LocalDateTime createdAt;

    @Column( name = "updated_at", nullable = false )
    protected LocalDateTime updatedAt;

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| GETTERS |---------------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    /**
     * Obtiene el identificador de la {@link BaseEntity}.
     *
     * @return {@link T} que representa el identificador de la {@link BaseEntity}.
     */
    @EqualsAndHashCode.Include
    abstract T getId();

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| EVENT |------------------------------------------------------------------------------------------------------ \\
// ------------------------------------------------------------------------------------------------------------------ \\

    /**
     * Lógica que se ejecuta antes de persistir la entidad.
     */
    @PrePersist
    public void prePersist() {
        this.createdAt = Optional.ofNullable( this.createdAt ).orElse( LocalDateTime.now() );
        this.updatedAt = Optional.ofNullable( this.updatedAt ).orElse( this.createdAt );
    }

    /**
     * Lógica que se ejecuta antes de actualizar la entidad.
     */
    @PreUpdate
    public void preUpdate() {
        this.updatedAt = Optional.ofNullable( this.updatedAt ).orElse( LocalDateTime.now() );
    }

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| OVERRIDE METHODS |------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    /**
     * Obtiene el identificador {@link T} de la {@link BaseEntity} en formato {@link String}.
     *
     * @return {@link String} con el identificador {@link T} de la {@link BaseEntity}.
     */
    @Override
    public String toString() {
        return this.getId().toString();
    }

// ------------------------------------------------------------------------------------------------------------------ \\

}
