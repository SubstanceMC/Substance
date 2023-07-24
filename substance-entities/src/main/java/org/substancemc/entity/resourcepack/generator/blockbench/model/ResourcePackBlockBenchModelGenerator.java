package org.substancemc.entity.resourcepack.generator.blockbench.model;

import org.substancemc.core.resourcepack.generator.model.ResourcePackModelGenerator;
import org.substancemc.entity.blockbench.structure.BlockBenchModel;

public class ResourcePackBlockBenchModelGenerator extends ResourcePackModelGenerator<BlockBenchModel>  {

    public ResourcePackBlockBenchModelGenerator() {
        super(new BlockBenchModelGeneratorPreProcessor());
    }

}
