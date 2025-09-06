package com.codenaiten.template.rest.app.old.repository;

import com.codenaiten.template.rest.app.old.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ImageRepository extends JpaRepository<Image, UUID> {
}
