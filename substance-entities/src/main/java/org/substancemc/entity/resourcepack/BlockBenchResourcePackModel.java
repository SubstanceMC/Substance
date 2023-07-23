package org.substancemc.entity.resourcepack;

import org.substancemc.core.resourcepack.structure.minecraft.model.ModelElementFacingInformation;
import org.substancemc.core.resourcepack.structure.substance.ResourcePackModel;
import org.substancemc.core.resourcepack.structure.substance.ResourcePackModelCubeDisplay;
import org.substancemc.entity.blockbench.structure.BlockBenchModel;
import org.substancemc.entity.blockbench.structure.element.BlockBenchModelCube;

import java.util.*;

public class BlockBenchResourcePackModel extends ResourcePackModel {
    public BlockBenchResourcePackModel(List<BlockBenchModelCube> cubes, String subParent, BlockBenchModel parent, boolean hurt)
    {
        setTextures(extractTextures(cubes, parent, hurt));
        setParent(subParent);
        ResourcePackModelCubeDisplay head = new ResourcePackModelCubeDisplay();
        getDisplay().put("head", head);
        head.setScale(3.7333333, 3.7333333, 3.7333333);
    }

    public BlockBenchResourcePackModel()
    {
        ResourcePackModelCubeDisplay head = new ResourcePackModelCubeDisplay();
        getDisplay().put("head", head);
        head.setScale(3.7333333, 3.7333333, 3.7333333);
    }

    private Map<String, String> extractTextures(List<BlockBenchModelCube> childGroup, BlockBenchModel parent, boolean hurt)
    {
        Map<String, String> textures = new HashMap<>();
        childGroup.forEach(child -> {
            for(ModelElementFacingInformation information : child.getFaces().getAllFacings())
            {
                if(!textures.containsKey(information.getTextureId()))
                {
                    String textureName = Objects.requireNonNull(Arrays.stream(parent.getTextures()).filter(texture -> texture.getId().equals(information.getTextureId())).findAny().orElse(null)).getName();
                    textureName = textureName.substring(0, textureName.length() - 4);
                    if(hurt) textureName += "_hurt";
                    textures.put(information.getTextureId(), String.format("substance:entity/%s", textureName));
                }
            }
        });
        return textures;
    }


}
