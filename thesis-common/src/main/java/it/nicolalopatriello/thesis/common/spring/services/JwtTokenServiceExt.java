package it.nicolalopatriello.thesis.common.spring.services;


import io.jsonwebtoken.Claims;
import it.nicolalopatriello.thesis.common.exception.JwtExpiredTokenException;
import it.nicolalopatriello.thesis.common.spring.security.jwt.JwtUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service

public class JwtTokenServiceExt {

    static final String CLAIM_KEY_USERNAME = "sub";
    static final String CLAIM_KEY_AUTHORITIES = "role";
    static final String CLAIM_KEY_IS_ENABLED = "isEnabled";
    static final String CLAIM_KEY_IS_CHANGE_PSW_REQUIRED = "isChangePswRequired";
    static final String CLAIM_KEY_CREATED = "iat";

    @Autowired
    JwtTokenService jwtTokenService;

    public String generateUserToken(JwtUser userDetails, boolean isRefreshToken) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(CLAIM_KEY_USERNAME, userDetails.getUsername());
        claims.put(CLAIM_KEY_CREATED, new Date());
        claims.put(CLAIM_KEY_AUTHORITIES, userDetails.getRole());
        claims.put(CLAIM_KEY_IS_ENABLED, userDetails.isEnabled());
        claims.put(CLAIM_KEY_IS_CHANGE_PSW_REQUIRED, userDetails.isChangePasswordRequired());
        return jwtTokenService.generateToken(claims, isRefreshToken);
    }

    public JwtTokenService getJwtTokenService() {
        return jwtTokenService;
    }

    public String getUsernameFromToken(String token) {
        String username;
        try {
            final Claims claims = jwtTokenService.getClaimsFromToken(token);
            username = claims.getSubject();
        } catch (Exception e) {
            username = null;
        }
        return username;
    }


    public JwtUser fromUserToken(String token) throws JwtExpiredTokenException {
        return from(token, true);
    }

    private JwtUser from(String token, boolean isUserToken) throws JwtExpiredTokenException {

        if (jwtTokenService.getExpirationDateFromToken(token) == null) {
            throw new JwtExpiredTokenException("ERR", null);
        }

        final Claims claims = jwtTokenService.getClaimsFromToken(token);
        if (isUserToken && claims.getExpiration().getTime() < System.currentTimeMillis())
            throw new JwtExpiredTokenException(claims.getExpiration().toString(), null);

        String username = (String) claims.get(CLAIM_KEY_USERNAME);
        String role = (String) claims.get(CLAIM_KEY_AUTHORITIES);
        Boolean isEnabled = (Boolean) claims.get(CLAIM_KEY_IS_ENABLED);
        Boolean isChangePasswordRequired = (Boolean) claims.get(CLAIM_KEY_IS_CHANGE_PSW_REQUIRED);
        return new JwtUser(username, role, isEnabled, isChangePasswordRequired);
    }

}
