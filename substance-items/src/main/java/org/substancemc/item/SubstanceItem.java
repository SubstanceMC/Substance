package org.substancemc.item;

import org.bukkit.inventory.ItemStack;

public class SubstanceItem {

    private final ItemStack object;
    private final SubstanceItemType type;
    public SubstanceItem(SubstanceItemType type, ItemStack object)
    {
        this.type = type;
        this.object = object;
    }
    public ItemStack getPhysical()
    {
        return object;
    }

    public SubstanceItemType getType()
    {
        return type;
    }

}
