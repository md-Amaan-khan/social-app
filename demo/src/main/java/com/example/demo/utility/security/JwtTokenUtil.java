package com.example.demo.utility.security;

import com.example.demo.response.LoginResponse;
import com.example.demo.serviceImpl.security.CustomUserDetails;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenUtil {

    @Value("${epm.api.jwtSecret}")
    private String secretKey;
    @Value("${epm.api.jwtExpirationMs}")
    private int expirationTime;
    public LoginResponse generateToken(Authentication authentication) {
        CustomUserDetails userPrincipal = (CustomUserDetails) authentication.getPrincipal ( ); // Use CustomUserDetails
        String username = userPrincipal.getUsername ( ); // Get employeeId as String

        String token = Jwts.builder ( )
                .claim ( "roles", userPrincipal.getAuthorities ( ).stream ( )
                        .map ( GrantedAuthority::getAuthority )
                        .toList ( ) )
                .setSubject ( username )
                .setIssuedAt ( new Date( System.currentTimeMillis ( ) ) )
                .setExpiration ( new Date ( System.currentTimeMillis ( ) + expirationTime ) )
                .signWith ( SignatureAlgorithm.HS256, secretKey )
                .compact ( );
        // Create the LoginResponse object
        LoginResponse jwtResponse = new LoginResponse ();
        jwtResponse.setAccessToken (token);
        return jwtResponse;
    }

}
