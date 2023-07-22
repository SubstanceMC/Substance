package org.substancemc.core.compatibility.impl;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.substancemc.core.SubstancePlugin;
import org.substancemc.core.compatibility.Compatibility;
import org.substancemc.core.compatibility.CompatibilityAction;

public class ProtocolLibCompatibility implements Compatibility {

    private boolean checkedAlready;
    private boolean isPresent;
    @Override
    public boolean checkCompatible() {
        if(checkedAlready) return isPresent;
        Plugin protocolLib = Bukkit.getPluginManager().getPlugin("ProtocolLib");
        boolean isPresent = protocolLib != null && protocolLib.isEnabled();
        this.isPresent = isPresent;
        checkedAlready = true;
        if(!isPresent) SubstancePlugin.get().getLogger().warning("ProtocolLib is not present, which may produce unexpected behavior for resource pack loading. You may want to install it.");
        return isPresent;
    }

    public ProtocolManager getProtocolManager()
    {
        return executeAction(new CompatibilityAction<>() {
            @Override
            public ProtocolManager executeAbsent() {
                return null;
            }

            @Override
            public ProtocolManager executePresent() {
                return ProtocolLibrary.getProtocolManager();
            }
        });
    }

    public void sendResourcePack(Player player)
    {
        executeAction(new CompatibilityAction<>() {
            @Override
            public Void executeAbsent() {
                player.setResourcePack(SubstancePlugin.get().getResourcePackManager().getPackProvider().getURL(), SubstancePlugin.get().getResourcePackManager().getPackProvider().getHash(), "<red>To fully experience this server, a custom resource pack is mandatory. Download it now or leave this server.</red>", false);
                return null;
            }

            @Override
            public Void executePresent() {
                PacketContainer handle = getProtocolManager().createPacket(PacketType.Play.Server.RESOURCE_PACK_SEND);
                handle.getStrings().write(0, SubstancePlugin.get().getResourcePackManager().getPackProvider().getURL());
                handle.getStrings().write(1, SubstancePlugin.get().getResourcePackManager().getPackProvider().getChecksum());
                handle.getBooleans().write(0, true);
                handle.getChatComponents().write(0, WrappedChatComponent.fromText("To fully experience this server, a custom resource pack is mandatory. Download it now or leave this server."));
                getProtocolManager().sendServerPacket(player, handle);
                return null;
            }
        });
    }
}
