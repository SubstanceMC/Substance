package org.substancemc.core.compatibility;

import org.substancemc.core.compatibility.impl.ProtocolLibCompatibility;
import org.substancemc.core.util.structure.SubstanceManager;

import java.util.ArrayList;

public class CompatibilityManager implements SubstanceManager {

    public final ProtocolLibCompatibility PROTOCOL_LIB = new ProtocolLibCompatibility();

    private final ArrayList<Compatibility> compatibilities = new ArrayList<>();


    public void addCompatibility(Compatibility compatibility)
    {
        compatibilities.add(compatibility);
    }

    public <T extends Compatibility> T getCompatibility(Class<? extends Compatibility> compatibility)
    {
        return (T) compatibilities.stream().filter(compat -> compat.getClass().equals(compatibility)).findAny().orElse(null);
    }


    @Override
    public void load() {
        addCompatibility(PROTOCOL_LIB);
    }



    @Override
    public void unload() {
        compatibilities.clear();
    }
}
