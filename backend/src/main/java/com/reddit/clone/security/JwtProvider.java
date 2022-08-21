package com.reddit.clone.security;

import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.User;

@Service
public class JwtProvider {


    @Autowired
    private JwtEncoder jwtEncoder;
    @Value("${jwt.expiration.time}")
    private Long jwtExpirationInMils;

    public String generateToken(Authentication authentication)
    {
        User principal = (User) authentication.getPrincipal();
        return generateTokenWithUsername(principal.getUsername());
    }

    public String generateTokenWithUsername(String username) {

        JwtClaimsSet claims = JwtClaimsSet.builder()
            .issuer("self")
            .issuedAt(Instant.now())
            .expiresAt(Instant.now().plusMillis(jwtExpirationInMils))
            .subject(username)
            .claim("scope","ROLE_USER")
            .build();

            return this.jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();

       
    }

    public Long getJwtExpirationInMils()
    {
        return this.jwtExpirationInMils;
    }
    
}
