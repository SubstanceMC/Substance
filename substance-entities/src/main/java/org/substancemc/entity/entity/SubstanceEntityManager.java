package org.substancemc.entity.entity;

import org.bukkit.configuration.ConfigurationSection;
import org.substancemc.core.SubstancePlugin;
import org.substancemc.core.util.structure.SubstanceManager;
import org.substancemc.entity.SubstanceEntityAddon;
import org.substancemc.entity.entity.spawnegg.SpawnEggManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SubstanceEntityManager implements SubstanceManager {

    private final List<SubstanceEntityType> entityTypes = new ArrayList<>();

    private final SpawnEggManager spawnEggManager = new SpawnEggManager();

    private final List<SubstanceEntity> entities = new ArrayList<>();



    @Override
    public void load() {
        if(!SubstanceEntityAddon.get().getConfig("config_entity").isConfigurationSection("entities")) return;
        ConfigurationSection section = SubstanceEntityAddon.get().getConfig("config_entity").getConfigurationSection("entities");
        assert section != null;
        section.getKeys(false).forEach(key -> {
            SubstanceEntityType entityType = SubstanceEntityAddon.get().getConfig("config_entity").getSubstanceObject("entities." + key, SubstanceEntityType.class);
            entityTypes.add(entityType);
            SubstancePlugin.get().getLogger().warning("Registered entity type " + entityType.getId() + " using model " + entityType.getModelId());
        });
        spawnEggManager.load();
    }

    public List<SubstanceEntityType> getEntityTypes()
    {
        return entityTypes;
    }

    public SubstanceEntityType getEntityTypeById(String id)
    {
        return entityTypes.stream().filter(entityType -> entityType.getId().equals(id)).findAny().orElse(null);
    }

    public SpawnEggManager getSpawnEggManager()
    {
        return spawnEggManager;
    }

    public void registerEntities(SubstanceEntity... entities)
    {
        this.entities.addAll(Arrays.stream(entities).toList());
    }

    public List<SubstanceEntity> getEntities()
    {
        return entities;
    }

    @Override
    public void unload() {
        entityTypes.clear();
    }
}
