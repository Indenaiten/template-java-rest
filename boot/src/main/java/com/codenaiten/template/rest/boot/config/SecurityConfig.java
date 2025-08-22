package com.codenaiten.template.rest.boot.config;

import com.codenaiten.template.rest.app.properties.SecurityProperties;
import com.codenaiten.template.rest.web.rest.filter.TokenJwtFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.context.NullSecurityContextRepository;
import org.springframework.security.web.savedrequest.NullRequestCache;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig{

    private final SecurityProperties securityProperties;
    private final TokenJwtFilter tokenJwtFilter;

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| BEANS |------------------------------------------------------------------------------------------------------ \\
// ------------------------------------------------------------------------------------------------------------------ \\

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        final CorsConfiguration cfg = new CorsConfiguration();

        if( this.securityProperties.getCorsAllowedOrigins().contains( "*" ))
            cfg.setAllowedOriginPatterns( this.securityProperties.getCorsAllowedOrigins() );
        else cfg.setAllowedOrigins( this.securityProperties.getCorsAllowedOrigins());

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
        http.csrf( CsrfConfigurer::disable ) // Disable CSRF
                // CORS Config
                .cors( cors -> cors.configurationSource( corsConfigurationSource ))
                // Set session management to stateless
                .sessionManagement( sm -> sm.sessionCreationPolicy( SessionCreationPolicy.STATELESS ))
                // Not save any SecurityContext in the HTTP Session
                .securityContext( sc -> sc.securityContextRepository( new NullSecurityContextRepository() ))
                // Not save requests to redirections in cache
                .requestCache( rc -> rc.requestCache( new NullRequestCache() ))
                // Disable mechanisms of authentication with state
                .formLogin( AbstractHttpConfigurer::disable )
                .httpBasic( AbstractHttpConfigurer::disable )
                .logout( AbstractHttpConfigurer::disable )
                .rememberMe( AbstractHttpConfigurer::disable )
                // Secured Endpoints Config
                .authorizeHttpRequests(auth -> auth
                        // Set public paths
                        // TODO: Establecer los endpoints libres (ignore-paths) también estableciendo el método HTTP
                        .requestMatchers( this.securityProperties.getIgnorePaths().toArray( new String[0] )).permitAll()
                        .anyRequest().authenticated()) // Set all other paths to authenticated
                // Set JWT Filter
                .addFilterBefore( this.tokenJwtFilter, UsernamePasswordAuthenticationFilter.class );
        return http.build();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthProvider( final UserDetailsService userDetailsService, final PasswordEncoder passwordEncoder ){
        final DaoAuthenticationProvider provider = new DaoAuthenticationProvider( userDetailsService );
        provider.setPasswordEncoder( passwordEncoder );
        return provider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager( final AuthenticationConfiguration config ) throws Exception {
        return config.getAuthenticationManager();
    }

// ------------------------------------------------------------------------------------------------------------------ \\

}

