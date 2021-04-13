package it.nicolalopatriello.thesis.common.spring.contexts;

import it.nicolalopatriello.thesis.common.spring.security.jwt.JwtUser;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j;

import java.util.Optional;

/**
 * This class represents the Security Context of a single REST Request.
 * When a request arrives, if it has a JWT Token it is automatically translated in JwtUser class and saved into
 * the context only for that session/thread
 */
@Log4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ThesisSecurityContext {

    private static final ThreadLocal<JwtUser> current = new ThreadLocal<>();

    public static void set(JwtUser env) {
        log.info("[ ThesisSecurityContext ] Set User " + env);
        current.set(env);
    }

    public static Optional<JwtUser> get() {
        return Optional.ofNullable(current.get());
    }

    public static void clear() {
        current.remove();
    }

}