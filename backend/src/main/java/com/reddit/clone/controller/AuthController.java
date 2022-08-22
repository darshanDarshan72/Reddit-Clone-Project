package com.reddit.clone.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.web.server.ServerHttpSecurity.HttpsRedirectSpec;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.reddit.clone.dto.AuthenticationResponse;
import com.reddit.clone.dto.LoginRequest;
import com.reddit.clone.dto.RefreshTokenRequest;
import com.reddit.clone.dto.RegisterRequest;
import com.reddit.clone.service.AuthService;
import com.reddit.clone.service.RefreshTokenService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthService authService;

    @Autowired
    RefreshTokenService refreshTokenService;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody RegisterRequest registerRequest)
    {
        this.authService.signup(registerRequest);
        return new ResponseEntity<>("User Registeration is Successful",HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody LoginRequest loginRequest)
    {
        return ResponseEntity.status(HttpStatus.OK).body(this.authService.login(loginRequest));
    }

    @GetMapping("/accountVerification/{token}")
    public ResponseEntity<String> verifyAccount(@PathVariable String token)
    {
        this.authService.verifyAccount(token);
        return new ResponseEntity<>("Account Surressfully Activated",HttpStatus.OK);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout( @Valid @RequestBody RefreshTokenRequest refreshTokenRequest)
    {
        this.refreshTokenService.deleteRefreshToken(refreshTokenRequest.getRefreshToken());
        return new ResponseEntity<>("Refresh Token deleted Successfully",HttpStatus.OK);
    }

    @PostMapping("/refresh/token")
    public ResponseEntity<AuthenticationResponse> refreshTokens(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest)
    {
        return ResponseEntity.status(HttpStatus.OK).body(this.authService.refreshToken(refreshTokenRequest));
    }

    
}
