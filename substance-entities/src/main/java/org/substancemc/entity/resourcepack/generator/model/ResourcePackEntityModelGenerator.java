package org.substancemc.entity.resourcepack.generator.model;

import org.substancemc.entity.blockbench.structure.BlockBenchModel;
import org.substancemc.core.resourcepack.generator.model.ResourcePackModelGenerator;

public class ResourcePackEntityModelGenerator extends ResourcePackModelGenerator<BlockBenchModel>  {

    public ResourcePackEntityModelGenerator() {
        super(new EntityModelGeneratorPreProcessor());
    }

}
