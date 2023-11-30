package org.substancemc.item;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TranslatableComponent;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.substancemc.core.util.config.SubstanceSerializable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SubstanceItemType implements SubstanceSerializable {

    private Material material;
    private String name, translationKey;
    private String parent = "minecraft:item/generated";
    private List<String> textures = new ArrayList<>();

    private String id;
    private transient int customModelData = -1;
    private final transient Map<String, String> textureMap = new HashMap<>();

    public SubstanceItemType()
    {
    }

    public SubstanceItemType(Material material, String id, String name, String translationKey, List<String> textures, String parent)
    {
        this.material = material;
        this.name = name;
        this.translationKey = translationKey;
        this.textures = textures;
        this.parent = parent;
        this.id = id;
        postDeserialization();
    }

    public SubstanceItemType(Material material, int customModelData, String id, String name, String translationKey)
    {
        this.material = material;
        this.name = name;
        this.translationKey = translationKey;
        this.id = id;
        this.customModelData = customModelData;
        postDeserialization();
    }

    public SubstanceItemType(Material material, String id, String name, String translationKey, List<String> textures)
    {
        this.material = material;
        this.name = name;
        this.translationKey = translationKey;
        this.textures = textures;
        this.id = id;
        postDeserialization();
    }

    @Override
    public SubstanceSerializable deserialize(String path, Map<String, Object> values) throws IllegalAccessException {
        this.id = path.contains(".") ? path.split("\\.")[path.split("\\.").length - 1] : path;
        return SubstanceSerializable.super.deserialize(path, values);
    }

    @Override
    public SubstanceSerializable getInstance() {
        return this;
    }

    @Override
    public void postDeserialization() {
        textures.forEach(texture -> {
            String key = texture.split(":")[0];
            String value = texture.split(":")[1];
            textureMap.put(key, value);
        });
    }

    public Map<String, String> getTextureMap()
    {
        return textureMap;
    }

    public List<String> getTextures()
    {
        return textures;
    }

    public Material getMaterial()
    {
        return material;
    }

    public String getParent()
    {
        return parent;
    }

    public String getId()
    {
        return id;
    }

    public String getName()
    {
        return name;
    }

    public String getTranslationKey()
    {
        return translationKey;
    }

    public int getCustomModelData()
    {
        return customModelData;
    }

    public void setCustomModelData(int customModelData)
    {
        this.customModelData = customModelData;
    }

    public SubstanceItem createDefaultItem()
    {
        if(customModelData == -1) throw new RuntimeException("No custom model data was generated for this item");
        TranslatableComponent translatableComponent = Component.translatable().key(translationKey).fallback(name).decoration(TextDecoration.ITALIC, false).build();
        ItemStack itemStack = new ItemStack(material);
        ItemMeta meta = itemStack.getItemMeta();
        meta.setCustomModelData(customModelData);
        meta.displayName(translatableComponent);
        itemStack.setItemMeta(meta);
        return new SubstanceItem(this, itemStack);
    }

}
