package net.meku.chameleon.demo.web;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import net.meku.chameleon.core.Configable;

@ApiModel(description = "Config View")
public class ConfigView implements Configable {

    private String id;

    private String value;

    @ApiModelProperty("Id of a config item")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @JsonIgnore
    @ApiModelProperty(hidden = true)
    @Override
    public String getKey() {
        return id;
    }

    @ApiModelProperty("The value of a config item")
    @Override
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
