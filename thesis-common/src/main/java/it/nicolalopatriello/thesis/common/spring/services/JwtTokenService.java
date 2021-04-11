package it.nicolalopatriello.thesis.common.spring.services;

import io.jsonwebtoken.Claims;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/**
 * Created by Greta Sasso <greta.sasso@gfmintegration.it> on 1/16/20.
 */
public interface JwtTokenService extends Serializable {
    String generateToken(Map<String, Object> claims, boolean isRefreshToken);

    String refreshToken(String token);

    Date getExpirationDateFromToken(String token);

    Claims getClaimsFromToken(String token);

    Boolean isTokenExpired(String token);
}
