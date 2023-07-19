package org.substancemc.core;

import org.bukkit.plugin.java.JavaPlugin;
import org.substancemc.core.addon.AddonManager;
import org.substancemc.core.resourcepack.ResourcePackManager;

public class SubstancePlugin extends JavaPlugin {

    private static SubstancePlugin instance;

    private ResourcePackManager resourcePackManager;
    private AddonManager addonManager;

    public void onEnable()
    {
        instance = this;
        addonManager = new AddonManager();
        resourcePackManager = new ResourcePackManager();
        saveDefaultConfig();
        loadAll();
    }

    private void loadAll()
    {
        addonManager.load();
        resourcePackManager.load();
    }

    private void unloadAll()
    {
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

    public static SubstancePlugin get()
    {
        return instance;
    }


}
