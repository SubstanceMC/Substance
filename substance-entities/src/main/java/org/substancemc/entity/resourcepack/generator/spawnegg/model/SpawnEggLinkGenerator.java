package org.substancemc.entity.resourcepack.generator.spawnegg.model;

import org.bukkit.Material;
import org.substancemc.core.resourcepack.generator.model.ResourcePackModelFile;
import org.substancemc.core.resourcepack.generator.model.ResourcePackModelLinkGenerator;
import org.substancemc.core.resourcepack.structure.minecraft.model.ResourcePackModelLink;
import org.substancemc.entity.SubstanceEntityAddon;
import org.substancemc.entity.entity.SubstanceEntityType;
import org.substancemc.item.SubstanceItemType;

import java.util.Arrays;
import java.util.List;

public class SpawnEggLinkGenerator extends ResourcePackModelLinkGenerator {
    public SpawnEggLinkGenerator() {
        super(Material.CARROT_ON_A_STICK);
    }
    @Override
    public void generate(List<String> context) {
        ResourcePackModelLink link = generateWithReturn(context);
        Arrays.stream(link.getOverrides()).toList().stream().filter(override -> context.contains(override.getModel())).forEach(override -> {
            SubstanceEntityType type = SubstanceEntityAddon.get().getEntityManager().getEntityTypes().stream().filter(entityType -> new ResourcePackModelFile("item/spawn_egg/" + entityType.getId() + ".json").getResourceLocator().equals(override.getModel())).findAny().orElse(null);
            if(type == null) return;
            SubstanceItemType spawnEggType = new SubstanceItemType(getMaterial(), override.getPredicate().getCustomModelData(), "spawn_egg_" + type.getId(), "Spawn " + type.getName(), "entity.spawn_egg." + type.getId());
            SubstanceEntityAddon.get().getItemAddon().getItemManager().registerExternalItemType(spawnEggType);
            type.setSpawnEgg(spawnEggType.createDefaultItem().getPhysical());
        });
    }
}
