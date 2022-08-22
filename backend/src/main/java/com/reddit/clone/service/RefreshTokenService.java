package com.reddit.clone.service;

import java.time.Instant;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.reddit.clone.exception.SpringRedditException;
import com.reddit.clone.model.RefreshToken;
import com.reddit.clone.repository.RefreshTokenRepository;

@Service
@Transactional
public class RefreshTokenService {

    @Autowired
    RefreshTokenRepository refreshTokenRepository;

    public RefreshToken generateRefreshToken()
    {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setCreatedDate(Instant.now());

        return this.refreshTokenRepository.save(refreshToken);
    }

    public void validateRefreshToken(String token) {
        this.refreshTokenRepository.findByToken(token).orElseThrow(()-> new SpringRedditException("Invalid Refresh Token"));
    }

    public void deleteRefreshToken(String token)
    {
        this.refreshTokenRepository.deleteByToken(token);
    }
    
}
