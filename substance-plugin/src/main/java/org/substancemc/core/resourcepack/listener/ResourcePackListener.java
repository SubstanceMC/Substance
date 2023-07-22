package org.substancemc.core.resourcepack.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerResourcePackStatusEvent;
import org.substancemc.core.SubstancePlugin;

import java.util.HashMap;
import java.util.UUID;

public class ResourcePackListener implements Listener {

    private final HashMap<UUID, Integer> playerTries = new HashMap<>();
    int maxTries = 3;

    @EventHandler
    public void applyResourcePack(PlayerJoinEvent event)
    {
        SubstancePlugin.get().getResourcePackManager().applyPack(event.getPlayer());
    }

    @EventHandler
    public void onFailApplyResourcePack(PlayerResourcePackStatusEvent event)
    {
        if(event.getStatus().equals(PlayerResourcePackStatusEvent.Status.DECLINED))
            event.getPlayer().kickPlayer("<red>You have to accept the resource pack</red>");
        if(event.getStatus().equals(PlayerResourcePackStatusEvent.Status.FAILED_DOWNLOAD))
            if(playerTries.get(event.getPlayer().getUniqueId()) < maxTries){
                playerTries.put(event.getPlayer().getUniqueId(), playerTries.getOrDefault(event.getPlayer().getUniqueId(), 0) + 1);
                SubstancePlugin.get().getCompatibilityManager().PROTOCOL_LIB.sendResourcePack(event.getPlayer());
            }
            else {
                playerTries.remove(event.getPlayer().getUniqueId());
                event.getPlayer().sendMessage( "<red>The resourcepack download has failed three times. Please download it manually at <hover:show_text:Copy to clipboard><click:copy_to_clipboard:" + SubstancePlugin.get().getResourcePackManager().getPackProvider().getURL() + ">\"" + SubstancePlugin.get().getResourcePackManager().getPackProvider().getURL() + "\"</click></hover></red>");
            }
    }
}
