package net.meku.chameleon.memory;

import net.meku.chameleon.core.ConfigPojo;
import net.meku.chameleon.core.Configable;
import net.meku.chameleon.spi.ConfigCacheResolver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MemoryCacheResolver implements ConfigCacheResolver {

    private Map<String, String> cache = new HashMap<>();

    @Override
    public void clear() {
        cache.clear();
    }

    @Override
    public void set(Configable configable) {
        if (configable != null) {
            cache.put(configable.getKey(), configable.getValue());
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
        List<Configable> list = new ArrayList<>();
        for (Map.Entry<String, String> entry : cache.entrySet()) {
            ConfigPojo pojo = new ConfigPojo();
            pojo.setKey(entry.getKey());
            pojo.setValue(entry.getValue());
            list.add(pojo);
        }

        return list;
    }
}
