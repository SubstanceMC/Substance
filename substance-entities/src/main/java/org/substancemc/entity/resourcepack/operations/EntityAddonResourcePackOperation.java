package org.substancemc.entity.resourcepack.operations;

import org.bukkit.inventory.ItemStack;
import org.substancemc.core.SubstancePlugin;
import org.substancemc.core.resourcepack.ResourcePackOperation;
import org.substancemc.core.resourcepack.generator.model.ResourcePackModelLinkGenerator;
import org.substancemc.core.resourcepack.structure.minecraft.atlas.ResourcePackAtlasEntry;
import org.substancemc.entity.SubstanceEntityAddon;
import org.substancemc.entity.entity.SubstanceEntity;
import org.substancemc.entity.resourcepack.generator.blockbench.model.BlockBenchLinkGenerator;
import org.substancemc.entity.resourcepack.operations.blockbench.BlockBenchToMinecraftResourcePackOperation;
import org.substancemc.entity.resourcepack.operations.spawnegg.SpawnEggItemResourcePackOperation;

import java.util.HashMap;

public class EntityAddonResourcePackOperation implements ResourcePackOperation {


    @Override
    public void operate() {
        new BlockBenchToMinecraftResourcePackOperation(SubstanceEntityAddon.get().getBlockBenchManager().getModels()).operate();
        new BlockBenchLinkGenerator().generate(SubstanceEntityAddon.get().getBlockBenchManager().getModelLocators());
        if(SubstanceEntityAddon.get().isSpawnEggEnabled()) new SpawnEggItemResourcePackOperation().operate();
        SubstancePlugin.get().getResourcePackManager().addAtlasEntry("blocks", new ResourcePackAtlasEntry("entity", "directory"));
    }
}
