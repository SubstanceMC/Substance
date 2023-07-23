package org.substancemc.core;

import org.bstats.bukkit.Metrics;
import org.bstats.charts.AdvancedBarChart;
import org.bstats.charts.DrilldownPie;
import org.bukkit.plugin.java.JavaPlugin;
import org.checkerframework.checker.units.qual.C;
import org.substancemc.core.addon.AddonManager;
import org.substancemc.core.compatibility.CompatibilityManager;
import org.substancemc.core.resourcepack.ResourcePackManager;
import org.substancemc.core.util.resource.ResourceExtractor;
import org.substancemc.core.util.resource.SubstanceResourceConstants;

import java.util.HashMap;
import java.util.Map;

public class SubstancePlugin extends JavaPlugin {

    private static SubstancePlugin instance;

    private ResourcePackManager resourcePackManager;
    private AddonManager addonManager;
    private CompatibilityManager compatibilityManager;
    private ResourceExtractor resourceExtractor;

    private Metrics metrics;

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
        metrics = new Metrics(this, 19207);
        metrics.addCustomChart(new DrilldownPie("used_addons", () -> {
            HashMap<String, Map<String, Integer>> addonsUsedMap = new HashMap<>();
            HashMap<String, Integer> official = new HashMap<>();
            HashMap<String, Integer> unofficial = new HashMap<>();
            addonManager.getAddons().forEach(addon -> {
                String key = addon.getId();
                boolean isOfficial = false;
                for(String author : addon.getAuthors())
                {
                    if (getDescription().getAuthors().contains(author)) {
                        isOfficial = true;
                        break;
                    }
                }
                if(addon.showsInMetrics())
                {
                    if(isOfficial)
                    {
                        official.put(key, 1);
                    }else unofficial.put(key, 1);
                }
            });
            addonsUsedMap.put("Official Addons", official);
            addonsUsedMap.put("Unofficial Addons", unofficial);
            return addonsUsedMap;
        }));
        resourcePackManager.load();
        compatibilityManager.load();
    }

    private void unloadAll()
    {
        compatibilityManager.unload();
        resourcePackManager.unload();
        addonManager.unload();
        metrics.shutdown();

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
