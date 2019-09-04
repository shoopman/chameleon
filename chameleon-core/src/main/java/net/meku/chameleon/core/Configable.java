package net.meku.chameleon.core;

/**
 * 配置项
 */
public interface Configable {

    /**
     * 获得配置标识
     *
     * @return 配置标识
     */
    String getKey();

    /**
     * 获得配置值
     *
     * @return 配置值
     */
    String getValue();
}
