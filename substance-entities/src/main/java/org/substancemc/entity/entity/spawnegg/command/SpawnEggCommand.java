package org.substancemc.entity.entity.spawnegg.command;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;
import org.substancemc.entity.SubstanceEntityAddon;

public class SpawnEggCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(commandSender instanceof Player)
        {
            Inventory inventory = Bukkit.createInventory(null, 54, "Spawn eggs");
            SubstanceEntityAddon.get().getEntityManager().getEntityTypes().forEach(entityType ->
            {
                if(entityType.getSpawnEgg() != null)
                {
                    inventory.addItem(entityType.getSpawnEgg());
                }
            });
            ((Player) commandSender).openInventory(inventory);
        }
        return false;
    }
}
