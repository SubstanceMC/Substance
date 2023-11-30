package org.substancemc.core.resourcepack.generator.model;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.substancemc.core.SubstancePlugin;
import org.substancemc.core.resourcepack.ResourcePackFile;
import org.substancemc.core.resourcepack.generator.GeneratorPreProcessor;
import org.substancemc.core.resourcepack.generator.ResourcePackGenerator;
import org.substancemc.core.resourcepack.structure.minecraft.model.ResourcePackModelLink;
import org.substancemc.core.util.gson.GsonFileParser;
import org.substancemc.core.util.gson.GsonFileWriter;

import java.util.HashMap;
import java.util.List;

public class ResourcePackModelLinkGenerator implements ResourcePackGenerator<List<String>, List<String>>, GeneratorPreProcessor<List<String>, List<String>> {

    private final Material mat;

    public ResourcePackModelLinkGenerator(Material mat)
    {
        this.mat = mat;
    }

    public Material getMaterial()
    {
        return mat;
    }



    @Override
    public void generate(List<String> context) {
        generateWithReturn(context);
    }

    public ResourcePackModelLink generateWithReturn(List<String> context)
    {
        ResourcePackFile file = new ResourcePackFile("minecraft/models/item/" + mat.toString().toLowerCase() + ".json");
        ResourcePackModelLink link;
        if(!file.getFile().exists()) link = new ResourcePackModelLink(mat, getProcessor().process(context));
        else {
            link = new GsonFileParser<ResourcePackModelLink>().parse(file, ResourcePackModelLink.class);
            link = new ResourcePackModelLink(mat, link.getOverrides(), process(context));
        }
        new GsonFileWriter<ResourcePackModelLink>().write(file.getFile(), link);
        return link;
    }

    public HashMap<String, ItemStack> generateWithContextReturn(List<String> context)
    {
        HashMap<String, ItemStack> toReturn = new HashMap<>();
        ResourcePackModelLink link = generateWithReturn(context);
        for(int i = 0; i < context.size(); i++)
        {
            int modelData = link.getOverrides()[i].getPredicate().getCustomModelData();
            ItemStack returned = new ItemStack(link.getMaterial());
            ItemMeta meta = returned.getItemMeta();
            meta.setCustomModelData(modelData);
            returned.setItemMeta(meta);

            toReturn.put(context.get(i), returned);
        }
        return toReturn;
    }

    @Override
    public GeneratorPreProcessor<List<String>, List<String>> getProcessor() {
        return this;
    }

    @Override
    public List<String> process(List<String> original) {
        return original;
    }
}
