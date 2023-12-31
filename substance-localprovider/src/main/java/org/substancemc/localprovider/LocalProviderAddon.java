package org.substancemc.localprovider;

import org.substancemc.core.SubstancePlugin;
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
    public void load() {
        String providerConfigPath = "resourcePack.server.localProvider";
        LocalPackProvider packProvider = getConfig("config").getSubstanceObject(providerConfigPath, LocalPackProvider.class);
        SubstancePlugin.get().getResourcePackManager().setPackProvider(packProvider);
    }

    @Override
    public void unload() {

    }
}
