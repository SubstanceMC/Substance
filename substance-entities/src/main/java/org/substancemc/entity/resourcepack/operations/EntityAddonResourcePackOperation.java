package org.substancemc.entity.resourcepack.operations;

import org.substancemc.core.SubstancePlugin;
import org.substancemc.core.resourcepack.ResourcePackOperation;
import org.substancemc.core.resourcepack.generator.model.ResourcePackModelLinkGenerator;
import org.substancemc.core.resourcepack.structure.minecraft.atlas.ResourcePackAtlasEntry;
import org.substancemc.entity.SubstanceEntityAddon;
import org.substancemc.entity.resourcepack.operations.blockbench.BlockBenchToMinecraftResourcePackOperation;

public class EntityAddonResourcePackOperation implements ResourcePackOperation {


    @Override
    public void operate() {
        new BlockBenchToMinecraftResourcePackOperation(SubstanceEntityAddon.get().getBlockBenchManager().getModels()).operate();
        new ResourcePackModelLinkGenerator(SubstanceEntityAddon.get().getModelMaterial()).generate(SubstanceEntityAddon.get().getBlockBenchManager().getModelLocators());
        SubstancePlugin.get().getResourcePackManager().addAtlasEntry("blocks", new ResourcePackAtlasEntry("entity", "directory"));
    }
}
