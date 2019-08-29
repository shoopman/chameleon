package net.meku.chameleon;

import net.meku.chameleon.core.CacheableBeanResolver;
import net.meku.chameleon.core.ConfigPojo;
import net.meku.chameleon.core.Configable;
import net.meku.chameleon.spi.ConfigCacheResolver;
import net.meku.chameleon.spi.ConfigPersistResolver;
import net.meku.chameleon.util.SpringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ConfigServiceImpl implements ConfigService, BeanPostProcessor, Ordered {

    private Map<String, String> propertiesConfigs = new HashMap<>();

    @Autowired
    private ConfigCacheResolver cacheResolver;

    @Autowired
    private ConfigPersistResolver persistResolver;

    @Autowired
    private CacheableBeanResolver cacheableBeanResolver;

    @Autowired
    private SpringUtils springUtils;

    @Override
    public List<Configable> listAll() {
        List<Configable> list = cacheResolver.list();
        Collections.sort(list, ((o1, o2) -> o1.getKey().compareTo(o2.getKey())));
        return list;
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
        List<Configable> list = initializePropertiesList(); //properties文件中的配置
        list.addAll(persistResolver.load());    //持久化的配置

        // 先清空原缓存
        cacheResolver.clear();

        // 重新设置缓存
        list.forEach(config -> cacheResolver.set(config));
        return list;
    }

    private List<Configable> initializePropertiesList() {
        List<Configable> list = new ArrayList<>();
        for (String key : propertiesConfigs.keySet()) {
            ConfigPojo pojo = new ConfigPojo();
            pojo.setKey(key);
            pojo.setValue(propertiesConfigs.get(key));
            list.add(pojo);
        }
        return list;
    }

    @Override
    public Configable save(Configable configable) {
        Configable saved = persistResolver.save(configable);
        cacheResolver.set(saved);
        return saved;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    // 设置应用级配置项的默认值
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        List<String> keys = cacheableBeanResolver.resolve(bean);
        if (!keys.isEmpty()) {
            keys.forEach(key -> setConfigFromProperties(key));
        }

        return bean;
    }

    private void setConfigFromProperties(String key) {
        String value = springUtils.getProperty(key);
        propertiesConfigs.put(key, value);
    }

    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }
}
