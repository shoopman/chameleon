package net.meku.chameleon.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

public class RedisRefreshActionFactoryBuilder {

    @Autowired
    private RedisMessageListenerContainer listenerContainer;

    public RedisRefreshActionFactory build() {
        return new RedisRefreshActionFactory(listenerContainer);
    }
}
