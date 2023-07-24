package org.substancemc.core.resourcepack.generator.model;

import org.bukkit.Material;
import org.substancemc.core.SubstancePlugin;
import org.substancemc.core.resourcepack.ResourcePackFile;
import org.substancemc.core.resourcepack.generator.GeneratorPreProcessor;
import org.substancemc.core.resourcepack.generator.ResourcePackGenerator;
import org.substancemc.core.resourcepack.structure.minecraft.model.ResourcePackModelLink;
import org.substancemc.core.util.gson.GsonFileParser;
import org.substancemc.core.util.gson.GsonFileWriter;

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
        ResourcePackFile file = new ResourcePackFile("minecraft/models/item/" + mat.toString().toLowerCase() + ".json");
        ResourcePackModelLink link;
        if(!file.getFile().exists()) link = new ResourcePackModelLink(mat, getProcessor().process(context));
        else {
            link = new GsonFileParser<ResourcePackModelLink>().parse(file, ResourcePackModelLink.class);
            link = new ResourcePackModelLink(mat, link.getOverrides(), process(context));
        }
        new GsonFileWriter<ResourcePackModelLink>().write(file.getFile(), link);
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
