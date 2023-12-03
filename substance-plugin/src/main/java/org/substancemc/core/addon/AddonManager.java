package org.substancemc.core.addon;

import org.substancemc.core.SubstancePlugin;
import org.substancemc.core.addon.snippet.AddonSnippet;
import org.substancemc.core.addon.snippet.AddonSnippetManager;
import org.substancemc.core.util.file.DataFolderFile;
import org.substancemc.core.util.structure.SubstanceManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AddonManager implements SubstanceManager, AddonSnippetManager {

    private final List<SubstanceAddon> addonList = new ArrayList<>();
    private final AddonLoader loader = new AddonLoader(new DataFolderFile("addons"));

    private final List<AddonSnippet<?, ?>> addonSnippets = new ArrayList<>();

    public static String ENTITY_ADDON = "substance-entity";
    @Override
    public void load() {
        addonList.addAll(loader.load());
        addonList.forEach(this::loadAddon);
    }
    public SubstanceAddon loadAddon(DataFolderFile file) throws IOException, ClassNotFoundException, IllegalAccessException {
        SubstanceAddon addon = loader.load(file);
        if(addon == null) return null;
        addonList.add(addon);
        loadAddon(addon);
        return addon;
    }

    public void loadAddon(SubstanceAddon addon)
    {
        addon.load();
        addonSnippets.addAll(List.of(addon.getSnippets()));
        SubstancePlugin.get().getLogger().info(addon.getId() + " v" + addon.getVersion() + " loaded. (Addon by " + String.join(", ", addon.getAuthors()) + ")");
    }

    public <T extends SubstanceAddon> T getAddon(String addonId)
    {
        return (T) addonList.stream().filter(addon -> addon.getId().equals(addonId)).findFirst().orElse(null);
    }

    public List<SubstanceAddon> getAddons()
    {
        return addonList;
    }

    @Override
    public void unload() {
        addonList.forEach(SubstanceManager::unload);
    }

    @Override
    public List<AddonSnippet<?, ?>> getAllSnippets() {
        return addonSnippets;
    }
}
