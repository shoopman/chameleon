package net.meku.chameleon;

import net.meku.chameleon.core.Configable;
import net.meku.chameleon.spi.ConfigCacheResolver;
import net.meku.chameleon.spi.ConfigPersistResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConfigServiceImpl implements ConfigService {

    @Autowired
    private ConfigCacheResolver cacheResolver;

    @Autowired
    private ConfigPersistResolver persistResolver;

    @Override
    public List<Configable> listAll() {
        return null;
    }

    @Override
    public boolean exists(String key) {
        return cacheResolver.exists(key);
    }

    @Override
    public String getString(String key) {
        return cacheResolver.get(key);
    }

    @Override
    public boolean getBool(String key) {
        return Boolean.parseBoolean(getString(key));
    }

    @Override
    public int getInt(String key) {
        return Integer.parseInt(getString(key));
    }

    @Override
    public long getLong(String key) {
        return Long.parseLong(getString(key));
    }

    @Override
    public double getDouble(String key) {
        return Double.parseDouble(getString(key));
    }

    @Override
    public List<Configable> reload() {
        List<Configable> list = persistResolver.load();
        cacheResolver.clear();
        // TODO 获得properties中的
        list.forEach(config -> cacheResolver.set(config));
        return list;
    }

    @Override
    public Configable save(Configable configable) {
        return persistResolver.save(configable);
    }
}
