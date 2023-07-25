package org.substancemc.entity.resourcepack.generator.spawnegg.texture;

import org.checkerframework.checker.units.qual.C;
import org.substancemc.core.resourcepack.generator.GeneratorPreProcessor;
import org.substancemc.core.resourcepack.generator.ResourcePackGenerator;
import org.substancemc.core.resourcepack.generator.texture.ResourcePackTextureFile;
import org.substancemc.core.util.file.DataFolderFile;
import org.substancemc.core.util.file.ImageTinter;
import org.substancemc.entity.SubstanceEntityAddon;
import org.substancemc.entity.entity.SubstanceEntityType;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;

public class SpawnEggTextureGenerator implements GeneratorPreProcessor<SubstanceEntityType, HashMap<DataFolderFile, Color>>, ResourcePackGenerator<SubstanceEntityType, HashMap<DataFolderFile, Color>> {
    @Override
    public HashMap<DataFolderFile, Color> process(SubstanceEntityType original) {
        return SubstanceEntityAddon.get().getEntityManager().getSpawnEggManager().getSpawnEggEntityMap().getOrDefault(original, new HashMap<>());
    }

    @Override
    public void generate(SubstanceEntityType entityType) {
        HashMap<DataFolderFile, Color> processed = process(entityType);
        BufferedImage image = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics = image.createGraphics();
        processed.keySet().forEach(key -> {
            Color withAlpha = new Color(processed.get(key).getRed(), processed.get(key).getGreen(), processed.get(key).getBlue(), 180);
            BufferedImage imageByKey = new ImageTinter(null, withAlpha).tint(key);
            graphics.drawImage(imageByKey, 0, 0, null);
        });
        graphics.dispose();
        ResourcePackTextureFile textureFile = new ResourcePackTextureFile("item/spawn_egg/" + entityType.getId() + ".png");
        if(!textureFile.getFile().exists()) {
            try {
                if(!textureFile.getFile().getParentFile().exists()) textureFile.getFile().getParentFile().mkdirs();
                textureFile.getFile().createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        try {
            ImageIO.write(image, "png", textureFile.getFile());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public GeneratorPreProcessor<SubstanceEntityType, HashMap<DataFolderFile, Color>> getProcessor() {
        return this;
    }
}
