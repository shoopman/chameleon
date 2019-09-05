package net.meku.chameleon.persist;

import net.meku.chameleon.core.Configable;
import net.meku.chameleon.spi.ConfigPersistResolver;
import net.meku.chameleon.util.ChameleonUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * A resolver to persist configurations to a json file.
 */
public class FilePersistResolver implements ConfigPersistResolver {

    private static final Log LOGGER = LogFactory.getLog(JsonFileHandler.class);

    private JsonFileHandler jsonFileHandler;

    public FilePersistResolver(JsonFileHandler jsonFileHandler) {
        this.jsonFileHandler = jsonFileHandler;
    }

    @Override
    public List<Configable> load() {
        Map<String, String> map = jsonFileHandler.read();
        return ChameleonUtils.fromMap(map);
    }

    @Override
    public Configable save(Configable configable) {
        try {
            jsonFileHandler.write(configable.getKey(), configable.getValue());
        } catch (IOException e) {
            LOGGER.error("Failed to save config" + configable.getKey(), e);
        }
        return configable;
    }
}
