package org.substancemc.entity.resourcepack.generator.blockbench.model;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.substancemc.core.resourcepack.generator.model.ResourcePackModelLinkGenerator;
import org.substancemc.core.resourcepack.structure.minecraft.model.ResourcePackModelLink;
import org.substancemc.entity.SubstanceEntityAddon;
import org.substancemc.entity.blockbench.BlockBenchEntityBone;
import org.substancemc.entity.blockbench.structure.BlockBenchModel;
import org.substancemc.entity.blockbench.structure.element.BlockBenchModelBone;

import java.util.Arrays;
import java.util.List;

public class BlockBenchLinkGenerator extends ResourcePackModelLinkGenerator {
    public BlockBenchLinkGenerator() {
        super(SubstanceEntityAddon.get().getModelMaterial());
    }

    @Override
    public void generate(List<String> context) {
        ResourcePackModelLink link = generateWithReturn(context);
        Arrays.stream(link.getOverrides()).toList().stream().filter(override -> context.contains(override.getModel())).forEach(override -> {
            String boneLocator = override.getModel();
            BlockBenchModel model = SubstanceEntityAddon.get().getBlockBenchManager().getModelByLocator(boneLocator);
            BlockBenchModelBone bone = SubstanceEntityAddon.get().getBlockBenchManager().getBoneByLocator(boneLocator);
            ItemStack itemStack = new ItemStack(SubstanceEntityAddon.get().getModelMaterial());
            ItemMeta meta = itemStack.getItemMeta();
            meta.setCustomModelData(override.getPredicate().getCustomModelData());
            itemStack.setItemMeta(meta);
            SubstanceEntityAddon.get().getBlockBenchManager().registerParsedBones(model, new BlockBenchEntityBone(bone, itemStack));

        });
    }
}
