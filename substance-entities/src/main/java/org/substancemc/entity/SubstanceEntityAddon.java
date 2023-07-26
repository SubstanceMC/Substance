package org.substancemc.entity;

import org.bukkit.Material;
import org.substancemc.core.SubstancePlugin;
import org.substancemc.core.addon.AddonManager;
import org.substancemc.core.addon.SubstanceAddon;
import org.substancemc.core.util.file.DataFolderFile;
import org.substancemc.entity.blockbench.BlockBenchManager;
import org.substancemc.entity.entity.SubstanceEntityManager;
import org.substancemc.entity.entity.spawnegg.command.SpawnEggCommand;
import org.substancemc.entity.resourcepack.operations.EntityAddonResourcePackOperation;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SubstanceEntityAddon implements SubstanceAddon {

    @Override
    public String getVersion() {
        return "0.1.1-UNUSABLE";
    }

    @Override
    public String getId() {
        return "substance-entity";
    }

    @Override
    public String[] getAuthors() {
        return new String[]{"SubstanceMC"};
    }

    @Override
    public String[] getDependencyAddons() {

        return new String[]{"substance-item"};
    }

    private BlockBenchManager blockBenchManager;
    private SubstanceEntityManager entityManager;
    @Override
    public void load() {
        saveDefaultConfig("config_entity");
        if(isSpawnEggEnabled()) SubstancePlugin.get().getResourceExtractor().extract(new DataFolderFile("pack/assets/substance/models/item/spawn_egg_template.json"), getClass().getClassLoader());
        blockBenchManager = new BlockBenchManager();
        entityManager = new SubstanceEntityManager();
        blockBenchManager.load();
        entityManager.load();
        SubstancePlugin.get().getResourcePackManager().addOperation(new EntityAddonResourcePackOperation());
        SubstancePlugin.get().getCommandManager().addSubCommand(new SpawnEggCommand());

    }

    public boolean isSpawnEggEnabled()
    {
        return !getConfig("config_entity").contains("settings.spawnEgg") || getConfig("config_entity").getBoolean("settings.spawnEgg");
    }

    public boolean isHurtColorEnabled()
    {
        return !getConfig("config_entity").contains("settings.hurtColor") || getConfig("config_entity").getBoolean("settings.hurtColor");
    }

    public Material getModelMaterial()
    {
        return Material.getMaterial(Objects.requireNonNull(getConfig("config_entity").getString("settings.modelMaterial")));
    }

    @Override
    public void unload() {
        blockBenchManager.unload();
    }

    public BlockBenchManager getBlockBenchManager()
    {
        return blockBenchManager;
    }
    public SubstanceEntityManager getEntityManager()
    {
        return entityManager;
    }
    public static SubstanceEntityAddon get()
    {
        return SubstancePlugin.get().getAddonManager().getAddon(AddonManager.ENTITY_ADDON);
    }

}
