package net.meku.chameleon.starter;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.meku.chameleon.core.CacheableBeanResolver;
import net.meku.chameleon.core.CacheableConfigAspect;
import net.meku.chameleon.core.ConfigServiceImpl;
import net.meku.chameleon.memory.MemoryCacheResolver;
import net.meku.chameleon.memory.MemoryRefreshActionFactory;
import net.meku.chameleon.persist.FilePersistResolver;
import net.meku.chameleon.persist.JsonFileHandler;
import net.meku.chameleon.redis.RedisCacheConfig;
import net.meku.chameleon.redis.RedisCacheResolver;
import net.meku.chameleon.spi.ConfigCacheResolver;
import net.meku.chameleon.spi.ConfigPersistResolver;
import net.meku.chameleon.spi.ConfigRefreshActionFactory;
import net.meku.chameleon.spi.ConfigService;
import net.meku.chameleon.util.SpringUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnClass({ConfigService.class})
public class ChameleonAutoConfiguration {

    //======== core module beans start ========
    @Bean
    public SpringUtils springUtils() {
        return new SpringUtils();
    }

    @Bean
    public CacheableBeanResolver cacheableBeanResolver() {
        return new CacheableBeanResolver();
    }

    @Bean
    public ConfigService configService() {
        return new ConfigServiceImpl();
    }

    @Bean
    public CacheableConfigAspect cacheableConfigAspect() {
        return new CacheableConfigAspect();
    }
    //======== core module beans end ========

    @ConditionalOnProperty(value = "chameleon.cache", havingValue = "redis")
    @Bean
    public RedisCacheConfig redisCacheConfig() {
        return new RedisCacheConfig();
    }

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
    public JsonFileHandler jsonFileHandler(ObjectMapper objectMapper) {
        return new JsonFileHandler(objectMapper);
    }

    @ConditionalOnMissingBean(ConfigPersistResolver.class)
    @Bean
    public ConfigPersistResolver filePersistResolver(JsonFileHandler jsonFileHandler) {
        return new FilePersistResolver(jsonFileHandler);
    }

    @ConditionalOnMissingBean(ConfigRefreshActionFactory.class)
    @Bean
    public ConfigRefreshActionFactory memoryRefreshActionFactory() {
        return new MemoryRefreshActionFactory();
    }

}
