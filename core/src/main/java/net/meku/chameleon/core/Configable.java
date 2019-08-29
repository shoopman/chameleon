package net.meku.chameleon.core;

/**
 * 配置项
 */
public interface Configable {

    /**
     * 获得配置标识
     *
     * @return
     */
    String getKey();

    /**
     * 获得配置值
     *
     * @return
     */
    String getValue();
}
