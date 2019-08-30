package net.meku.chameleon.spi;

/**
 * 管理配置变更操作的工厂
 *
 * @author LiangBin
 */
public interface ConfigRefreshActionFactory {

    /**
     * 增加变更操作
     *
     * @param key
     * @param action
     */
    void registerAction(String key, ConfigRefreshAction action);

    /**
     * 移除变更操作
     *
     * @param key
     */
    void removeActions(String key);

}
