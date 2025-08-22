package com.codenaiten.template.rest.web.rest.filter;

import com.codenaiten.template.rest.app.authentication.AuthenticatedUser;
import com.codenaiten.template.rest.app.authentication.SecurityUtils;
import com.codenaiten.template.rest.app.authentication.TokenJwtManager;
import com.codenaiten.template.rest.app.properties.TokenProperties;
import com.codenaiten.template.rest.app.vo.user.UserId;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
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
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class TokenJwtFilter extends OncePerRequestFilter {

    private final SecurityUtils securityUtils;
    private final TokenProperties tokenProperties;
    private final TokenJwtManager tokenJwtManager;
    private final UserDetailsService userDetailsService;
    private final PathMatcher pathMatcher;

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| OVERRIDE METHODS |------------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    @Override
    protected boolean shouldNotFilter( final HttpServletRequest request ) {
        final String path = request.getServletPath();
        final String method = request.getMethod();
        return this.securityUtils.isPublicEndpoint( method, path );
    }

// ------------------------------------------------------------------------------------------------------------------ \\
// ---| IMPLEMENTED METHODS |---------------------------------------------------------------------------------------- \\
// ------------------------------------------------------------------------------------------------------------------ \\

    @Override
    protected void doFilterInternal( final HttpServletRequest request, final HttpServletResponse response,
                                     final FilterChain filterChain ) throws ServletException, IOException {
        final String authHeader = request.getHeader( HttpHeaders.AUTHORIZATION );
        final String token;
        final String accessTokenName = this.tokenProperties.getAccessTokenName().toLowerCase();
        if( Objects.nonNull( authHeader ) && authHeader.startsWith( "Bearer " )) token = authHeader.substring( 7 );
        else token = Optional.ofNullable( request.getCookies() ).flatMap(cookies -> Arrays.stream( cookies )
                            .filter(cookie -> Objects.equals( accessTokenName, cookie.getName() ))
                            .map( Cookie::getValue )
                            .findFirst()).orElse( null );

        if( Objects.isNull( SecurityContextHolder.getContext().getAuthentication() ) && Objects.nonNull( token ) && this.tokenJwtManager.validateAccessToken( token )) {
            final UserId userId = this.tokenJwtManager.getUserId( token );
            final UserDetails userDetails = this.userDetailsService.loadUserByUsername( userId.toString() );
            if( userDetails instanceof AuthenticatedUser authenticatedUser ) authenticatedUser.setToken( token );
            final UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken( userDetails, null, userDetails.getAuthorities() );
            auth.setDetails( new WebAuthenticationDetailsSource().buildDetails( request ));
            SecurityContextHolder.getContext().setAuthentication( auth );
        }

        filterChain.doFilter( request, response );
    }

// ------------------------------------------------------------------------------------------------------------------ \\

}

