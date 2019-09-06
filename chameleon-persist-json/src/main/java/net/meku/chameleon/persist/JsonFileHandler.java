package net.meku.chameleon.persist;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class JsonFileHandler {

    private static final Log LOGGER = LogFactory.getLog(JsonFileHandler.class);

    private ObjectMapper objectMapper;

    public JsonFileHandler(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Value("${chameleon.persist.json.path:configs.json}")
    private String filePath;

    public Map<String, String> read() {
        File file = new File(filePath);
        if (!file.exists()) {
            return Collections.EMPTY_MAP;
        }

        try {
            return objectMapper.readValue(file, Map.class);
        } catch (IOException e) {
            LOGGER.error("Failed to convert file to Map.", e);
            return Collections.EMPTY_MAP;
        }
    }

    public Map<String, String> write(String key, String value) throws IOException {
        Map<String, String> map;
        File file = new File(filePath);
        if (!file.exists()) {
            map = new HashMap<>();
        }
        else {
            map = objectMapper.readValue(file, Map.class);
        }

        map.put(key, value);
        objectMapper.writeValue(file, map);

        return map;
    }
}
