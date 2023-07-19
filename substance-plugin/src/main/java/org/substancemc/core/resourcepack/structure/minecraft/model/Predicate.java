package org.substancemc.core.resourcepack.structure.minecraft.model;

public class Predicate {

    private int custom_model_data;

    public int getCustomModelData() {
        return custom_model_data;
    }

    public void setCustomModelData(int customModelData) {
        this.custom_model_data = customModelData;
    }

    public Predicate(int customModelData)
    {
        this.custom_model_data = customModelData;
    }

}
