package it.nicolalopatriello.thesis.core.service;

import io.jsonwebtoken.Claims;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

public interface JwtTokenService extends Serializable {
    String generateToken(Map<String, Object> claims, boolean isRefreshToken);

    String refreshToken(String token);

    Date getExpirationDateFromToken(String token);

    Claims getClaimsFromToken(String token);

    Boolean isTokenExpired(String token);
}
