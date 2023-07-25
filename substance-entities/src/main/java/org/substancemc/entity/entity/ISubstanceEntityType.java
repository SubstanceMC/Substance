package org.substancemc.entity.entity;

import org.bukkit.inventory.ItemStack;
import org.substancemc.core.util.file.DataFolderFile;
import org.substancemc.entity.blockbench.structure.BlockBenchModel;

import java.awt.*;

public interface ISubstanceEntityType {
    String getId();
    String getName();
    double getMaxHealth();
    double[] getHitBox();
    BlockBenchModel getModel();
    String getModelId();
    Color getSpawnEggBase();
    Color getSpawnEggOverlay();
    ItemStack getSpawnEgg();
    DataFolderFile getSpawnEggBaseFile();
    DataFolderFile getSpawnEggOverlayFile();

}
