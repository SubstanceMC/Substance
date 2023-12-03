package org.substancemc.entity.entity.spawnegg;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.substancemc.core.SubstancePlugin;
import org.substancemc.entity.SubstanceEntityAddon;
import org.substancemc.entity.blockbench.BlockBenchEntity;
import org.substancemc.entity.entity.SubstanceEntity;
import org.substancemc.entity.entity.SubstanceEntityType;
import org.substancemc.item.SubstanceItemSnippet;
import org.substancemc.item.event.SubstanceItemEvent;

import java.util.Objects;

public class SpawnEggSnippet implements SubstanceItemSnippet<PlayerInteractEvent> {

    @Override
    public boolean isCaseMet(String otherCase) {
        return SubstanceEntityAddon.get().getEntityManager().getEntityTypes().stream().anyMatch(type -> otherCase.equals("spawn_egg_" + type.getId()));
    }

    @Override
    public void call(SubstanceItemEvent<PlayerInteractEvent> context) {
        if(!context.getContext().getAction().equals(Action.RIGHT_CLICK_BLOCK)) return;
        EquipmentSlot slot = context.getContext().getHand();
        assert slot != null;
        context.getContext().getPlayer().swingHand(slot);
        if(!context.getContext().getPlayer().getGameMode().equals(GameMode.CREATIVE))
        {
            ItemStack stack = context.getItem().getPhysical();
            if(stack.getAmount() == 1) stack = null;
            else stack.setAmount(stack.getAmount() - 1);
            context.getContext().getPlayer().getInventory().setItem(slot, stack);
        }
        String entityTypeId = context.getItem().getType().getId().replaceFirst("spawn_egg_", "");
        SubstanceEntityType type = SubstanceEntityAddon.get().getEntityManager().getEntityTypeById(entityTypeId);
        if(type == null) return;
        Location spawnLocation = Objects.requireNonNull(context.getContext().getClickedBlock()).getLocation().add(0.5, 1, 0.5);
        SubstanceEntity entity = BlockBenchEntity.spawn(type, spawnLocation);
    }
}
