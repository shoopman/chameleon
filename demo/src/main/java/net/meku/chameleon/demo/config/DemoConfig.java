package net.meku.chameleon.demo.config;

import net.meku.chameleon.persist.DummyPersistResolver;
import net.meku.chameleon.memory.MemoryCacheResolver;
import net.meku.chameleon.memory.MemoryRefreshActionFactory;
import net.meku.chameleon.spi.ConfigRefreshActionFactory;
import net.meku.chameleon.spi.ConfigCacheResolver;
import net.meku.chameleon.spi.ConfigPersistResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DemoConfig {

    @Bean
    public ConfigCacheResolver configCacheResolver() {
        return new MemoryCacheResolver();
    }

    @Bean
    public ConfigPersistResolver configPersistResolver() {
        return new DummyPersistResolver();
    }

    @Bean
    public ConfigRefreshActionFactory configRefreshActionFactory() {
        return new MemoryRefreshActionFactory();
    }
}
