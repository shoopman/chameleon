package net.meku.chameleon.refresh;

/**
 * @author LiangBin
 */
public interface ConfigRefreshActionFactory {

    void registerAction(String key, ConfigRefreshAction action);

    void removeActions(String key);
}
