package it.nicolalopatriello.thesis.core.security;

import lombok.extern.log4j.Log4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.security.SecureRandom;

@Log4j
public class ThesisBCryptPasswordEncoder extends BCryptPasswordEncoder {

    private static final SecureRandom random = new SecureRandom();

    public ThesisBCryptPasswordEncoder(int strength) {
        super(validateStrength(strength), secureRandom());
    }

    private static SecureRandom secureRandom() {
        byte[] seed = new byte[16];
        random.nextBytes(seed);
        return new SecureRandom(seed);
    }

    private static int validateStrength(int i) {
        if (i < 12)
            log.warn("BCrypt strength is lower than 12 (current=" + i + "). It will replaced with 12");
        return Math.max(i, 12);
    }
}
