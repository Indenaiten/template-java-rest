package com.codenaiten.template.rest.app.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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

    @Column( name = "key" )
    private String key;
}
