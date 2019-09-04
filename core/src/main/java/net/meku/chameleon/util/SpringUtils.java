package net.meku.chameleon.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.EmbeddedValueResolverAware;
import org.springframework.stereotype.Component;
import org.springframework.util.StringValueResolver;

/**
 * @author LiangBin
 */
@Component
public class SpringUtils implements ApplicationContextAware, EmbeddedValueResolverAware {

    private ApplicationContext applicationContext;

    private StringValueResolver stringValueResolver;

    // 获取applicationContext
    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    // 通过name获取 Bean.
    public Object getBean(String name) {
        return getApplicationContext().getBean(name);
    }

    // 通过class获取Bean.
    public <T> T getBean(Class<T> clazz) {
        return getApplicationContext().getBean(clazz);
    }

    // 通过name,以及Clazz返回指定的Bean
    public <T> T getBean(String name, Class<T> clazz) {
        return getApplicationContext().getBean(name, clazz);
    }

    /**
     * 动态获取配置文件中的值
     *
     * @param name 配置名称
     * @return 配置值
     */
    public String getProperty(String name) {
        String key = "${" + name + "}";
        return stringValueResolver.resolveStringValue(key);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void setEmbeddedValueResolver(StringValueResolver resolver) {
        this.stringValueResolver = resolver;
    }
}
