package net.meku.chameleon.memory;

import net.meku.chameleon.core.Configable;
import net.meku.chameleon.spi.ConfigPersistResolver;

import java.util.ArrayList;
import java.util.List;

/**
 * A dummy persist provider
 */
public class DummyPersistResolver implements ConfigPersistResolver {

    @Override
    public List<Configable> load() {
        return new ArrayList<>();
    }

    @Override
    public Configable save(Configable configable) {
        return configable;
    }
}
