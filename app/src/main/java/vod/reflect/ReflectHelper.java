package vod.reflect;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Reflect Support.
 */
public class ReflectHelper {
    public static Object invokeMethod(Object invokeObject, String methodName, Class<?>[] methodArgsClass, String[] methodArgs) {
        return invokeMethod(invokeObject, invokeObject.getClass().getSuperclass(), methodName, methodArgsClass, methodArgs);
    }

    public static Object invokeMethod(Object invokeObject, Class invokeClass, String methodName, Class<?>[] methodArgsClass, String[] methodArgs) {
        try {
            Method method = invokeClass.getDeclaredMethod(methodName, methodArgsClass);
            method.setAccessible(true);
            return method.invoke(invokeObject, methodArgs);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Object getValue(Object invokeObject, String fieldName) {
        return getValue(invokeObject, invokeObject.getClass().getSuperclass(), fieldName);
    }

    public static Object getValue(Object invokeObject, Class invokeClass, String fieldName) {
        try {
            Field field = invokeClass.getDeclaredField(fieldName);
            field.setAccessible(true);
            return field.get(invokeObject);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
}
