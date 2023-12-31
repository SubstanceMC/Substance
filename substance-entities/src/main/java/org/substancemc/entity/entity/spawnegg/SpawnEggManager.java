package org.substancemc.entity.entity.spawnegg;

import org.substancemc.core.util.file.DataFolderFile;
import org.substancemc.core.util.structure.SubstanceManager;
import org.substancemc.entity.SubstanceEntityAddon;
import org.substancemc.entity.entity.SubstanceEntityType;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SpawnEggManager implements SubstanceManager {

    private final HashMap<SubstanceEntityType, HashMap<DataFolderFile, Color>> spawnEggEntityMap = new HashMap<>();

    private final List<String> spawnEggModelResourceLocations = new ArrayList<>();

    @Override
    public void load() {
        SubstanceEntityAddon.get().getEntityManager().getEntityTypes().forEach(entityType -> {
            if(entityType.getSpawnEggBaseFile() == null || entityType.getSpawnEggBase() == null || entityType.getSpawnEggOverlayFile() == null || entityType.getSpawnEggOverlay() == null) return;
            HashMap<DataFolderFile, Color> spawnEggMap = new HashMap<>();
            spawnEggMap.put(entityType.getSpawnEggBaseFile(), entityType.getSpawnEggBase());
            spawnEggMap.put(entityType.getSpawnEggOverlayFile(), entityType.getSpawnEggOverlay());
            spawnEggEntityMap.put(entityType, spawnEggMap);
        });
    }

    public HashMap<SubstanceEntityType, HashMap<DataFolderFile, Color>> getSpawnEggEntityMap()
    {
        return spawnEggEntityMap;
    }

    @Override
    public void unload() {
        spawnEggModelResourceLocations.clear();
        spawnEggEntityMap.clear();
    }

    public List<String> getSpawnEggModelResourceLocations()
    {
        return spawnEggModelResourceLocations;
    }

    public void addSpawnEggResourceLocator(String locator)
    {
        spawnEggModelResourceLocations.add(locator);
    }
}
