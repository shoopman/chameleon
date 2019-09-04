package net.meku.chameleon.memory;

import net.meku.chameleon.core.Configable;
import net.meku.chameleon.spi.ConfigCacheResolver;
import net.meku.chameleon.spi.ConfigRefreshActionFactory;
import net.meku.chameleon.util.ChameleonUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MemoryCacheResolver implements ConfigCacheResolver {

    private Map<String, String> cache = new HashMap<>();

    @Autowired(required = false)
    private ConfigRefreshActionFactory refreshActionFactory;

    @Override
    public void clear() {
        cache.clear();
    }

    @Override
    public void set(Configable configable) {
        if (configable == null) {
            return;
        }

        cache.put(configable.getKey(), configable.getValue());

        if (refreshActionFactory != null && refreshActionFactory instanceof MemoryRefreshActionFactory) {
            ((MemoryRefreshActionFactory) refreshActionFactory).onRefresh(configable.getKey());
        }
    }

    @Override
    public String get(String key) {
        return cache.get(key);
    }

    @Override
    public boolean exists(String key) {
        return cache.containsKey(key);
    }

    @Override
    public List<Configable> list() {
        return ChameleonUtils.fromMap(cache);
    }
}
