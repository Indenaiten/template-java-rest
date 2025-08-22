package com.codenaiten.template.rest.web.rest.filter;

import com.codenaiten.template.rest.app.authentication.TokenJwtManager;
import com.codenaiten.template.rest.app.properties.SecurityProperties;
import com.codenaiten.template.rest.app.vo.user.UserId;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.PathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;

@Slf4j
@Component
@RequiredArgsConstructor
public class TokenJwtFilter extends OncePerRequestFilter {

    private final SecurityProperties securityProperties;
    private final TokenJwtManager tokenJwtManager;
    private final UserDetailsService userDetailsService;
    private final PathMatcher pathMatcher;

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| OVERRIDE METHODS |------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    @Override
    protected boolean shouldNotFilter( final HttpServletRequest request ){
        // TODO: Establecer los endpoints libres (ignore-paths) también estableciendo el método HTTP
        final String path = request.getServletPath();
        //final String method = request.getMethod();
        return this.securityProperties.getIgnorePaths().stream().anyMatch( target -> pathMatcher.match( target, path ));
    }

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| IMPLEMENTED METHODS |---------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    @Override
    protected void doFilterInternal( final HttpServletRequest request, final HttpServletResponse response,
                                     final FilterChain filterChain ) throws ServletException, IOException {
        final String authHeader = request.getHeader( HttpHeaders.AUTHORIZATION );
        if( Objects.nonNull( authHeader ) && authHeader.startsWith( "Bearer " )){
            final String token = authHeader.substring( 7 );
            final UserId id = this.tokenJwtManager.getSubject( token );

            if( Objects.nonNull( id ) && Objects.isNull( SecurityContextHolder.getContext().getAuthentication() )){
                final UserDetails userDetails = this.userDetailsService.loadUserByUsername( id.toString() );
                if( this.tokenJwtManager.check( token )) {
                    final UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken( userDetails, null, userDetails.getAuthorities() );
                    auth.setDetails( new WebAuthenticationDetailsSource().buildDetails( request ));
                    SecurityContextHolder.getContext().setAuthentication( auth );
                }
            }
        }

        filterChain.doFilter( request, response );
    }

// ------------------------------------------------------------------------------------------------------------------ \\

}

