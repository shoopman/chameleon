package net.meku.chameleon.core;

import net.meku.chameleon.spi.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

public class CacheFirstLoader implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private ConfigService configService;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        configService.reload();
    }

}
