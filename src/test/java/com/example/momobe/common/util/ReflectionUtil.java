package com.example.momobe.common.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

public interface ReflectionUtil {
    static <T> T newInstance(Class<T> c) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Constructor<T> con = c.getDeclaredConstructor();
        con.setAccessible(true);
        return con.newInstance();
    }

    static <E, V> void setField(E entity, String fieldName, V value) throws NoSuchFieldException, IllegalAccessException {
        Field field = entity.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(entity, value);
    }
}
