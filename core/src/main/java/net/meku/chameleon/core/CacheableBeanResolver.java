package net.meku.chameleon.core;

import net.meku.chameleon.util.ReflectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @author LiangBin
 */
@Component
public class CacheableBeanResolver {

    /**
     * 解析Cacheable bean
     *
     * @param bean 需要处理的bean
     * @return 需要缓存的参数名列表
     */
    public List<String> resolve(Object bean) {
        List<String> cacheableKeys = new ArrayList<>();
        List<Method> annotatedMethods = new ArrayList<>();

        Class<?> targetClass = getBeanClass(bean);
        CacheableConfig annotation = targetClass.getAnnotation(CacheableConfig.class);
        if (annotation != null && !annotation.ignore()) {
            // 类上有注解的方法
            Method[] methods = targetClass.getMethods();
            if (methods != null && methods.length > 0) {
                for (Method method : methods) {
                    if (isMatchedMethodInAnnotatedClass(method)) {
                        annotatedMethods.add(method);
                    }
                }
            }
        } else {
            // 类上无注解的方法
            Method[] methods = targetClass.getMethods();
            for (Method method : methods) {
                if (isMatchedMethodInNonAnnotatedClass(method)) {
                    annotatedMethods.add(method);
                }
            }
        }

        for (Method method : annotatedMethods) {
            Field field = ReflectUtils.getFieldForGetter(method);
            if (field == null) {
                continue;
            }

            // 获得属性上的@Value注解
            Value value = field.getAnnotation(Value.class);
            if (value == null) {
                continue;
            }
            String keyEL = value.value();
            if (StringUtils.isBlank(keyEL)) {
                continue;
            }
            if (!keyEL.startsWith("${") && !keyEL.endsWith("}")) {
                continue;
            }
            String key = keyEL.substring(2, keyEL.length() - 1);
            cacheableKeys.add(key);
        }

        return cacheableKeys;
    }

    private Class getBeanClass(Object bean) {
        Class clazz = AopProxyUtils.ultimateTargetClass(bean);
        String className = clazz.getName();
        if (className.indexOf("$$") > 0) {
            className = StringUtils.split(className, "$$")[0];
            try {
                clazz = Class.forName(className);
            } catch (ClassNotFoundException e) {
                //LOGGER.error("Failed to load class %s.", className);
                // TODO logger
            }
        }
        return clazz;
    }

    private boolean isMatchedMethodInAnnotatedClass(Method method) {
        CacheableConfig annotation = method.getAnnotation(CacheableConfig.class);
        if ((annotation == null || !annotation.ignore()) && ReflectUtils.isGetter(method)) {
            return true;
        }
        return false;
    }

    private boolean isMatchedMethodInNonAnnotatedClass(Method method) {
        CacheableConfig annotation = method.getAnnotation(CacheableConfig.class);
        if (annotation != null && !annotation.ignore() && ReflectUtils.isGetter(method)) {
            return true;
        }
        return false;
    }
}
