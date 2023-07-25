package org.substancemc.entity.resourcepack.generator.spawnegg.model;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TranslatableComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.substancemc.core.resourcepack.generator.model.ResourcePackModelFile;
import org.substancemc.core.resourcepack.generator.model.ResourcePackModelLinkGenerator;
import org.substancemc.core.resourcepack.structure.minecraft.model.ResourcePackModelLink;
import org.substancemc.entity.SubstanceEntityAddon;
import org.substancemc.entity.entity.SubstanceEntityType;

import java.util.Arrays;
import java.util.List;

public class SpawnEggLinkGenerator extends ResourcePackModelLinkGenerator {
    public SpawnEggLinkGenerator() {
        super(Material.PAPER);
    }

    @Override
    public void generate(List<String> context) {
        ResourcePackModelLink link = generateWithReturn(context);
        Arrays.stream(link.getOverrides()).toList().stream().filter(override -> context.contains(override.getModel())).forEach(override -> {
            SubstanceEntityType type = SubstanceEntityAddon.get().getEntityManager().getEntityTypes().stream().filter(entityType -> new ResourcePackModelFile("item/spawn_egg/" + entityType.getId() + ".json").getResourceLocator().equals(override.getModel())).findAny().orElse(null);
            if(type == null) return;
            ItemStack spawnEgg = new ItemStack(getMaterial());
            ItemMeta meta = spawnEgg.getItemMeta();
            assert meta != null;
            meta.setCustomModelData(override.getPredicate().getCustomModelData());
            TranslatableComponent nameComponent = Component.translatable().key("entity.spawn_egg." + type.getId()).fallback("Spawn " + type.getName()).color(NamedTextColor.WHITE).decoration(TextDecoration.ITALIC, false).build();
            meta.displayName(nameComponent);
            spawnEgg.setItemMeta(meta);
            type.setSpawnEgg(spawnEgg);
        });
    }
}
