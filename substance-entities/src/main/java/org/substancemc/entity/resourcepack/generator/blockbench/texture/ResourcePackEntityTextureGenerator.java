package org.substancemc.entity.resourcepack.generator.blockbench.texture;

import org.checkerframework.checker.units.qual.A;
import org.substancemc.entity.SubstanceEntityAddon;
import org.substancemc.entity.blockbench.structure.BlockBenchModel;
import org.substancemc.entity.blockbench.structure.texture.BlockBenchModelTexture;
import org.substancemc.core.SubstancePlugin;
import org.substancemc.core.resourcepack.generator.GeneratorPreProcessor;
import org.substancemc.core.resourcepack.generator.ResourcePackGenerator;
import org.substancemc.core.resourcepack.generator.texture.ResourcePackTextureFile;
import org.substancemc.core.util.file.ImageConverter;
import org.substancemc.core.util.file.ImageTinter;

import java.awt.*;
import java.util.*;
import java.awt.image.BufferedImage;
import java.util.List;

public class ResourcePackEntityTextureGenerator implements GeneratorPreProcessor<BlockBenchModel, List<BlockBenchModelTexture>>, ResourcePackGenerator<BlockBenchModel, List<BlockBenchModelTexture>> {


    @Override
    public GeneratorPreProcessor<BlockBenchModel, List<BlockBenchModelTexture>> getProcessor() {
        return this;
    }

    @Override
    public List<BlockBenchModelTexture> process(BlockBenchModel model) {
        return new ArrayList<>(Arrays.asList(model.getTextures()));
    }

    @Override
    public void generate(BlockBenchModel model) {
        boolean withHurtColor = SubstanceEntityAddon.get().isHurtColorEnabled();
        List<BlockBenchModelTexture> processed = getProcessor().process(model);
        processed.forEach(texture -> {
            String name = texture.getName();
            BufferedImage modelTexture = new ImageConverter(texture.getSource()).convert(new ResourcePackTextureFile("entity/" + name));
            texture.setResolution(modelTexture.getWidth());
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
