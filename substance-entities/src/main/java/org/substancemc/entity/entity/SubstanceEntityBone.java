package org.substancemc.entity.entity;

import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Transformation;

import java.util.List;

public interface SubstanceEntityBone {
    ItemStack getVisuals();
    Transformation getTransformation();
    void setTransformation(Transformation transformation);
    void addTransformation(Transformation transformation);
    String getID();
    List<String> getChildren();
}
