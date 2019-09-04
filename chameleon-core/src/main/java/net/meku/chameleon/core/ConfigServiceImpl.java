package net.meku.chameleon.core;

import net.meku.chameleon.spi.ConfigCacheResolver;
import net.meku.chameleon.spi.ConfigPersistResolver;
import net.meku.chameleon.spi.ConfigService;
import net.meku.chameleon.util.SpringUtils;
import org.apache.commons.lang3.StringUtils;
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
        String value = getString(key);
        if (StringUtils.isBlank(value)) {
            return false;
        }
        return Boolean.parseBoolean(value);
    }

    @Override
    public int getInt(String key) {
        String value = getString(key);
        if (StringUtils.isBlank(value)) {
            return 0;
        }
        return Integer.parseInt(value);
    }

    @Override
    public long getLong(String key) {
        String value = getString(key);
        if (StringUtils.isBlank(value)) {
            return 0l;
        }
        return Long.parseLong(value);
    }

    @Override
    public double getDouble(String key) {
        String value = getString(key);
        if (StringUtils.isBlank(value)) {
            return 0d;
        }
        return Double.parseDouble(value);
    }

    @Override
    public List<Configable> reload() {
        List<Configable> properties = initializePropertiesList(); //properties文件中的配置
        List<Configable> persists = persistResolver.load(); //持久化的配置
        List<Configable> list = mergeConfigList(properties, persists);

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

    private List<Configable> mergeConfigList(List<Configable> properties, List<Configable> persists) {
        for (Configable configable : properties) {
            if (!existsInList(configable.getKey(), persists)) {
                persists.add(configable);   //如果未持久化的properties中的配置，才加入到缓存
            }
        }
        return persists;
    }

    private boolean existsInList(String key, List<Configable> list) {
        for (Configable configable : list) {
            if (key.equals(configable.getKey())) {
                return true;
            }
        }
        return false;
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
