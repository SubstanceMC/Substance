package org.substancemc.core;

import org.bukkit.plugin.java.JavaPlugin;
import org.checkerframework.checker.units.qual.C;
import org.substancemc.core.addon.AddonManager;
import org.substancemc.core.compatibility.CompatibilityManager;
import org.substancemc.core.resourcepack.ResourcePackManager;
import org.substancemc.core.util.resource.ResourceExtractor;
import org.substancemc.core.util.resource.SubstanceResourceConstants;

public class SubstancePlugin extends JavaPlugin {

    private static SubstancePlugin instance;

    private ResourcePackManager resourcePackManager;
    private AddonManager addonManager;
    private CompatibilityManager compatibilityManager;
    private ResourceExtractor resourceExtractor;

    public void onEnable()
    {
        instance = this;
        addonManager = new AddonManager();
        resourcePackManager = new ResourcePackManager();
        compatibilityManager = new CompatibilityManager();
        resourceExtractor = new ResourceExtractor(SubstanceResourceConstants.RESOURCE_LOCATIONS);
        loadAll();
    }

    private void loadAll()
    {
        addonManager.load();
        resourcePackManager.load();
        compatibilityManager.load();
    }

    private void unloadAll()
    {
        compatibilityManager.unload();
        resourcePackManager.unload();
        addonManager.unload();
    }

    public void reloadAll()
    {
        unloadAll();
        loadAll();
    }

    public void onDisable()
    {
        unloadAll();
    }

    public AddonManager getAddonManager()
    {
        return addonManager;
    }

    public ResourcePackManager getResourcePackManager()
    {
        return resourcePackManager;
    }

    public CompatibilityManager getCompatibilityManager()
    {
        return compatibilityManager;
    }

    public ResourceExtractor getResourceExtractor()
    {
        return resourceExtractor;
    }

    public static SubstancePlugin get()
    {
        return instance;
    }


}
