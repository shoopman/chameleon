package net.meku.chameleon.demo.web;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.meku.chameleon.spi.ConfigManager;
import net.meku.chameleon.core.Configable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Api("Config API")
@RequestMapping("/configs")
@RestController
public class ConfigController {

    @Autowired
    private ConfigManager configManager;

    @ApiOperation(value = "Get all config items.")
    @GetMapping
    public List<ConfigView> listAll() {
        List<Configable> pojos = configManager.listAll();
        return from(pojos);
    }

    @ApiOperation(value = "Reload all config items.")
    @PostMapping("/reload")
    public List<ConfigView> reload() {
        List<Configable> pojos = configManager.reload();
        return from(pojos);
    }

    @ApiOperation(value = "Update a config item.")
    @PutMapping
    public ConfigView update(ConfigView view) {
        Configable saved = configManager.save(view);
        return from(saved);
    }

    private ConfigView from(Configable configable) {
        ConfigView view = new ConfigView();
        if (configable != null) {
            view.setId(configable.getKey());
            view.setValue(configable.getValue());
        }
        return view;
    }

    private List<ConfigView> from(List<Configable> pojos) {
        List<ConfigView> views = new ArrayList<>();
        pojos.forEach(pojo -> views.add(from(pojo)));
        return views;
    }
}
