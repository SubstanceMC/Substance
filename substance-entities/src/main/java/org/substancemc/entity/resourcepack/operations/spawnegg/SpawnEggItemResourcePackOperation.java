package org.substancemc.entity.resourcepack.operations.spawnegg;

import org.substancemc.core.resourcepack.ResourcePackOperation;
import org.substancemc.entity.SubstanceEntityAddon;
import org.substancemc.entity.resourcepack.generator.spawnegg.model.SpawnEggLinkGenerator;
import org.substancemc.entity.resourcepack.generator.spawnegg.model.SpawnEggModelGenerator;
import org.substancemc.entity.resourcepack.generator.spawnegg.texture.SpawnEggTextureGenerator;

public class SpawnEggItemResourcePackOperation implements ResourcePackOperation {

    private final SpawnEggModelGenerator modelGenerator = new SpawnEggModelGenerator();
    private final SpawnEggLinkGenerator linkGenerator = new SpawnEggLinkGenerator();
    private final SpawnEggTextureGenerator textureGenerator = new SpawnEggTextureGenerator();

    @Override
    public void operate() {
        SubstanceEntityAddon.get().getEntityManager().getSpawnEggManager().getSpawnEggEntityMap().keySet().forEach(textureGenerator::generate);
        SubstanceEntityAddon.get().getEntityManager().getSpawnEggManager().getSpawnEggEntityMap().keySet().forEach(modelGenerator::generate);
        linkGenerator.generate(SubstanceEntityAddon.get().getEntityManager().getSpawnEggManager().getSpawnEggModelResourceLocations());
    }
}
