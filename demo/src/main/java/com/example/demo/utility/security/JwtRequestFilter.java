package com.example.demo.utility.security;

import com.example.demo.serviceImpl.security.CustomUserDetailService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@Component
public class JwtRequestFilter extends OncePerRequestFilter {
    private final CustomUserDetailService customEmployeeDetails;
    @Value("${epm.api.jwtSecret}")
    private String secretKey; // This should match your JWT signing key

    @Autowired
    public JwtRequestFilter(CustomUserDetailService customEmployeeDetails) {
        this.customEmployeeDetails = customEmployeeDetails;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        final String authorizationHeader = request.getHeader ( "Authorization" );

        String username = null;
        String jwt;

        // Check if the Authorization header is present and starts with "Bearer "
        if ( authorizationHeader != null && authorizationHeader.startsWith ( "Bearer " ) ) {
            jwt = authorizationHeader.substring ( 7 ); // Extract the token
            try {
                Claims claims = Jwts.parser ( )
                        .setSigningKey ( secretKey ) // Use the secret key for validation
                        .parseClaimsJws ( jwt ) // Validate the token
                        .getBody ( );
                username = claims.getSubject ( ) ; // Extract username from claims
            }
            catch ( ExpiredJwtException e ) {
                // Handle expired token
            }
            catch ( Exception e ) {
                // Handle other exceptions
            }
        }

        // If username is present in the token, set the authentication context
        if ( username != null && SecurityContextHolder.getContext ( ).getAuthentication ( ) == null ) {
            UserDetails userDetails = this.customEmployeeDetails.loadUserByUsername ( username  ); // Use username as String

            // Create an AuthenticationToken and set it in the Security Context
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken ( userDetails, null, userDetails.getAuthorities ( ) );
            authentication.setDetails ( new WebAuthenticationDetailsSource( ).buildDetails ( request ) );
            SecurityContextHolder.getContext ( ).setAuthentication ( authentication ); // Set the authentication in context
        }

        chain.doFilter ( request, response ); // Continue the filter chain
    }
}
