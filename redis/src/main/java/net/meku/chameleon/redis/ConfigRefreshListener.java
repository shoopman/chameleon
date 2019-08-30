package net.meku.chameleon.redis;

import net.meku.chameleon.spi.ConfigRefreshAction;
import net.meku.chameleon.spi.ConfigRefreshActionFactory;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.listener.KeyspaceEventMessageListener;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author LiangBin
 */
@Component
public class ConfigRefreshListener extends KeyspaceEventMessageListener implements ConfigRefreshActionFactory {

    private static final String KEY_EVENT = "__keyevent@";

    private static final String KEY_OPERATION = ":set";

    private Map<String, Set<ConfigRefreshAction>> actionMap = new HashMap<>();

    @Autowired
    private RedisCacheConfig redisCacheConfig;

    /**
     * Creates new {@link KeyspaceEventMessageListener}.
     *
     * @param listenerContainer must not be {@literal null}.
     */
    @Autowired
    public ConfigRefreshListener(RedisMessageListenerContainer listenerContainer) {
        super(listenerContainer);
    }

    @Override
    protected void doHandleMessage(Message message) {
        if (message == null || message.getBody() == null || message.getChannel() == null) {
            return;
        }

        String body = new String(message.getBody());    //Redis的Key
        String channel = new String(message.getChannel());  // 如 __keyevent@0__:set

        String prefix = redisCacheConfig.getKeyPrefix();
        if (body.startsWith(prefix) && channel.startsWith(KEY_EVENT) && channel.endsWith(KEY_OPERATION)) {
            onRefresh(body.substring(prefix.length()));
        }
    }

    private void onRefresh(String configId) {
        Set<ConfigRefreshAction> actions = actionMap.get(configId);
        if (actions == null || actions.isEmpty()) {
            return;
        }

        actions.forEach(action -> action.onRefresh());
    }

    @Override
    public void registerAction(String key, ConfigRefreshAction action) {
        if (StringUtils.isBlank(key) || action == null) {
            return;
        }

        Set<ConfigRefreshAction> actions = actionMap.get(key);
        if (actions == null) {
            actions = new HashSet<>();
            actionMap.put(key, actions);
        }
        actions.add(action);
    }

    @Override
    public void removeActions(String key) {
        actionMap.remove(key);
    }
}
