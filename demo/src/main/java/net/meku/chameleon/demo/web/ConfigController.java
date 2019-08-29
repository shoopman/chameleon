package net.meku.chameleon.demo.web;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.meku.chameleon.ConfigService;
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
    private ConfigService configService;

    @ApiOperation(value = "Get all config items.")
    @GetMapping
    public List<ConfigView> listAll() {
        List<Configable> pojos = configService.listAll();
        return from(pojos);
    }

    @ApiOperation(value = "Reload all config items.")
    @PostMapping("/reload")
    public List<ConfigView> reload() {
        List<Configable> pojos = configService.reload();
        return from(pojos);
    }

    @ApiOperation(value = "Update a config item.")
    @PutMapping
    public ConfigView update(ConfigView view) {
        Configable saved = configService.save(view);
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
