package com.codenaiten.template.rest.boot.config;

import com.codenaiten.template.rest.app.properties.SecurityProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig{

    private final SecurityProperties securityProperties;

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| BEANS |------------------------------------------------------------------------------------------------------ \\
// ------------------------------------------------------------------------------------------------------------------ \\

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        final CorsConfiguration cfg = new CorsConfiguration();

        cfg.setAllowedOrigins( this.securityProperties.getCorsAllowedOrigins());
        if( this.securityProperties.getCorsAllowedOrigins().contains( "*" ))
            cfg.setAllowedOriginPatterns( this.securityProperties.getCorsAllowedOrigins() );

        cfg.setAllowedMethods( this.securityProperties.getCorsAllowedMethods() );
        cfg.setAllowedHeaders( this.securityProperties.getCorsAllowedHeaders() );
        cfg.setExposedHeaders( this.securityProperties.getCorsExposedHeaders() );

        this.securityProperties.getCorsAllowCredentials().ifPresent( cfg::setAllowCredentials );

        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration( "/**", cfg );
        return source;
    }

    @Bean
    public SecurityFilterChain securityFilterChain( final HttpSecurity http, final CorsConfigurationSource corsConfigurationSource ) throws Exception {
        http.csrf( CsrfConfigurer::disable )
                .cors(cors -> cors.configurationSource( corsConfigurationSource ))
                .sessionManagement(sess -> sess.sessionCreationPolicy( SessionCreationPolicy.STATELESS ))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers( this.securityProperties.getIgnorePaths().toArray( new String[0] )).permitAll()
                        .anyRequest().authenticated());
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

// ------------------------------------------------------------------------------------------------------------------ \\

}

