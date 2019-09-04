package net.meku.chameleon.spi;

import net.meku.chameleon.core.Configable;

import java.util.List;

/**
 * 配置项的缓存处理器
 */
public interface ConfigCacheResolver {

    /**
     * 清空缓存
     */
    void clear();

    /**
     * 设置配置项到缓存
     *
     * @param configable 配置项
     */
    void set(Configable configable);

    /**
     * 从缓存中获得配置值
     *
     * @param key 配置标识
     * @return 配置值
     */
    String get(String key);

    /**
     * 判断缓存中是否包含指定的配置
     *
     * @param key 配置标识
     * @return 如果存在则返回true
     */
    boolean exists(String key);

    /**
     * 获得缓存中的所有配置
     *
     * @return 配置项列表
     */
    List<Configable> list();
}
