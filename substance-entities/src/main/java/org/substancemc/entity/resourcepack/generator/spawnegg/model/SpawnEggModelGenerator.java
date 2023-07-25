package org.substancemc.entity.resourcepack.generator.spawnegg.model;

import org.substancemc.core.resourcepack.generator.model.ResourcePackModelGenerator;
import org.substancemc.entity.entity.SubstanceEntityType;

public class SpawnEggModelGenerator extends ResourcePackModelGenerator<SubstanceEntityType> {
    public SpawnEggModelGenerator() {
        super(new SpawnEggModelGeneratorPreProcessor());
    }
}
