package mc.replay.api.utils;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.*;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public final class JavaReflections {

    private static final MethodType VOID_METHOD_TYPE = MethodType.methodType(void.class);
    private static volatile Object theUnsafe;

    public static Class<?> getClass(String className) throws ClassNotFoundException {
        return Class.forName(className);
    }

    public static Optional<Class<?>> getClassOptional(String className) {
        try {
            return Optional.of(Class.forName(className));
        } catch (ClassNotFoundException e) {
            return Optional.empty();
        }
    }

    public static Object getEnumValueOf(Class<?> enumClass, String enumName) {
        return Enum.valueOf(enumClass.asSubclass(Enum.class), enumName);
    }

    public static Object getEnumValueOf(Class<?> enumClass, String enumName, int fallbackOrdinal) {
        try {
            return getEnumValueOf(enumClass, enumName);
        } catch (IllegalArgumentException e) {
            Object[] constants = enumClass.getEnumConstants();
            if (constants.length > fallbackOrdinal) {
                return constants[fallbackOrdinal];
            }
            throw e;
        }
    }

    public static ConstructorInvoker getConstructor(Class<?> clazz, Class<?>... params) {
        for (Constructor<?> constructor : clazz.getDeclaredConstructors()) {
            if (Arrays.equals(constructor.getParameterTypes(), params)) {
                constructor.setAccessible(true);
                return arguments -> {
                    try {
                        return constructor.newInstance(arguments);
                    } catch (Exception e) {
                        throw new RuntimeException("Cannot invoke constructor " + constructor, e);
                    }
                };
            }
        }
        throw new IllegalStateException(String.format("Unable to find constructor for %s (%s).", clazz, List.of(params)));
    }

    public static <T> FieldAccessor<T> getField(Class<?> clazz, String name, Class<T> fieldType) {
        return getField(clazz, name, fieldType, 0);
    }

    public static <T> FieldAccessor<T> getField(Class<?> clazz, Class<T> fieldType, int index) {
        return getField(clazz, null, fieldType, index);
    }

    private static <T> FieldAccessor<T> getField(Class<?> clazz, String name, Class<T> fieldType, int index) {
        for (Field field : clazz.getDeclaredFields()) {
            if ((name == null || field.getName().equals(name)) && fieldType.isAssignableFrom(field.getType()) && index-- <= 0) {
                field.setAccessible(true);
                return new FieldAccessor<T>() {
                    public T get(Object target) {
                        try {
                            return (T) field.get(target);
                        } catch (IllegalAccessException e) {
                            throw new RuntimeException("Cannot access reflection.", e);
                        }
                    }

                    public void set(Object target, Object value) {
                        try {
                            field.set(target, value);
                        } catch (IllegalAccessException e) {
                            throw new RuntimeException("Cannot access reflection.", e);
                        }
                    }

                    public boolean hasField(Object target) {
                        return field.getDeclaringClass().isAssignableFrom(target.getClass());
                    }
                };
            }
        }
        if (clazz.getSuperclass() != null) return getField(clazz.getSuperclass(), name, fieldType, index);
        throw new IllegalArgumentException("Cannot find field with type " + fieldType);
    }

    public static <T> FieldAccessor<T> getField(Class<?> clazz, Class<?> returnType, String... fieldNames) {
        Field field;

        if (clazz != null) {
            try {
                for (String fieldName : fieldNames) {
                    field = clazz.getDeclaredField(fieldName);
                    if (returnType == null || returnType.isAssignableFrom(field.getType())) {
                        field.setAccessible(true);

                        Field finalField = field;
                        return new FieldAccessor<T>() {
                            public T get(Object target) {
                                try {
                                    return (T) finalField.get(target);
                                } catch (IllegalAccessException e) {
                                    throw new RuntimeException("Cannot access reflection.", e);
                                }
                            }

                            public void set(Object target, Object value) {
                                try {
                                    finalField.set(target, value);
                                } catch (IllegalAccessException e) {
                                    throw new RuntimeException("Cannot access reflection.", e);
                                }
                            }

                            public boolean hasField(Object target) {
                                return finalField.getDeclaringClass().isAssignableFrom(target.getClass());
                            }
                        };
                    }
                }
            } catch (Exception ignored) {
            }
        }

        throw new IllegalArgumentException("Cannot find field with names " + Arrays.toString(fieldNames));
    }

    public static MethodInvoker getMethod(Class<?> clazz, String methodName, Class<?>... params) {
        return getTypedMethod(clazz, methodName, null, params);
    }

    public static Method getMethodSimply(Class<?> clazz, String method) {
        for (Method m : clazz.getMethods()) {
            if (m.getName().equals(method)) return m;
        }
        return null;
    }

    public static MethodInvoker getTypedMethod(Class<?> clazz, String methodName, Class<?> returnType, Class<?>... params) {
        for (Method method : clazz.getDeclaredMethods()) {
            if (((methodName == null || method.getName().equals(methodName)) && returnType == null) || (method.getReturnType().equals(returnType) && Arrays.equals(
                    method.getParameterTypes(), params
            ))) {
                method.setAccessible(true);
                return (target, arguments) -> {
                    try {
                        return method.invoke(target, arguments);
                    } catch (Exception e) {
                        throw new RuntimeException("Cannot invoke method " + method, e);
                    }
                };
            }
        }
        if (clazz.getSuperclass() != null) return getMethod(clazz.getSuperclass(), methodName, params);
        throw new IllegalStateException(String.format("Unable to find method %s (%s).", methodName, List.of(params)));
    }

    public static <T> T newInstance(Class<T> type) {
        try {
            return type.getConstructor().newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Class<?> getInnerClass(Class<?> parentClass, Predicate<Class<?>> classPredicate) throws ClassNotFoundException {
        for (Class<?> innerClass : parentClass.getDeclaredClasses()) {
            if (classPredicate.test(innerClass)) {
                return innerClass;
            }
        }
        throw new ClassNotFoundException("No class in " + parentClass.getCanonicalName() + " matches the predicate.");
    }

    public static Field findField(Class<?> instance, Predicate<Field> predicate) throws NoSuchFieldException {
        return Arrays.stream(instance.getDeclaredFields())
                .filter(predicate)
                .findFirst()
                .orElseThrow(NoSuchFieldException::new);
    }

    public static Field findAssignableTypeField(Class<?> instance, Class<?> assignableClass) throws NoSuchFieldException {
        return findField(instance, (field) -> assignableClass.isAssignableFrom(field.getType()));
    }

    public static Field findAssignableGenericField(Class<?> instance, Class<?> assignableClass, Type... argumentTypes) throws NoSuchFieldException {
        return findField(instance, (field) -> {
            if (!assignableClass.isAssignableFrom(field.getType())) return false;
            if (!(field.getGenericType() instanceof ParameterizedType type)) return false;

            for (int i = 0; i < argumentTypes.length; i++) {
                if (type.getActualTypeArguments().length <= i) break;

                if (!type.getActualTypeArguments()[i].equals(argumentTypes[i])) {
                    return false;
                }
            }

            return true;
        });
    }

    public static PacketConstructor findPacketConstructor(Class<?> packetClass, MethodHandles.Lookup lookup) throws Exception {
        try {
            MethodHandle constructor = lookup.findConstructor(packetClass, VOID_METHOD_TYPE);
            return constructor::invoke;
        } catch (NoSuchMethodException | IllegalAccessException e) {
            // try below with Unsafe
        }

        if (theUnsafe == null) {
            synchronized (JavaReflections.class) {
                if (theUnsafe == null) {
                    Class<?> unsafeClass = Class.forName("sun.misc.Unsafe");
                    Field theUnsafeField = unsafeClass.getDeclaredField("theUnsafe");
                    theUnsafeField.setAccessible(true);
                    theUnsafe = theUnsafeField.get(null);
                }
            }
        }

        MethodType allocateMethodType = MethodType.methodType(Object.class, Class.class);
        MethodHandle allocateMethod = lookup.findVirtual(theUnsafe.getClass(), "allocateInstance", allocateMethodType);
        return () -> allocateMethod.invoke(theUnsafe, packetClass);
    }

    @FunctionalInterface
    public interface PacketConstructor {
        Object invoke() throws Throwable;
    }

    public interface ConstructorInvoker {
        Object invoke(Object... param1VarArgs);
    }

    public interface MethodInvoker {
        Object invoke(Object param1Object, Object... param1VarArgs);
    }

    public interface FieldAccessor<T> {
        T get(Object param1Object);

        void set(Object param1Object1, Object param1Object2);

        boolean hasField(Object param1Object);
    }
}