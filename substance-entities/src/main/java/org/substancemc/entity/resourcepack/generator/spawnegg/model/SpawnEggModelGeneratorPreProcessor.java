package org.substancemc.entity.resourcepack.generator.spawnegg.model;

import org.substancemc.core.resourcepack.generator.GeneratorPreProcessor;
import org.substancemc.core.resourcepack.generator.model.ResourcePackModelFile;
import org.substancemc.core.resourcepack.generator.texture.ResourcePackTextureFile;
import org.substancemc.core.resourcepack.structure.substance.ResourcePackModel;
import org.substancemc.entity.SubstanceEntityAddon;
import org.substancemc.entity.entity.SubstanceEntityType;

import java.util.HashMap;
import java.util.Map;

public class SpawnEggModelGeneratorPreProcessor implements GeneratorPreProcessor<SubstanceEntityType, Map<ResourcePackModelFile, ResourcePackModel>> {
    @Override
    public Map<ResourcePackModelFile, ResourcePackModel> process(SubstanceEntityType original) {
        Map<ResourcePackModelFile, ResourcePackModel> processed = new HashMap<>();
        ResourcePackModelFile spawnEggModelFile = new ResourcePackModelFile("item/spawn_egg/" + original.getId() + ".json");
        ResourcePackModel spawnEggModel = new ResourcePackModel();
        spawnEggModel.setParent("substance:item/spawn_egg_template");
        HashMap<String, String> textures = new HashMap<>();
        textures.put("layer0", new ResourcePackTextureFile("item/spawn_egg/" + original.getId() + ".png").getResourceLocator());
        spawnEggModel.setTextures(textures);
        processed.put(spawnEggModelFile, spawnEggModel);
        SubstanceEntityAddon.get().getEntityManager().getSpawnEggManager().addSpawnEggResourceLocator(spawnEggModelFile.getResourceLocator());
        return processed;
    }
}
