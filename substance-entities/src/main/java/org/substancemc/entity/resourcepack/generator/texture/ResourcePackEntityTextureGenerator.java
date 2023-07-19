package org.substancemc.entity.resourcepack.generator.texture;

import org.substancemc.entity.blockbench.structure.BlockBenchModel;
import org.substancemc.entity.blockbench.structure.texture.BlockBenchModelTexture;
import org.substancemc.core.SubstancePlugin;
import org.substancemc.core.resourcepack.generator.GeneratorPreProcessor;
import org.substancemc.core.resourcepack.generator.ResourcePackGenerator;
import org.substancemc.core.resourcepack.generator.texture.ResourcePackTextureFile;
import org.substancemc.core.util.file.ImageConverter;
import org.substancemc.core.util.file.ImageTinter;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

public class ResourcePackEntityTextureGenerator implements GeneratorPreProcessor<BlockBenchModel, Map<String, String>>, ResourcePackGenerator<BlockBenchModel, Map<String, String>> {


    @Override
    public GeneratorPreProcessor<BlockBenchModel, Map<String, String>> getProcessor() {
        return this;
    }

    @Override
    public Map<String, String> process(BlockBenchModel model) {
        Map<String, String> processed = new HashMap<>();
        for(BlockBenchModelTexture texture : model.getTextures())
        {
            processed.put(texture.getSource(), texture.getName());
        }
        return processed;
    }

    @Override
    public void generate(BlockBenchModel model) {
        boolean withHurtColor = SubstancePlugin.get().getConfig().getBoolean("misc.hurtColor");
        Map<String, String> processed = getProcessor().process(model);
        processed.keySet().forEach(texture -> {
            String name = processed.get(texture);
            BufferedImage modelTexture = new ImageConverter(texture).convert(new ResourcePackTextureFile("entity/" + name));
            if(withHurtColor)
            {
                String hurtName = name.split(".png")[0] + "_hurt.png";
                ResourcePackTextureFile hurtTextureFile = new ResourcePackTextureFile("entity/" + hurtName);
                new ImageTinter(hurtTextureFile, new Color(255, 0, 0, 90)).tint(modelTexture);
            }
            SubstancePlugin.get().getLogger().warning("Generated texture " + name + " for model " + model.getName() + " with id " + model.getModelIdentifier());

        });
    }

}
