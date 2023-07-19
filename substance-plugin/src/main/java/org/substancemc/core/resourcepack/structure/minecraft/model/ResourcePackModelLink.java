package org.substancemc.core.resourcepack.structure.minecraft.model;

import org.bukkit.Material;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResourcePackModelLink {

    private String parent;
    private Map<String, String> textures;
    private Override[] overrides;

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public Map<String, String> getTextures() {
        return textures;
    }

    public void setTextures(Map<String, String> textures) {
        this.textures = textures;
    }

    public Override[] getOverrides() {
        return overrides;
    }

    public void setOverrides(Override[] overrides) {
        this.overrides = overrides;
    }

    public ResourcePackModelLink(Material material, List<String> totalModelList)
    {
        if(!material.isItem()) throw new RuntimeException("Material has to be an item material");
        this.parent = "minecraft:item/generated";
        Map<String, String> textureMap = new HashMap<>();
        textureMap.put("layer0", "minecraft:item/" + material.toString().toLowerCase());
        textures = textureMap;
        overrides = new Override[totalModelList.size()];
        for(int i = 0; i < totalModelList.size(); i++)
        {
            int currentModelData = i + 1;
            overrides[i] = new Override(totalModelList.get(i), currentModelData);
        }
    }

}
