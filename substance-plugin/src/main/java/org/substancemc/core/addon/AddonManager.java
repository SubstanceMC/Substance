package org.substancemc.core.addon;

import org.substancemc.core.SubstancePlugin;
import org.substancemc.core.util.file.DataFolderFile;
import org.substancemc.core.util.structure.SubstanceManager;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class AddonManager implements SubstanceManager {

    private List<SubstanceAddon> addonList = new ArrayList<>();
    private final AddonLoader loader = new AddonLoader(new DataFolderFile("addons"));

    public static String ENTITY_ADDON = "substance-entity";
    @Override
    public void load() {
        addonList.addAll(loader.load());
        addonList = addonList.stream().sorted(Comparator.comparingInt(SubstanceAddon::getPriority)).toList();
        addonList.forEach(addon -> {
            addon.load();
            SubstancePlugin.get().getLogger().info(addon.getId() + " v" + addon.getVersion() + " loaded. (Addon by " + String.join(", ", addon.getAuthors()) + ")");
        });
    }

    public SubstanceAddon loadAddon(DataFolderFile file)
    {
        SubstanceAddon addon = loader.load(file);
        if(addon == null) return null;
        addonList.add(addon);
        addon.load();
        return addon;
    }

    public void loadAddon(SubstanceAddon addon)
    {
        addonList.add(addon);
        addon.load();
    }

    public <T extends SubstanceAddon> T getAddon(String addonId)
    {
        return (T) addonList.stream().filter(addon -> addon.getId().equals(addonId)).findFirst().orElse(null);
    }

    @Override
    public void unload() {
        addonList.forEach(SubstanceManager::unload);
    }
}
