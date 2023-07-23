package org.substancemc.localprovider;

import org.substancemc.core.SubstancePlugin;
import org.substancemc.core.addon.AddonPriority;
import org.substancemc.core.addon.SubstanceAddon;

public class LocalProviderAddon implements SubstanceAddon {
    @Override
    public String getVersion() {
        return "0.1.0";
    }

    @Override
    public String getId() {
        return "local-provider-addon";
    }

    @Override
    public String[] getAuthors() {
        return new String[] {"SubstanceMC"};
    }

    @Override
    public int getPriority() {
        return AddonPriority.HIGHEST;
    }

    @Override
    public void load() {
        SubstancePlugin.get().getResourcePackManager().setPackProvider(new LocalPackProvider(8888));
    }

    @Override
    public void unload() {

    }
}