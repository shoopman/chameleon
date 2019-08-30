package net.meku.chameleon.memory;

import net.meku.chameleon.refresh.ConfigRefreshAction;
import net.meku.chameleon.refresh.ConfigRefreshActionFactory;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class MemoryRefreshActionFactory implements ConfigRefreshActionFactory {

    private Map<String, Set<ConfigRefreshAction>> actionMap = new HashMap<>();

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


    void onRefresh(String key) {
        Set<ConfigRefreshAction> actions = actionMap.get(key);
        if (actions == null || actions.isEmpty()) {
            return;
        }

        actions.forEach(action -> action.onRefresh());
    }

}
