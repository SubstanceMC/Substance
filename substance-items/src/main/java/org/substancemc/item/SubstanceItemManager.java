package org.substancemc.item;

import org.bukkit.configuration.ConfigurationSection;
import org.substancemc.core.util.structure.SubstanceManager;

import java.util.ArrayList;
import java.util.List;

public class SubstanceItemManager implements SubstanceManager {

    private final List<SubstanceItem> registeredItems = new ArrayList<>();

    @Override
    public void load() {
        if(!SubstanceItemAddon.get().getItemConfig().isConfigurationSection("")) return;
        ConfigurationSection section = SubstanceItemAddon.get().getItemConfig().getConfigurationSection("");
        assert section != null;
        section.getKeys(false).forEach(key -> {
            registeredItems.add(SubstanceItemAddon.get().getItemConfig().getSubstanceObject(key, SubstanceItem.class));
        });
    }

    public void registerExternalItem(SubstanceItem item)
    {

    }

    @Override
    public void unload() {
        registeredItems.clear();
    }

    public List<SubstanceItem> getRegisteredItems()
    {
        return registeredItems;
    }

    public List<String> getRegisteredItemIds()
    {
        List<String> itemIds = new ArrayList<>();
        registeredItems.forEach(item -> itemIds.add(item.getId()));
        return itemIds;
    }
}
