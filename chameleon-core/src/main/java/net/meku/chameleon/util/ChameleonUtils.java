package net.meku.chameleon.util;

import net.meku.chameleon.core.ConfigPojo;
import net.meku.chameleon.core.Configable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ChameleonUtils {

    public static List<Configable> fromMap(Map<String, String> map) {
        List<Configable> list = new ArrayList<>();

        for (Map.Entry<String, String> entry : map.entrySet()) {
            ConfigPojo pojo = new ConfigPojo();
            pojo.setKey(entry.getKey());
            pojo.setValue(entry.getValue());
            list.add(pojo);
        }

        return list;
    }
}
