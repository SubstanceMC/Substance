package org.substancemc.entity.entity.spawnegg;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.event.player.PlayerInteractEvent;
import org.substancemc.entity.SubstanceEntityAddon;
import org.substancemc.item.SubstanceItemSnippet;
import org.substancemc.item.event.SubstanceItemEvent;

public class SpawnEggSnippet implements SubstanceItemSnippet<PlayerInteractEvent> {

    @Override
    public boolean isCaseMet(String otherCase) {
        return SubstanceEntityAddon.get().getEntityManager().getEntityTypes().stream().anyMatch(type -> otherCase.equals("spawn_egg_" + type.getId()));
    }

    @Override
    public void call(SubstanceItemEvent<PlayerInteractEvent> context) {
        Bukkit.broadcast(Component.text(context.getItem().getType().getId()));
    }
}
