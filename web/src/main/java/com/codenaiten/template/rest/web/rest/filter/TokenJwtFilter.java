package com.codenaiten.template.rest.web.rest.filter;

import com.codenaiten.template.rest.app.authentication.AuthenticatedUser;
import com.codenaiten.template.rest.app.authentication.SecurityUtils;
import com.codenaiten.template.rest.app.authentication.TokenJwtManager;
import com.codenaiten.template.rest.app.entity.Account;
import com.codenaiten.template.rest.app.exception.InvalidAccessTokenException;
import com.codenaiten.template.rest.app.properties.TokenSecurityProperties;
import com.codenaiten.template.rest.app.vo.user.UserId;
import com.codenaiten.template.rest.web.rest.util.HttpRequestUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.LocaleResolver;

import java.io.IOException;
import java.util.Arrays;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class TokenJwtFilter extends OncePerRequestFilter {

    private final SecurityUtils securityUtils;
    private final TokenSecurityProperties tokenSecurityProperties;
    private final TokenJwtManager tokenJwtManager;
    private final UserDetailsService userDetailsService;
    private final LocaleResolver localeResolver;

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
        final String accessTokenName = this.tokenSecurityProperties.getAccessTokenName().toLowerCase();
        if( Objects.nonNull( authHeader ) && authHeader.startsWith( "Bearer " )) token = authHeader.substring( 7 );
        else token = Optional.ofNullable( request.getCookies() ).flatMap(cookies -> Arrays.stream( cookies )
                            .filter(cookie -> Objects.equals( accessTokenName, cookie.getName() ))
                            .map( Cookie::getValue )
                            .findFirst()).orElse( null );

        final String ip = HttpRequestUtil.getClientIp( request ).orElse( null );;
        if( Objects.isNull( SecurityContextHolder.getContext().getAuthentication() ) && Objects.nonNull( token )) {
            if( !this.tokenJwtManager.validateAccessToken( token, ip )) throw new InvalidAccessTokenException( token );
            final UserId userId = this.tokenJwtManager.getUserId( token );
            final UserDetails userDetails = this.userDetailsService.loadUserByUsername( userId.toString() );
            if( userDetails instanceof AuthenticatedUser authenticatedUser ) {
                authenticatedUser.setToken( token );
                final UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken( userDetails, null, userDetails.getAuthorities() );
                auth.setDetails( new WebAuthenticationDetailsSource().buildDetails( request ));
                SecurityContextHolder.getContext().setAuthentication( auth );
                final Account account = authenticatedUser.getAccount();
                account.getLang().map( Locale::forLanguageTag ).ifPresent(locale -> {
                    if( !locale.equals( LocaleContextHolder.getLocale() )){
                        LocaleContextHolder.setLocale( locale );
                        this.localeResolver.setLocale( request, response, locale );
                    }
                });
            }
        }

        filterChain.doFilter( request, response );
    }

// ------------------------------------------------------------------------------------------------------------------ \\

}