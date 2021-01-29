package com.xlteam.socialcaption.external.utility.logger;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MethodReflector {

    private static final String TAG = "MethodReflector";

    public static Class<?> getClass(String className) {
        Class<?> keyClass = null;

        try {
            keyClass = Class.forName(className);
        } catch (ClassNotFoundException e1) {
            Log.d(TAG, className + " getClass ClassNotFoundException");
        }

        return keyClass;
    }

    public static Method getMethod(String className, String methodName, Class<?>... parameterTypes) {
        if (className == null) {
            Log.d(TAG, "getMethod() className is null");
            return null;
        }

        Class<?> keyClass = getClass(className);
        Method method = null;

        if (keyClass != null) {
            try {
                method = keyClass.getMethod(methodName, parameterTypes);
            } catch (NoSuchMethodException e) {
                Log.d(TAG, methodName + " getMethod NoSuchMethodException");
            }
        }

        return method;
    }

    public static <T> Method getMethod(Class<T> className, String methodName, Class<?>... parameterTypes) {
        if (className == null) {
            Log.d(TAG, "getMethod() className is null");
            return null;
        }

        Method method = null;

        try {
            method = className.getMethod(methodName, parameterTypes);
        } catch (NoSuchMethodException e) {
            Log.d(TAG, methodName + " getMethod NoSuchMethodException");
        }

        return method;
    }

    public static Object invokeStatic(Method method) {
        return invokeStatic(method, (Object[]) null);
    }

    public static Object invokeStatic(Method method, Object... args) {
        if (method == null) {
//            Log.logWithTrace(TAG, "method is null");
            return null;
        }

        try {
            //Log.d(TAG, method.getName() + " is called");
            return method.invoke(null, args);
        } catch (ReflectiveOperationException e) {
            Log.w(TAG, method.getName() + " invoke " + e.getClass().getName());
        }

        return null;
    }

    public static Object invokeOriginal(Object callerInstance, Method method, Object... args) {
        return invoke(callerInstance, true, method, args);
    }

    // If you want to get static field, then thisInstance should be null.
    // Or not, it should be instance.
    public static Object invoke(Object callerInstance, Method method, Object... args) {
        return invoke(callerInstance, false, method, args);
    }

    private static Object invoke(Object callerInstance, boolean allowNull, Method method, Object... args) {
        if (method == null || (!allowNull && callerInstance == null)) {
            Log.d(TAG, "method or callerInstance is null");
            return null;
        }

        try {
            Object result = method.invoke(callerInstance, args);
            Log.d(TAG, method.getName() + " is called");
            return result;
        } catch (InvocationTargetException ite) {
            Log.d(TAG, method.getName() + " invoke InvocationTargetException");
        } catch (IllegalAccessException ie) {
            Log.d(TAG, method.getName() + " invoke IllegalAccessException");
        }

        return null;
    }

    // If you want to get static field, then thisInstance should be null.
    // Or not, it should be instance.
    public static Object getInt(String className, Object callerInstance, String fieldName) {
        Class<?> keyClass = getClass(className);
        int result = 0;

        if (keyClass == null) {
            return result;
        }

        Field field = getField(keyClass, fieldName);

        if (field == null) {
            return result;
        }

        try {
            result = field.getInt(callerInstance);
        } catch (IllegalArgumentException e) {
            Log.d(TAG, fieldName + " getInt IllegalArgumentException");
        } catch (IllegalAccessException e) {
            Log.d(TAG, fieldName + " getInt IllegalAccessException");
        }

        return result;
    }

    // If you want to get static field, then thisInstance should be null.
    // Or not, it should be instance.
    public static boolean getBoolean(String className, Object callerInstance, String fieldName) {
        Class<?> keyClass = getClass(className);
        boolean result = false;

        if (keyClass == null) {
            return false;
        }

        Field field = getField(keyClass, fieldName);

        if (field == null) {
            return false;
        }

        try {
            result = field.getBoolean(callerInstance);
        } catch (IllegalArgumentException e) {
            Log.d(TAG, fieldName + " getBoolean IllegalArgumentException");
        } catch (IllegalAccessException e) {
            Log.d(TAG, fieldName + " getBoolean IllegalAccessException");
        }

        return result;
    }

    // If you want to get static field, then thisInstance should be null.
    // Or not, it should be instance.
    public static long getLong(String className, Object callerInstance, String fieldName) {
        Class<?> keyClass = getClass(className);
        long result = 0L;

        if (keyClass == null) {
            return result;
        }

        Field field = getField(keyClass, fieldName);

        if (field == null) {
            return result;
        }

        try {
            result = field.getLong(callerInstance);
        } catch (IllegalArgumentException e) {
            Log.d(TAG, fieldName + " getLong IllegalArgumentException");
        } catch (IllegalAccessException e) {
            Log.d(TAG, fieldName + " getLong IllegalAccessException");
        }

        return result;
    }

    // If you want to get static field, then thisInstance should be null.
    // Or not, it should be instance.
    public static double getDouble(String className, Object callerInstance, String fieldName) {
        Class<?> keyClass = getClass(className);
        double result = 0;

        if (keyClass == null) {
            return result;
        }

        Field field = getField(keyClass, fieldName);

        if (field == null) {
            return result;
        }

        try {
            result = field.getDouble(callerInstance);
        } catch (IllegalArgumentException e) {
            Log.d(TAG, fieldName + " getDouble IllegalArgumentException");
        } catch (IllegalAccessException e) {
            Log.d(TAG, fieldName + " getDouble IllegalAccessException");
        }

        return result;
    }

    // If you want to get static field, then thisInstance should be null.
    // Or not, it should be instance.
    public static float getFloat(String className, Object callerInstance, String fieldName) {
        Class<?> keyClass = getClass(className);
        float result = 0f;

        if (keyClass == null) {
            return result;
        }

        Field field = getField(keyClass, fieldName);

        if (field == null) {
            return result;
        }

        try {
            result = field.getFloat(callerInstance);
        } catch (IllegalArgumentException e) {
            Log.d(TAG, fieldName + " getFloat IllegalArgumentException");
        } catch (IllegalAccessException e) {
            Log.d(TAG, fieldName + " getFloat IllegalAccessException");
        }

        return result;
    }

    // If you want to get static field, then thisInstance should be null.
    // Or not, it should be instance.
    public static Object getFieldValue(String className, Object callerInstance, String fieldName) {
        return getFieldValue(getClass(className), callerInstance, fieldName);
    }

    public static <T> Object getFieldValue(Class<T> keyClass, Object callerInstance, String fieldName) {
        if (keyClass == null || callerInstance == null) {
            return null;
        }

        Field field = getField(keyClass, fieldName);
        if (field == null) {
            return null;
        }

        Object result = null;
        try {
            result = field.get(callerInstance);
        } catch (IllegalArgumentException e) {
            Log.d(TAG, fieldName + " getFieldValue IllegalArgumentException");
        } catch (IllegalAccessException e) {
            Log.d(TAG, fieldName + " getFieldValue IllegalAccessException");
        }

        return result;
    }

    public static Object getFieldValue(Field field, Object callerInstance) {
        if (field == null || callerInstance == null) {
            Log.d(TAG, "field or callerInstance is null");
            return null;
        }

        Object result = null;
        try {
            result = field.get(callerInstance);
        } catch (IllegalArgumentException e) {
            Log.d(TAG, field.getName() + " getFieldValue IllegalArgumentException");
        } catch (IllegalAccessException e) {
            Log.d(TAG, field.getName() + " getFieldValue IllegalAccessException");
        }

        return result;
    }

    public static <T> Field getField(Class<T> keyClass, String fieldName) {
        Field field = null;

        try {
            field = keyClass.getField(fieldName);
        } catch (NoSuchFieldException e) {
            Log.d(TAG, fieldName + " getField NoSuchFieldException");
        }

        return field;
    }

    public static Object newInstance(Constructor<?> constructor, Object... args) {
        if (constructor == null) {
            return null;
        }

        try {
            return constructor.newInstance(args);
        } catch (ReflectiveOperationException | IllegalArgumentException e) {
            Log.d(TAG, e.toString());
        }
        return null;
    }

    public static Constructor<?> getConstructor(Class<?> className, Class<?>... parameterTypes) {
        if (className == null) {
            return null;
        }

        Constructor<?> resultConstructor = null;
        try {
            resultConstructor = className.getConstructor(parameterTypes);
        } catch (NoSuchMethodException e) {
            Log.d(TAG, className + " getConstructor NoSuchMethodException");
        }

        return resultConstructor;
    }
}
