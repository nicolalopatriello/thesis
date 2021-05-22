package it.nicolalopatriello.thesis.core.service;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import it.nicolalopatriello.thesis.common.utils.LoggerUtils;
import lombok.extern.log4j.Log4j;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Date;
import java.util.Map;

import static it.nicolalopatriello.thesis.core.service.JwtTokenServiceExt.CLAIM_KEY_CREATED;

@Log4j
public class JwtTokenServiceImpl implements JwtTokenService {
    LoggerUtils loggerUtils = new LoggerUtils(log);

    private final PublicKey publicKey;

    private PrivateKey privateKey;

    private Long expiration;

    public JwtTokenServiceImpl(PublicKey publicKey) {
        this(publicKey, null, null);
    }

    public JwtTokenServiceImpl(PublicKey publicKey, PrivateKey privateKey, Long expiration) {
        this.publicKey = publicKey;
        this.privateKey = privateKey;
        this.expiration = expiration;
    }

    @Override
    public String generateToken(Map<String, Object> claims, boolean isRefreshToken) {
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(generateExpirationDate(isRefreshToken))
                .signWith(SignatureAlgorithm.RS256, privateKey)
                .compact();
    }


    @Override
    public String refreshToken(String token) {
        String refreshedToken;
        try {
            final Claims claims = getClaimsFromToken(token);
            claims.put(CLAIM_KEY_CREATED, new Date());
            refreshedToken = generateToken(claims, true);
        } catch (Exception e) {
            refreshedToken = null;
        }
        return refreshedToken;
    }

    @Override
    public Date getExpirationDateFromToken(String token) {
        Date expirationDate;
        try {
            final Claims claims = getClaimsFromToken(token);
            expirationDate = claims.getExpiration();
        } catch (Exception e) {
            expirationDate = null;
        }
        return expirationDate;
    }

    @Override
    public Claims getClaimsFromToken(String token) {
        Claims claims;
        claims = Jwts.parser()
                .setSigningKey(publicKey)
                .parseClaimsJws(token)
                .getBody();
        return claims;
    }

    @Override
    public Boolean isTokenExpired(String token) {
        final Date expirationDate = getExpirationDateFromToken(token);
        log.debug("Expiration date parsed = " + expirationDate.getTime());
        return expirationDate.before(new Date());
    }

    private Date generateExpirationDate(boolean isRefreshToken) {
        return new Date(System.currentTimeMillis() + (isRefreshToken ? expiration * 2 : expiration) * 1000);
    }

}
