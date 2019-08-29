package net.meku.chameleon.refresh;

/**
 * 当配置项（缓存）发生重新加载（变化）时，需要执行的动作
 * 一般用于将配置项写入本地内存（成员变量）的Bean
 *
 * @author LiangBin
 */
public interface ConfigRefreshAction {

    /**
     * 通知配置项已重新加载
     */
    void onRefresh();

}
