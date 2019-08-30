package net.meku.chameleon.demo.config;

import net.meku.chameleon.core.CacheableConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@CacheableConfig
public class CustomConfig {

    @Value("${biz.custom.product}")
    private String product;

    @Value("${biz.custom.type}")
    private String type;

    public String getProduct() {
        return product;
    }

    public String getType() {
        return type;
    }
}
