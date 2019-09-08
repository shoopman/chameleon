package net.meku.chameleon.redis;

import net.meku.chameleon.spi.ConfigRefreshActionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

public class RedisCacheConfig {

    @Value("${chameleon.redis.keyPrefix:_ccc:}")
    private String keyPrefix = "";

    public String getKeyPrefix() {
        return keyPrefix;
    }

    @ConditionalOnMissingBean(RedisMessageListenerContainer.class)
    @Bean
    public RedisMessageListenerContainer redisMessageListenerContainer(RedisConnectionFactory connectionFactory) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        return container;
    }

    @Bean
    public ConfigRefreshActionFactory redisRefreshActionFactory(RedisMessageListenerContainer listenerContainer,
                                                                RedisCacheConfig redisCacheConfig) {
        return new RedisRefreshActionFactory(listenerContainer, redisCacheConfig);
    }
}
