package org.substancemc.entity.entity.spawnegg.command;

import dev.jorel.commandapi.CommandAPICommand;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.substancemc.core.command.SubstanceCommandHolder;
import org.substancemc.entity.SubstanceEntityAddon;

public class SpawnEggCommand implements SubstanceCommandHolder {

    public CommandAPICommand getCommand() {
        return new CommandAPICommand("spawneggs")
                .withPermission("substance.entity.spawneggs")
                .executesPlayer((player, args) -> {
                    Inventory inventory = Bukkit.createInventory(null, 54, Component.translatable("substance.entity.spawneggs.inventory.name", "Substance Spawn Eggs"));
                    SubstanceEntityAddon.get().getEntityManager().getEntityTypes().forEach(entityType -> {
                        if(entityType.getSpawnEgg() != null) inventory.addItem(entityType.getSpawnEgg());
                    });
                    player.openInventory(inventory);
                });
    }
}
