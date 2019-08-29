package net.meku.chameleon.spi;

import net.meku.chameleon.core.Configable;

import java.util.List;

/**
 * 配置项的持久化处理器
 */
public interface ConfigPersistResolver {

    /**
     * 获得所有已保存的
     * @return
     */
    List<Configable> load();

    /**
     * 保存配置项
     * @param configable
     * @return
     */
    Configable save(Configable configable);
}
