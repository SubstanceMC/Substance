package org.substancemc.entity;

import org.substancemc.core.SubstancePlugin;
import org.substancemc.core.addon.AddonManager;
import org.substancemc.core.addon.SubstanceAddon;
import org.substancemc.entity.blockbench.BlockBenchManager;
import org.substancemc.entity.resourcepack.operations.EntityAddonResourcePackOperation;

public class SubstanceEntityAddon implements SubstanceAddon {

    @Override
    public String getVersion() {
        return "0.1.0-UNUSABLE";
    }

    @Override
    public String getId() {
        return "substance-entity";
    }

    @Override
    public String[] getAuthors() {
        return new String[]{"SubstanceMC"};
    }

    private BlockBenchManager blockBenchManager;

    @Override
    public void load() {
        blockBenchManager = new BlockBenchManager();
        blockBenchManager.load();
        SubstancePlugin.get().getResourcePackManager().addOperation(new EntityAddonResourcePackOperation());
        SubstancePlugin.get().getLogger().info(getId() + " v" + getVersion() + " loaded. (Addon by " + String.join(", ", getAuthors()) + ")");
    }

    @Override
    public void unload() {
        blockBenchManager.unload();
    }

    public BlockBenchManager getBlockBenchManager()
    {
        return blockBenchManager;
    }

    public static SubstanceEntityAddon get()
    {
        return SubstancePlugin.get().getAddonManager().getAddon(AddonManager.ENTITY_ADDON);
    }

}
