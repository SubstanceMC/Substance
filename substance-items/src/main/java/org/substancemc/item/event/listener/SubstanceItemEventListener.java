package org.substancemc.item.event.listener;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.substancemc.item.SubstanceItem;
import org.substancemc.item.SubstanceItemAddon;
import org.substancemc.item.SubstanceItemType;
import org.substancemc.item.event.SubstanceItemEvent;

public class SubstanceItemEventListener implements Listener {


    @EventHandler
    public void onInteract(PlayerInteractEvent event)
    {
        if(!event.hasItem()) return;
        SubstanceItemType substanceItemType = SubstanceItemAddon.get().getItemManager().getRegisteredItemTypeByItem(event.getItem());
        if(substanceItemType == null) return;
        SubstanceItemEvent<PlayerInteractEvent> itemEvent = new SubstanceItemEvent<>(new SubstanceItem(substanceItemType, event.getItem()), event);
        Bukkit.getPluginManager().callEvent(itemEvent);
        event.setCancelled(itemEvent.isCancelled());
    }

}
