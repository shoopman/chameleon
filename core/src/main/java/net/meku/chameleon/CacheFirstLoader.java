package net.meku.chameleon;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class CacheFirstLoader implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private ConfigService configService;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        configService.reload();
    }

}
