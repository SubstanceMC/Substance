package org.substancemc.item;

import org.substancemc.core.SubstancePlugin;
import org.substancemc.core.addon.SubstanceAddon;
import org.substancemc.core.util.config.SubstanceConfiguration;
import org.substancemc.item.command.ItemCommand;

public class SubstanceItemAddon implements SubstanceAddon {
    @Override
    public String getVersion() {
        return "0.1.0-UNSUPPORTED";
    }

    @Override
    public String getId() {
        return "substance-item";
    }

    @Override
    public String[] getAuthors() {
        return new String[] {"SubstanceMC"};
    }

    private final SubstanceItemManager itemManager = new SubstanceItemManager();

    @Override
    public void load() {
        saveDefaultConfig("config_items");
        itemManager.load();
        SubstancePlugin.get().getCommandManager().addSubCommand(new ItemCommand());
    }

    @Override
    public void unload() {
        itemManager.unload();
    }

    public SubstanceConfiguration getItemConfig()
    {
        return getConfig("config_items");
    }

    public static SubstanceItemAddon get()
    {
        return SubstancePlugin.get().getAddonManager().getAddon("substance-item");
    }

    public SubstanceItemManager getItemManager()
    {
        return itemManager;
    }
}
