package net.meku.chameleon.core;

import net.meku.chameleon.spi.ConfigService;
import net.meku.chameleon.util.ReflectUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author LiangBin
 */
@Aspect
public class CacheableConfigAspect {

    @Autowired
    private ConfigService configService;

    private List<String> ignoreMethods = new ArrayList<>();

    private Map<String, String> keyMaps = new HashMap<>();


    @Pointcut("@annotation(net.meku.chameleon.core.CacheableConfig) || @within(net.meku.chameleon.core.CacheableConfig)")
    public void process() {
    }

    @Around("process()")
    public Object round(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();

        String aopKey = joinPoint.toLongString();
        if (keyMaps.containsKey(aopKey)) {
            return getCacheConfig(keyMaps.get(aopKey), signature.getReturnType());
        }

        if (isInIgnoreList(aopKey)) {
            return joinPoint.proceed();
        }

        String configKey = getConfigKey(signature);
        if (configKey == null) {
            ignoreMethods.add(aopKey);
            return joinPoint.proceed();
        }
        else {
            keyMaps.put(aopKey, configKey);
            return getCacheConfig(configKey, signature.getReturnType());
        }
    }

    private boolean isInIgnoreList(String aopKey) {
        if (ignoreMethods.isEmpty()) {
            return false;
        }

        return ignoreMethods.stream().anyMatch(item -> item.equals(aopKey));
    }

    /**
     * 获得getter方法对应的属性
     *
     * @param methodSignature
     * @return
     */
    private String getConfigKey(MethodSignature methodSignature) {
        Method method = methodSignature.getMethod();
        CacheableConfig config = method.getAnnotation(CacheableConfig.class);
        if (config != null && config.ignore()) {
            return null;
        }

        Field field = ReflectUtils.getFieldForGetter(method);
        if (field == null) {
            return null;
        }

        // 获得属性上的@Value注解
        Value value = field.getAnnotation(Value.class);
        if (value == null) {
            return null;
        }
        String keyEL = value.value();
        if (StringUtils.isBlank(keyEL)) {
            return null;
        }

        if (!keyEL.startsWith("${") && !keyEL.endsWith("}")) {
            return null;
        }
        return keyEL.substring(2, keyEL.length() - 1);
    }

    private Object getCacheConfig(String key, Class type) {
        String value = configService.getString(key);
        if (type == String.class) {
            return value;
        }

        return ConvertUtils.convert(value, type);
    }

}
