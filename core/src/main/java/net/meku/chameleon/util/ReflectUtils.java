package net.meku.chameleon.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @author LiangBin
 */
public class ReflectUtils {

    public static boolean isGetter(Method method) {
        if (method == null) {
            return false;
        }

        String methodName = method.getName();
        return methodName.startsWith("get") || methodName.startsWith("is");
    }

    public static Field getFieldForGetter(Method getter) {
        String fieldName = null;
        String methodName = getter.getName();
        if (methodName.startsWith("get")) {
            String startLetter = methodName.substring(3, 4).toLowerCase();
            String endLetters = methodName.substring(4);
            fieldName = startLetter + endLetters;
        }
        else if (methodName.startsWith("is")) {
            String startLetter = methodName.substring(3, 4).toLowerCase();
            String endLetters = methodName.substring(4);
            fieldName = startLetter + endLetters;
        }
        else {
            return null;
        }

        Field field = null;
        try {
            return getter.getDeclaringClass().getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            return null;
        }
    }
}
