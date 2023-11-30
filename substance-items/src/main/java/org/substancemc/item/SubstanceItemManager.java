package org.substancemc.item;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.substancemc.core.SubstancePlugin;
import org.substancemc.core.util.structure.SubstanceManager;
import org.substancemc.item.event.SubstanceItemEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SubstanceItemManager implements SubstanceManager {

    private final List<SubstanceItemType> registeredItemTypes = new ArrayList<>();

    @Override
    public void load() {
        if(!SubstanceItemAddon.get().getItemConfig().isConfigurationSection("")) return;
        ConfigurationSection section = SubstanceItemAddon.get().getItemConfig().getConfigurationSection("");
        assert section != null;
        section.getKeys(false).forEach(key -> {
            registeredItemTypes.add(SubstanceItemAddon.get().getItemConfig().getSubstanceObject(key, SubstanceItemType.class));
        });
    }

    public void registerExternalItemType(SubstanceItemType item)
    {
        registeredItemTypes.add(item);
    }

    @Override
    public void unload() {
        registeredItemTypes.clear();
    }

    public List<SubstanceItemType> getRegisteredItemTypes()
    {
        return registeredItemTypes;
    }

    public SubstanceItemType getRegisteredItemTypeById(String id)
    {
        return registeredItemTypes.stream().filter(itemType -> itemType.getId().equals(id)).findFirst().orElse(null);
    }

    public SubstanceItemType getRegisteredItemTypeByItem(ItemStack itemStack)
    {
        return registeredItemTypes.stream().filter(itemType -> itemType.getMaterial() == itemStack.getType() && itemType.getCustomModelData() == itemStack.getItemMeta().getCustomModelData()).findAny().orElse(null);
    }

    public boolean isSubstanceItem(ItemStack itemStack)
    {
        return registeredItemTypes.stream().anyMatch(itemType -> itemType.getMaterial() == itemStack.getType() && itemType.getCustomModelData() == itemStack.getItemMeta().getCustomModelData());
    }

    public List<String> getRegisteredItemTypeIds()
    {
        SubstancePlugin.get().getLogger().severe("Called");
        List<String> itemIds = new ArrayList<>();
        registeredItemTypes.forEach(item -> itemIds.add(item.getId()));
        return itemIds;
    }
}
