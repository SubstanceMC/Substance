package org.substancemc.entity.entity;

import org.bukkit.inventory.ItemStack;
import org.substancemc.core.util.config.SubstanceSerializable;
import org.substancemc.core.util.file.DataFolderFile;
import org.substancemc.entity.SubstanceEntityAddon;
import org.substancemc.entity.blockbench.structure.BlockBenchModel;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class SubstanceEntityType implements ISubstanceEntityType, SubstanceSerializable {

    private transient BlockBenchModel model;
    private String modelId;

    private transient ItemStack spawnEgg;
    private transient Color spawnEggBase, spawnEggOverlay;
    private transient DataFolderFile spawnEggBaseFile, spawnEggOverlayFile;
    private List<Integer> spawnEggBaseColor, spawnEggOverlayColor = new ArrayList<>();
    private String spawnEggBaseTexture, spawnEggOverlayTexture;
    private double maxHealth;
    private transient double[] hitBox = new double[2];

    private List<Double> hitBoxSize = new ArrayList<>();
    private String name, id;

    @Override
    public void postDeserialization() {
        this.model = SubstanceEntityAddon.get().getBlockBenchManager().getModel(modelId);
        if(spawnEggBaseColor != null)
        {
            spawnEggBase = new Color(spawnEggBaseColor.get(0), spawnEggBaseColor.get(1), spawnEggBaseColor.get(2));
        }
        if(spawnEggOverlayColor != null)
        {
            spawnEggOverlay = new Color(spawnEggOverlayColor.get(0), spawnEggOverlayColor.get(1), spawnEggOverlayColor.get(2));
        }
        if(hitBoxSize != null)
        {
            hitBox[0] = hitBoxSize.get(0);
            hitBox[1] = hitBoxSize.get(1);
        }
        if(spawnEggBaseTexture != null) this.spawnEggBaseFile = new DataFolderFile(spawnEggBaseTexture);
        if(spawnEggOverlayTexture != null) this.spawnEggOverlayFile = new DataFolderFile(spawnEggOverlayTexture);

    }

    public Color getSpawnEggBase()
    {
        return spawnEggBase;
    }

    public Color getSpawnEggOverlay()
    {
        return spawnEggOverlay;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public void setModelId(String modelId)
    {
        this.modelId = modelId;
    }

    public void setMaxHealth(double maxHealth)
    {
        this.maxHealth = maxHealth;
    }

    public void setHitBox(double[] hitBox)
    {
        this.hitBox = hitBox;
    }

    @Override
    public double getMaxHealth() {
        return maxHealth;
    }

    @Override
    public double[] getHitBox() {
        return hitBox;
    }

    @Override
    public BlockBenchModel getModel() {
        return model;
    }

    @Override
    public String getModelId() {
        return modelId;
    }


    @Override
    public ItemStack getSpawnEgg() {
        return spawnEgg != null ? spawnEgg.clone() : null;
    }

    @Override
    public DataFolderFile getSpawnEggBaseFile() {
        return spawnEggBaseFile;
    }

    @Override
    public DataFolderFile getSpawnEggOverlayFile() {
        return spawnEggOverlayFile;
    }

    public void setSpawnEgg(ItemStack spawnEgg)
    {
        this.spawnEgg = spawnEgg;
    }


    @Override
    public SubstanceSerializable getInstance() {
        return this;
    }
}
