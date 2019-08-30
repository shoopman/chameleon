package net.meku.chameleon.starter;

import net.meku.chameleon.memory.MemoryCacheResolver;
import net.meku.chameleon.memory.MemoryRefreshActionFactory;
import net.meku.chameleon.persist.FilePersistResolver;
import net.meku.chameleon.redis.RedisCacheResolver;
import net.meku.chameleon.redis.RedisRefreshActionFactoryBuilder;
import net.meku.chameleon.spi.ConfigCacheResolver;
import net.meku.chameleon.spi.ConfigPersistResolver;
import net.meku.chameleon.spi.ConfigRefreshActionFactory;
import net.meku.chameleon.spi.ConfigService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnClass({ConfigService.class})
public class ChameleonAutoConfiguration {

    @ConditionalOnProperty(value = "chameleon.cache", havingValue = "redis")
    @Bean
    public ConfigCacheResolver redisCacheResolver() {
        return new RedisCacheResolver();
    }

    @ConditionalOnMissingBean(ConfigCacheResolver.class)
    @Bean
    public ConfigCacheResolver memoryCacheResolver() {
        return new MemoryCacheResolver();
    }

    @ConditionalOnMissingBean(ConfigPersistResolver.class)
    @Bean
    public ConfigPersistResolver filePersistResolver() {
        return new FilePersistResolver();
    }

    @ConditionalOnProperty(value = "chameleon.cache", havingValue = "redis")
    @Bean
    public ConfigRefreshActionFactory redisRefreshActionFactory() {
        RedisRefreshActionFactoryBuilder builder = new RedisRefreshActionFactoryBuilder();
        return builder.build();
    }

    @ConditionalOnMissingBean(ConfigRefreshActionFactory.class)
    @Bean
    public ConfigRefreshActionFactory memoryRefreshActionFactory() {
        return new MemoryRefreshActionFactory();
    }

}
