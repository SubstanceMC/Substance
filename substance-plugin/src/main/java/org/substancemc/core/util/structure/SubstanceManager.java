package org.substancemc.core.util.structure;

public interface SubstanceManager {
    void load();
    void unload();
    default void reload()
    {
        unload();
        load();
    }
}
