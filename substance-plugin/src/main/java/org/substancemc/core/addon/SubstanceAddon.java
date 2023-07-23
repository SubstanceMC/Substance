package org.substancemc.core.addon;

import org.substancemc.core.util.structure.SubstanceManager;

public interface SubstanceAddon extends SubstanceManager {

    String getVersion();
    String getId();
    default String[] getAuthors()
    {
        return new String[]{};
    }

    default int getPriority()
    {
        return -1;
    }
}
