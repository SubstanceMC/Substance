package org.substancemc.core.resourcepack;

import org.substancemc.core.SubstancePlugin;
import org.substancemc.core.resourcepack.generator.atlas.ResourcePackAtlasGenerator;
import org.substancemc.core.resourcepack.structure.minecraft.atlas.ResourcePackAtlasEntry;
import org.substancemc.core.util.structure.SubstanceManager;
import org.apache.commons.io.FileUtils;
import org.bukkit.Material;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class ResourcePackManager implements SubstanceManager {

    private final HashMap<String, List<ResourcePackAtlasEntry>> atlases = new HashMap<>();
    private final List<ResourcePackOperation> operations = new ArrayList<>();

    private final ResourcePackAtlasGenerator atlasGenerator = new ResourcePackAtlasGenerator();
    @Override
    public void load() {
        operations.forEach(ResourcePackOperation::operate);
        atlases.entrySet().forEach(atlasGenerator::generate);
    }


    @Override
    public void unload() {
        atlases.clear();
        ResourcePackFile resourcePackFolder = new ResourcePackFile("");
        if(resourcePackFolder.getFile().exists()) {
            try {
                FileUtils.deleteDirectory(resourcePackFolder.getFile());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void addAtlasEntry(String atlas, ResourcePackAtlasEntry entry)
    {
        List<ResourcePackAtlasEntry> atlasEntryList = atlases.getOrDefault(atlas, new ArrayList<>());
        if(!atlasEntryList.contains(entry)) atlasEntryList.add(entry);
        atlases.put(atlas, atlasEntryList);
    }

    public void addOperation(ResourcePackOperation operation)
    {
        operations.add(operation);
    }

    public Material getModelMaterial()
    {
        return Material.getMaterial(Objects.requireNonNull(SubstancePlugin.get().getConfig().getString("resourcePack.modelMaterial")));
    }

}
