package net.meku.chameleon.redis;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RedisCacheConfig {

    @Value("${chameleon.redis.keyPrefix:_ccc:}")
    private String keyPrefix = "";

    public String getKeyPrefix() {
        return keyPrefix;
    }
}
