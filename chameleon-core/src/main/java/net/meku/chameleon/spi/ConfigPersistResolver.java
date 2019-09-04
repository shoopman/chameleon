package net.meku.chameleon.spi;

import net.meku.chameleon.core.Configable;

import java.util.List;

/**
 * 配置项的持久化处理器
 */
public interface ConfigPersistResolver {

    /**
     * 获得所有已保存的
     *
     * @return 配置项列表
     */
    List<Configable> load();

    /**
     * 保存配置项
     *
     * @param configable 需要保存/更新的配置项
     * @return 配置项
     */
    Configable save(Configable configable);
}
