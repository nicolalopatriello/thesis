package it.nicolalopatriello.thesis.common.annotations.roles;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ThesisAuthorization {
    boolean superUser() default false;

    boolean develop() default false;

    boolean read() default false;

    boolean readReport() default false;

    boolean execute() default false;

    boolean shared() default false;
}