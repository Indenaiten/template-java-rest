package com.codenaiten.template.rest.app.entity;

import com.codenaiten.template.rest.app.vo.user.UserId;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table( name = "token_versions" )
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class TokenVersion extends BaseEntity<UUID> {

    @Id
    private UUID id;

    @Column( name = "user_id", nullable = false )
    private UUID userId;

    @Column( name = "version", nullable = false )
    private Long version;

    @Column( name = "token_type", length = 20, nullable = false )
    private String tokenType; // "ACCESS" or "REFRESH"

    @Column( name = "expires_at", nullable = false )
    private LocalDateTime expiresAt;

    @Column( name = "is_invalidated", nullable = false )
    private Boolean isInvalidated = false;

    @Column( name = "invalidated_at" )
    private LocalDateTime invalidatedAt;

}