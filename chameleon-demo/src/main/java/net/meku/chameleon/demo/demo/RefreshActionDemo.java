package net.meku.chameleon.demo.demo;

import net.meku.chameleon.spi.ConfigManager;
import net.meku.chameleon.spi.ConfigRefreshAction;
import net.meku.chameleon.spi.ConfigRefreshActionFactory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class RefreshActionDemo implements ConfigRefreshAction, InitializingBean {

    private static final String REFRESH_KEY = "biz.custom.refreshDemo";

    private static final Log LOGGER = LogFactory.getLog(RefreshActionDemo.class);

    @Value("${" + REFRESH_KEY + "}")
    private boolean refreshDemo;

    @Autowired
    private ConfigRefreshActionFactory refreshActionFactory;

    @Autowired
    private ConfigManager configManager;

    @Override
    public void onRefresh() {
        boolean value = configManager.getBool(REFRESH_KEY);
        LOGGER.info("Now the value is " + value);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        refreshActionFactory.registerAction(REFRESH_KEY, this);
    }
}
