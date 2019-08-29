package net.meku.chameleon;

import net.meku.chameleon.core.Configable;

import java.util.List;

public interface ConfigService {

    /**
     * 从当前缓存中获得全部的配置项
     *
     * @return
     */
    List<Configable> listAll();

    boolean exists(String key);

    String getString(String key);

    boolean getBool(String key);

    int getInt(String key);

    long getLong(String key);

    double getDouble(String key);

    /**
     * 清空缓存并重新加载
     *
     * @return
     */
    List<Configable> reload();

    /**
     * 更新/保存配置项
     *
     * @param configable
     * @return
     */
    Configable save(Configable configable);
}
