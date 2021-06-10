package it.nicolalopatriello.thesis.core.configuration;


import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import it.nicolalopatriello.thesis.common.exception.*;
import it.nicolalopatriello.thesis.core.utils.ResponseEntityFactory;
import lombok.extern.log4j.Log4j;
import org.hibernate.exception.SQLGrammarException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceException;
import java.nio.file.AccessDeniedException;
import java.security.InvalidParameterException;
import java.security.SignatureException;

@ControllerAdvice
@Log4j
public class ExceptionConfiguration {

    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ExceptionHandler({EntityNotFoundException.class, UsernameNotFoundException.class, NotFoundException.class})
    public void notFoundException(Exception e) {
        log.debug(e.getMessage(), e);
    }


    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    @ExceptionHandler({SignatureException.class, io.jsonwebtoken.SignatureException.class, UnsupportedJwtException.class, MalformedJwtException.class,
            ExpiredJwtException.class, JwtExpiredTokenException.class,
            BadCredentialsException.class, UnauthorizedException.class})
    public void unauthorizedException(Exception e) {
        log.debug(e.getMessage(), e);
    }

    @ResponseStatus(value = HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
    public void methodNotAllowedException(Exception e) {
        log.debug(e.getMessage(), e);
    }

    @ExceptionHandler({IllegalStateException.class})
    @ResponseBody
    public ResponseEntity<Object> illegalStateException(Exception e) {
        log.debug(e.getMessage(), e);
        return ResponseEntityFactory.internalServerError();
    }

    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    @ExceptionHandler({AccessDeniedException.class})
    public void forbiddenException(Exception e) {
        log.debug(e.getMessage(), e);
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler({DataIntegrityViolationException.class,
            BadRequestException.class, DBUpsertException.class, PersistenceException.class, SQLGrammarException.class, TransactionSystemException.class, InvalidParameterException.class,
            DuplicateEntityException.class, InvalidFormatException.class, HttpMessageNotReadableException.class, MethodArgumentNotValidException.class})
    public void badRequestException(Exception e) {
        if (log.isInfoEnabled())
            log.info(e.getMessage(), e);
    }


    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({Exception.class})
    public ResponseEntity<Object> internalServerError(Exception e) {
        log.error(e.getMessage(), e);

        if (e.getCause() != null && e.getCause().getClass().equals(SQLGrammarException.class))
            return ResponseEntityFactory.badRequest();

        return ResponseEntityFactory.internalServerError();
    }

}
