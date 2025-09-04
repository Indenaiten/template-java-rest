package com.codenaiten.template.rest.app.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

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

    @Column( name = "content_type", nullable = false )
    private String contentType;
}
