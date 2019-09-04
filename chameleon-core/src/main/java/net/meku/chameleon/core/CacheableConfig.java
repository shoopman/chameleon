package net.meku.chameleon.core;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 标记支持缓存的配置项
 *
 * @author LiangBin
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface CacheableConfig {

    /**
     * 是否忽略此方法
     * @return true则忽略
     */
    boolean ignore() default false;
}
