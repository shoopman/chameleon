package net.meku.chameleon.redis;

import net.meku.chameleon.core.ConfigPojo;
import net.meku.chameleon.core.Configable;
import net.meku.chameleon.spi.ConfigCacheResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.*;

public class RedisCacheResolver implements ConfigCacheResolver {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private RedisCacheConfig redisCacheConfig;

    @Override
    public void clear() {
        Set<String> keys = redisTemplate.keys(redisCacheConfig.getKeyPrefix() + "*");
        redisTemplate.delete(keys);
    }

    @Override
    public void set(Configable configable) {
        if (configable == null) {
            return;
        }

        redisTemplate.opsForValue().set(getFullKey(configable.getKey()), configable.getValue());
    }

    @Override
    public void set(List<? extends Configable> list) {
        if (list == null || list.isEmpty()) {
            return;
        }

        Map<String, String> configs = new HashMap<>();
        list.forEach(configable -> configs.put(getFullKey(configable.getKey()), configable.getValue()));
        redisTemplate.opsForValue().multiSet(configs);
    }

    @Override
    public String get(String key) {
        return redisTemplate.opsForValue().get(getFullKey(key));
    }

    @Override
    public boolean exists(String key) {
        return redisTemplate.hasKey(getFullKey(key));
    }

    @Override
    public List<Configable> list() {
        List<Configable> list = new ArrayList<>();
        Set<String> keys = redisTemplate.keys(redisCacheConfig.getKeyPrefix() + "*");
        List<String> orderKeys = new ArrayList<>();
        keys.forEach(key -> orderKeys.add(key));

        List<String> values = redisTemplate.opsForValue().multiGet(orderKeys);
        int prefixLength = redisCacheConfig.getKeyPrefix().length();
        for (int i = 0; i < values.size(); i++) {
            ConfigPojo pojo = new ConfigPojo();
            pojo.setKey(orderKeys.get(i).substring(prefixLength));
            pojo.setValue(values.get(i));
            list.add(pojo);
        }

        return list;
    }

    private String getFullKey(String shortKey) {
        return redisCacheConfig.getKeyPrefix() + shortKey;
    }
}
