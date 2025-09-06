package com.codenaiten.template.rest.boot.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EntityScan( basePackages = "com.codenaiten.template.rest.app.old.entity" )
@EnableJpaRepositories( basePackages = "com.codenaiten.template.rest.app.old.repository" )
public class JpaConfig {
}
