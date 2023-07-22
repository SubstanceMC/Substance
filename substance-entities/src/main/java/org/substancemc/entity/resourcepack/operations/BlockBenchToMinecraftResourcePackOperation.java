package org.substancemc.entity.resourcepack.operations;

import org.substancemc.core.resourcepack.ResourcePackOperation;
import org.substancemc.entity.blockbench.structure.BlockBenchModel;
import org.substancemc.entity.resourcepack.generator.model.ResourcePackEntityModelGenerator;
import org.substancemc.entity.resourcepack.generator.texture.ResourcePackEntityTextureGenerator;

import java.util.List;

public class BlockBenchToMinecraftResourcePackOperation implements ResourcePackOperation {

    private final List<BlockBenchModel> models;

    private final ResourcePackEntityModelGenerator modelGenerator = new ResourcePackEntityModelGenerator();
    private final ResourcePackEntityTextureGenerator textureGenerator = new ResourcePackEntityTextureGenerator();


    public BlockBenchToMinecraftResourcePackOperation(List<BlockBenchModel> models)
    {
        this.models = models;
    }

    @Override
    public void operate() {
        models.forEach(textureGenerator::generate);
        models.forEach(modelGenerator::generate);
    }
}
