package net.meku.chameleon.core;

public class ConfigPojo implements Configable {

    private String key;

    private String value;

    public ConfigPojo() {
    }

    public ConfigPojo(String key, String value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
