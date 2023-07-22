package org.substancemc.core.resourcepack.generator.model;

import org.substancemc.core.SubstancePlugin;
import org.substancemc.core.util.gson.GsonFileWriter;
import org.substancemc.core.resourcepack.ResourcePackFile;
import org.substancemc.core.resourcepack.generator.GeneratorPreProcessor;
import org.substancemc.core.resourcepack.generator.ResourcePackGenerator;
import org.substancemc.core.resourcepack.structure.minecraft.model.ResourcePackModelLink;

import java.util.List;

public class ResourcePackModelLinkGenerator implements ResourcePackGenerator<List<String>, List<String>>, GeneratorPreProcessor<List<String>, List<String>> {
    @Override
    public void generate(List<String> context) {
        ResourcePackModelLink link = new ResourcePackModelLink(SubstancePlugin.get().getResourcePackManager().getModelMaterial(), getProcessor().process(context));
        ResourcePackFile file = new ResourcePackFile("minecraft/models/item/" + SubstancePlugin.get().getResourcePackManager().getModelMaterial().toString().toLowerCase() + ".json");
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
