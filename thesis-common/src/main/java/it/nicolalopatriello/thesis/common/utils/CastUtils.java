package it.nicolalopatriello.thesis.common.utils;

/**
 * Created by Greta Sasso <greta.sasso@gfmintegration.it> on 4/6/20.
 */


import it.nicolalopatriello.thesis.common.exception.CastUtilsException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CastUtils {

    private static final String METHOD_REGEX = "get[A-Z]+.*";

    /**
     * This method allows to obtain upcast. It requires empty constructor and set/get methods in involved objects
     *
     * @param a
     * @param target
     * @param <A>
     * @param <T>
     * @return
     * @throws CastUtilsException
     */
    public static <A, T extends A> T upcast(A a, Class<T> target) throws CastUtilsException {
        try {
            Class<? extends Object> aClass = a.getClass();
            Set<Method> aMethods = extractGetterMethods(aClass);
            T instance = target.getConstructor().newInstance();
            for (Method m : aMethods) {
                Method setterMethod = findSetterMethod(m, target);
                setterMethod.setAccessible(true);//NOSONAR
                m.setAccessible(true);//NOSONAR
                setterMethod.invoke(instance, m.invoke(a));
            }
            return instance;
        } catch (Exception e) {
            throw new CastUtilsException(e);
        }
    }

    /**
     * This method allows to obtain downcast. It requires empty constructor and set/get methods in involved objects
     *
     * @param a
     * @param target
     * @param <T>
     * @param <A>
     * @return
     * @throws CastUtilsException
     */
    public static <T, A extends T> T downcast(A a, Class<T> target) throws CastUtilsException {
        try {
            Class<? extends Object> aClass = a.getClass();
            Set<Method> targetMethods = extractGetterMethods(target);
            T instance = target.getConstructor().newInstance();
            for (Method m : targetMethods) {
                Method setterMethod = findSetterMethod(m, aClass);
                setterMethod.setAccessible(true);//NOSONAR
                m.setAccessible(true);//NOSONAR
                setterMethod.invoke(instance, m.invoke(a));
            }
            return instance;
        } catch (Exception e) {
            throw new CastUtilsException(e);
        }
    }

    private static Set<Method> extractGetterMethods(Class<?> aClass) {
        return Arrays.stream(aClass.getMethods())
                .filter(CastUtils::getterMethod)
                .filter(CastUtils::noObjectMethod)
                .collect(Collectors.toSet());
    }

    private static boolean getterMethod(Method x) {
        return x.getName().matches(METHOD_REGEX); //NOSONAR
    }

    private static boolean noObjectMethod(Method m) {
        return !objectMethod(m);
    }

    private static boolean objectMethod(Method m) {
        try {
            Object.class.getMethod(m.getName(), m.getParameterTypes());
        } catch (NoSuchMethodException e) {
            return false;
        }
        return true;
    }

    private static Method findSetterMethod(Method getterMethod, Class<?> target) throws CastUtilsException {
        Class<?> returnType = getterMethod.getReturnType();
        String setterName = String.format("set%s", getterMethod.getName().substring(3));
        try {
            return target.getMethod(setterName, returnType);
        } catch (NoSuchMethodException e) {
            throw new CastUtilsException(e);
        }
    }


    public static <E> E cloneObject(E old, Class<E> clazz) throws CastUtilsException {
        return upcast(old, clazz);
    }
}