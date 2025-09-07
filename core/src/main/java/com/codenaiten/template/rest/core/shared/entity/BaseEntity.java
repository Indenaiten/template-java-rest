package com.codenaiten.template.rest.core.shared.entity;

import com.codenaiten.template.rest.core.shared.vo.common.Timestamp;
import lombok.Getter;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;

@Getter
public abstract class BaseEntity<T extends Serializable> implements Entity<T>{

    protected T id;
    protected Timestamp createdAt;
    protected Timestamp updatedAt;

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| CONSTRUCTOR |------------------------------------------------------------------------------------------------ \\
// ------------------------------------------------------------------------------------------------------------------ \\

    protected BaseEntity( final T id, final Timestamp createdAt, final Timestamp updatedAt ){
        this.setId( id );
        this.setCreatedAt( createdAt );
        this.setUpdatedAt( updatedAt );
    }

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| SETTERS |---------------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    protected void setId( final T id ){
        if( Objects.isNull( id )) throw new IllegalArgumentException( "Id is required" );
        this.id = id;
    }

    protected void setCreatedAt( final Timestamp createdAt ){
        if( Objects.isNull( createdAt )) throw new IllegalArgumentException( "CreatedAt is required" );
        this.createdAt = createdAt;
    }

    public void setUpdatedAt( final Timestamp updatedAt ){
        if( Objects.isNull( updatedAt )) throw new IllegalArgumentException( "UpdatedAt is required" );
        this.updatedAt = updatedAt;
    }

    public void setUpdatedAt(){
        this.setUpdatedAt( Timestamp.now() );
    }

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| GETTERS |---------------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    @Override
    public Optional<Timestamp> getUpdatedAt(){
        return Optional.ofNullable( this.updatedAt );
    }

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| COMPARISON METHODS |----------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    @Override
    public boolean equals( final Object obj ){
        if( Objects.isNull( obj ) || getClass() != obj.getClass() ) return false;
        final Entity<T> that = (Entity<T>) obj;
        return Objects.equals( this.getId(), that.getId() );
    }

    @Override
    public int hashCode(){
        return Objects.hashCode( this.id );
    }

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| TO STRING |-------------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    @Override
    public String toString() {
        return String.valueOf( this.id );
    }

// ------------------------------------------------------------------------------------------------------------------ \\

}
