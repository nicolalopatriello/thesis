package it.nicolalopatriello.thesis.core.aspects;

import it.nicolalopatriello.thesis.common.annotations.roles.ThesisAuthorization;
import it.nicolalopatriello.thesis.common.annotations.roles.ThesisPublicApi;
import it.nicolalopatriello.thesis.common.exception.BadRequestException;
import it.nicolalopatriello.thesis.common.exception.ForbiddenException;
import it.nicolalopatriello.thesis.common.exception.UnauthorizedException;
import it.nicolalopatriello.thesis.common.spring.contexts.ThesisSecurityContext;
import it.nicolalopatriello.thesis.common.spring.security.jwt.JwtUser;
import lombok.extern.log4j.Log4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import java.lang.reflect.Method;
import java.util.Optional;


@Component
@Aspect
@Log4j
public class ControllerAuthorizationAspect {


    @Around("execution(* it.nicolalopatriello.thesis.core.controller..*(..))")
    public Object authorize(ProceedingJoinPoint methodExecution) throws Throwable {
        log.debug("ControllerAuthorizationAspect.authorize");

        Optional<JwtUser> user = ThesisSecurityContext.get();

        Object[] params = methodParams(methodExecution);
        Signature sig = methodExecution.getSignature();
        Class clazz = sig.getDeclaringType();
        String methodName = sig.getName();

        if (!user.isPresent()) {
            log.warn("User not detected in token ");
        }
        //check for authorize
        for (Method method : clazz.getDeclaredMethods()) {
            if (method.getName().equals(methodName)) {
              if (!method.isAnnotationPresent(ThesisPublicApi.class)) {
                    if (!user.isPresent())
                        throw new UnauthorizedException();
                    checksAuthorization(method, user.orElse(null));
                }
                break;
            }
        }

        //editing method params values
        for (int i = 0; i < params.length; i++) {
            if (params[i] instanceof JwtUser) {
                if (!user.isPresent())
                    throw new BadRequestException();
                params[i] = user.get();
                break;
            }
        }

        return methodExecution.proceed(params);
    }


    private void checksAuthorization(Method method, @Nullable JwtUser user) throws ForbiddenException {
        if (method.isAnnotationPresent(ThesisAuthorization.class)) {
            if (user == null)
                throw new ForbiddenException();
        } else {
            throw new ForbiddenException();
        }
    }



    private Object[] methodParams(ProceedingJoinPoint pjp) {
        return pjp.getArgs();
    }



}
