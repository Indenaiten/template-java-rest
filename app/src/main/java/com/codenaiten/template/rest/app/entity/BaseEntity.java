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

    @EqualsAndHashCode.Include
    abstract T getId();

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| EVENT |------------------------------------------------------------------------------------------------------ \\
// ------------------------------------------------------------------------------------------------------------------ \\

    @PrePersist
    public void prePersist() {
        this.createdAt = Optional.ofNullable( this.createdAt ).orElse( LocalDateTime.now() );
        this.updatedAt = Optional.ofNullable( this.updatedAt ).orElse( this.createdAt );
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = Optional.ofNullable( this.updatedAt ).orElse( LocalDateTime.now() );
    }

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| OVERRIDE METHODS |------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    @Override
    public String toString() {
        return this.getId().toString();
    }

// ------------------------------------------------------------------------------------------------------------------ \\

}
