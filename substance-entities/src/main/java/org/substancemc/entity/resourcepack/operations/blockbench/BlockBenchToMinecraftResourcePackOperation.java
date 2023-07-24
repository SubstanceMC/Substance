package org.substancemc.entity.resourcepack.operations.blockbench;

import org.substancemc.core.resourcepack.ResourcePackOperation;
import org.substancemc.entity.blockbench.structure.BlockBenchModel;
import org.substancemc.entity.resourcepack.generator.blockbench.model.ResourcePackBlockBenchModelGenerator;
import org.substancemc.entity.resourcepack.generator.blockbench.texture.ResourcePackEntityTextureGenerator;

import java.util.List;

public class BlockBenchToMinecraftResourcePackOperation implements ResourcePackOperation {

    private final List<BlockBenchModel> models;

    private final ResourcePackBlockBenchModelGenerator modelGenerator = new ResourcePackBlockBenchModelGenerator();
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
