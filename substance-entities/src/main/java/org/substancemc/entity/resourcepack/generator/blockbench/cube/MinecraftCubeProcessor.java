package org.substancemc.entity.resourcepack.generator.blockbench.cube;

import org.jetbrains.annotations.ApiStatus;
import org.substancemc.core.SubstancePlugin;
import org.substancemc.core.resourcepack.generator.GeneratorPreProcessor;
import org.substancemc.core.resourcepack.structure.minecraft.model.ModelElementFacingInformation;
import org.substancemc.core.resourcepack.structure.substance.ResourcePackModel;
import org.substancemc.core.resourcepack.structure.substance.ResourcePackModelCube;
import org.substancemc.entity.blockbench.convert.BlockBenchVector;
import org.substancemc.entity.blockbench.structure.BlockBenchModel;
import org.substancemc.entity.blockbench.structure.element.BlockBenchModelBone;
import org.substancemc.entity.blockbench.structure.element.BlockBenchModelCube;
import org.substancemc.entity.resourcepack.generator.blockbench.model.BlockBenchResourcePackModel;

import java.util.*;
import java.util.stream.Collectors;

public class MinecraftCubeProcessor implements GeneratorPreProcessor<Map.Entry<BlockBenchModelBone, List<BlockBenchModelCube>>, ResourcePackModel> {

    private final BlockBenchModel blockBenchModel;

    public MinecraftCubeProcessor(BlockBenchModel model)
    {
        this.blockBenchModel = model;
    }
    @ApiStatus.Experimental
    @Override
    public ResourcePackModel process(Map.Entry<BlockBenchModelBone, List<BlockBenchModelCube>> structureElement) {
        ResourcePackModel model = new BlockBenchResourcePackModel();
        generateResourcePackElements(structureElement.getValue(), model);
        portToOrigin(structureElement.getValue(), structureElement.getKey(), model);
        structureElement.getKey().setSmall(normalize(model.getElements(), model));
        model.setTextures(extractTextures(structureElement.getValue()));
        for(ResourcePackModelCube cube : model.getElements()) updateUV(this.blockBenchModel, cube);
        return model;
    }
    private Map<String, String> extractTextures(List<BlockBenchModelCube> childGroup)
    {
        Map<String, String> textures = new HashMap<>();
        childGroup.forEach(child -> {
            for(ModelElementFacingInformation information : child.getFaces().getAllFacings())
            {
                if(!textures.containsKey(information.getTextureId()))
                {
                    textures.put(information.getTextureId(), "#" + information.getTextureId());
                }
            }
        });
        return textures;
    }
    private void generateResourcePackElements(List<BlockBenchModelCube> childGroup, ResourcePackModel model)
    {
        ResourcePackModelCube[] cubes = new ResourcePackModelCube[childGroup.size()];
        for(int i = 0; i < cubes.length; i++) cubes[i] = new ResourcePackModelCube(childGroup.get(i));
        model.setElements(cubes);
    }
    @ApiStatus.Experimental
    private void portToOrigin(List<BlockBenchModelCube> childGroup, BlockBenchModelBone parent, ResourcePackModel model)
    {
        for(int i = 0; i < model.getElements().length; i++)
        {
            BlockBenchModelCube child = childGroup.get(i);
            ResourcePackModelCube resourcePackCube = model.getElement(i);
            BlockBenchVector from = new BlockBenchVector(child.getFrom()).sub(new BlockBenchVector(parent.getOrigin())).sub(child.getInflate()).add(8);
            resourcePackCube.getFrom().set(from.toArray());
            BlockBenchVector to = new BlockBenchVector(child.getTo()).sub(new BlockBenchVector(parent.getOrigin())).add(child.getInflate()).add(8);
            resourcePackCube.getTo().set(to.toArray());
        }
    }


    private void fixOffset(double[] offset, double[] from, double[] to)
    {
        for (int i = 0; i < 3; i++) {
            if (from[i] + offset[i] > 32.0) {
                offset[i] -= from[i] + offset[i] - 32.0;
            }

            if (from[i] + offset[i] < -16.0) {
                offset[i] -= from[i] + offset[i] + 16;
            }

            if (to[i] + offset[i] > 32.0) {
                offset[i] -= to[i] + offset[i] - 32.0;
            }

            if (to[i] + offset[i] < -16.0) {
                offset[i] -= to[i] + offset[i] + 16;
            }
        }
    }

    private void preFixCubes(ResourcePackModelCube[] cubes, double[] offset)
    {
        for(ResourcePackModelCube normalized : cubes) {
            normalized.shrinkCube(0.6);
            fixOffset(offset, normalized.getFrom().get(), normalized.getTo().get());
        }
    }

    private void shrinkOrigin(ResourcePackModelCube[] cubes, double[] offset)
    {
        Arrays.stream(cubes).toList().forEach(normalized -> {
            normalized.getRotation().getOrigin().shrink(0.42857143);
            normalized.getRotation().getOrigin().addOffset(offset);
        });
    }

    private void shrinkRotation(ResourcePackModelCube[] cubes, double[] offset)
    {
        Arrays.stream(cubes).toList().forEach(normalized -> {
            if(normalized.getRotation() == null || normalized.getRotation().getOrigin() == null) return;
            normalized.getRotation().getOrigin().shrink(0.6);
            normalized.getRotation().getOrigin().addOffset(offset);
        });
    }

    private void resetScaleForDisplay(ResourcePackModel model, double[] offset, double scale)
    {
        if (offset[0] != 0 || offset[1] != 0 || offset[2] != 0) {
            model.getDisplay().get("head").moveTranslation(offset);
        }
        model.getDisplay().get("head").setScale(scale, scale, scale);
    }

    @ApiStatus.Experimental
    private boolean normalize(ResourcePackModelCube[] cubes, ResourcePackModel model)
    {
        double[] offset = new double[]{0, 0, 0};
        preFixCubes(cubes, offset);
        boolean from, to;

        Iterator<ResourcePackModelCube> normalizeIterator = Arrays.stream(cubes).iterator();
        do {
            if(!normalizeIterator.hasNext()) {
                shrinkRotation(cubes, offset);
                resetScaleForDisplay(model, offset, 3.8095);
                return true;
            }
            ResourcePackModelCube normalized = normalizeIterator.next();
            normalized.getFrom().addOffset(offset);
            normalized.getTo().addOffset(offset);
            from = normalized.checkFrom();
            to = normalized.checkTo();
        } while(from && to);
        return shrinkLarge(cubes, model);
    }


    private boolean shrinkLarge(ResourcePackModelCube[] cubes, ResourcePackModel model)
    {
        double[] offset = new double[]{0, 0, 0};
        preFixCubes(cubes, offset);
        model.setOffset(offset);
        shrinkOrigin(cubes, offset);
        resetScaleForDisplay(model, offset, 3.7333333);
        return false;
    }





    private void updateUV(BlockBenchModel parent, ResourcePackModelCube cube)
    {
        float widthRatio = 16.0f / parent.getResolution().getWidth();
        float heightRatio = 16.0f / parent.getResolution().getHeight();
        ModelElementFacingInformation[] facing = cube.getFaces().getAllFacings();
        for(ModelElementFacingInformation information : facing)
        {
            double[] uv = information.getUV();
            uv[0] = uv[0] * widthRatio;
            uv[1] = uv[1] * heightRatio;
            uv[2] = uv[2] * widthRatio;
            uv[3] = uv[3] * heightRatio;
            information.setUV(uv);
        }
    }
}
