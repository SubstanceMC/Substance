package org.substancemc.entity.resourcepack;

import org.substancemc.entity.blockbench.structure.BlockBenchModel;
import org.substancemc.entity.blockbench.structure.element.BlockBenchModelCube;
import org.substancemc.entity.blockbench.structure.texture.BlockBenchModelTexture;
import org.substancemc.core.resourcepack.structure.substance.ResourcePackModel;
import org.substancemc.core.resourcepack.structure.substance.ResourcePackModelCube;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BlockBenchResourcePackModel extends ResourcePackModel {
    public BlockBenchResourcePackModel(BlockBenchModel parentModel, List<BlockBenchModelCube> cubes)
    {
        create(parentModel, cubes, false);
    }

    public BlockBenchResourcePackModel(BlockBenchModel parentModel, List<BlockBenchModelCube> cubes, boolean hurt)
    {
        create(parentModel, cubes, hurt);
    }

    private void create(BlockBenchModel parentModel, List<BlockBenchModelCube> cubes, boolean hurt)
    {
        Map<String, String> textures = new HashMap<>();
        for(BlockBenchModelTexture texture : parentModel.getTextures())
        {
            textures.put(texture.getId(),"substance:entity/" + texture.getName().substring(0, texture.getName().length() - 4) + (hurt ? "_hurt" : ""));
        }
        setTextures(textures);
        ResourcePackModelCube[] elements = new ResourcePackModelCube[cubes.size()];
        for(int i = 0; i < elements.length; i++)
        {
            elements[i] = new ResourcePackModelCube(cubes.get(i));
        }
        setElements(elements);
        setTextureSize(new int[] {parentModel.getResolution().getWidth(), parentModel.getResolution().getHeight()});
    }
}
