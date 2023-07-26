package org.substancemc.core.addon;

import org.substancemc.core.SubstancePlugin;
import org.substancemc.core.util.file.DataFolderFile;
import org.substancemc.core.util.structure.SubstanceManager;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class AddonManager implements SubstanceManager {

    private final List<SubstanceAddon> addonList = new ArrayList<>();
    private final List<SubstanceAddon> loadedAddons = new ArrayList<>();
    private final AddonLoader loader = new AddonLoader(new DataFolderFile("addons"));

    public static String ENTITY_ADDON = "substance-entity";
    @Override
    public void load() {
        addonList.addAll(loader.load());
        sortAddonsWithNoDependencies();
        sortAddonsWithDependencies();
        loadedAddons.forEach(addon -> {
            addon.load();
            SubstancePlugin.get().getLogger().info(addon.getId() + " v" + addon.getVersion() + " loaded. (Addon by " + String.join(", ", addon.getAuthors()) + ")");
        });
    }

    private void sortAddonsWithNoDependencies()
    {
        loadedAddons.addAll(addonList.stream().filter(addon -> addon.getDependencyAddons().length == 0).toList());
    }

    private void sortAddonsWithDependencies()
    {
        List<SubstanceAddon> addable = addonList.stream().filter(addon -> {
            if(loadedAddons.contains(addon)) return false;
            for(String dependency : addon.getDependencyAddons())
            {
                if(loadedAddons.stream().filter(loaded -> loaded.getId().equals(dependency)).findAny().orElse(null) == null) return false;
            }
            return true;
        }).toList();
        if(addable.size() > 0)
        {
            loadedAddons.addAll(addable);
            sortAddonsWithDependencies();
        }else {
            List<SubstanceAddon> unloadAble = addonList.stream().filter(addon -> !loadedAddons.contains(addon)).toList();
            if(unloadAble.size() > 0)
            {
                List<String> unloadAbleIds = new ArrayList<>();
                unloadAble.forEach(addon -> unloadAbleIds.add(addon.getId()));
                SubstancePlugin.get().getLogger().severe("The following addons can not be loaded because of missing dependency addons: " + String.join(",", unloadAbleIds));
            }
        }
    }

    public SubstanceAddon loadAddon(DataFolderFile file)
    {
        SubstanceAddon addon = loader.load(file);
        if(addon == null) return null;
        loadedAddons.add(addon);
        addon.load();
        return addon;
    }

    public void loadAddon(SubstanceAddon addon)
    {
        loadedAddons.add(addon);
        addon.load();
    }

    public <T extends SubstanceAddon> T getAddon(String addonId)
    {
        return (T) loadedAddons.stream().filter(addon -> addon.getId().equals(addonId)).findFirst().orElse(null);
    }

    public List<SubstanceAddon> getAddons()
    {
        return addonList;
    }

    @Override
    public void unload() {
        loadedAddons.forEach(SubstanceManager::unload);
    }
}
