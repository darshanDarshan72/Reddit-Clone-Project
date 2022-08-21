package com.reddit.clone.service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.reddit.clone.dto.AuthenticationResponse;
import com.reddit.clone.dto.LoginRequest;
import com.reddit.clone.dto.RefreshTokenRequest;
import com.reddit.clone.dto.RegisterRequest;
import com.reddit.clone.exception.SpringRedditException;
import com.reddit.clone.model.NotificationEmail;
import com.reddit.clone.model.User;
import com.reddit.clone.model.VerificationToken;
import com.reddit.clone.repository.UserRepository;
import com.reddit.clone.repository.VerificationTokenRepository;
import com.reddit.clone.security.JwtProvider;

@Service
@Transactional
public class AuthService {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserRepository userRepository;

    @Autowired
    VerificationTokenRepository verificationTokenRepository;

    @Autowired
    MailService mailService;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtProvider jwtProvider;

    @Autowired
    RefreshTokenService refreshTokenService;

    public void signup(RegisterRequest registerRequest) {
        User user = new User();

        user.setEmail(registerRequest.getEmail());
        user.setUsername(registerRequest.getUsername());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setCreated(Instant.now());
        user.setRole("ROLE_USER");
        user.setEnabled(false);

        this.userRepository.save(user);

        String token = generateVerficationToken(user);
        this.mailService.sendMail(new NotificationEmail("Please Activate Your Account",user.getEmail(),"Thank you for signing up to Spring Reddit, " +
        "please click on the below url to activate your account : " +
        "http://localhost:8080/api/auth/accountVerification/" + token));
    }


    private String generateVerficationToken(User user)
    {
    
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(token);
        verificationToken.setUser(user);

        this.verificationTokenRepository.save(verificationToken);
        return token;
    }


    public void verifyAccount(String token) {

        Optional<VerificationToken> verificationToken =  this.verificationTokenRepository.findByToken(token);
        this.fetchUserAndEnable(verificationToken.orElseThrow(()-> new SpringRedditException("Invalid Token")));
    }


    private void fetchUserAndEnable(VerificationToken verificationToken) {
        String username = verificationToken.getUser().getUsername();
        User user = this.userRepository.findByUsername(username).orElseThrow(()-> new SpringRedditException("User Not Found With Username " + username));
        user.setEnabled(true);
        this.userRepository.save(user);
    }


    public AuthenticationResponse login(LoginRequest loginRequest) {
        Authentication authentication =  this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = this.jwtProvider.generateToken(authentication);

        return AuthenticationResponse.builder()
                    .authenticationToken(token)
                    .username(loginRequest.getUsername())
                    .refreshToken(this.refreshTokenService.generateRefreshToken().getToken())
                    .expiresAt(Instant.now().plusMillis(jwtProvider.getJwtExpirationInMils()))
                    .build();
        
        // return new AuthenticationResponse(token,loginRequest.getUsername());
    }

    @Transactional(readOnly = true)
    public User getCurrentUser() {
        Jwt principal = (Jwt) SecurityContextHolder.
                getContext().getAuthentication().getPrincipal();
        return userRepository.findByUsername(principal.getSubject())
                .orElseThrow(() -> new UsernameNotFoundException("User name not found - " + principal.getSubject()));
    }


    // public AuthenticationResponse getRefreshToken(RefreshTokenRequest refreshTokenRequest) {

    //     this.refreshTokenService.validateRefreshToken(refreshTokenRequest.getRefreshToken());
    //     String token = jwtProvider.generateTokenWithUsername(refreshTokenRequest.getUsername());

    //     return AuthenticationResponse.builder()
    //             .authenticationToken(token)
    //             .username(refreshTokenRequest.getUsername())
    //             .expiresAt(Instant.now().plusMillis(this.jwtProvider.getJwtExpirationInMils()))
    //             .refreshToken(refreshTokenRequest.getRefreshToken())
    //             .build();
       
    // }


    public AuthenticationResponse refreshToken(@Valid RefreshTokenRequest refreshTokenRequest) {
        this.refreshTokenService.validateRefreshToken(refreshTokenRequest.getRefreshToken());
        String token = jwtProvider.generateTokenWithUsername(refreshTokenRequest.getUsername());
        return AuthenticationResponse.builder()
        .authenticationToken(token)
        .refreshToken(refreshTokenRequest.getRefreshToken())
        .expiresAt(Instant.now().plusMillis(jwtProvider.getJwtExpirationInMils()))
        .username(refreshTokenRequest.getUsername())
        .build();
    }
    
}
