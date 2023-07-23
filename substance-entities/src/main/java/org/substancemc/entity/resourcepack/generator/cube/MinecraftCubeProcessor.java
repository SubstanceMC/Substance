package org.substancemc.entity.resourcepack.generator.cube;

import org.jetbrains.annotations.ApiStatus;
import org.substancemc.core.resourcepack.generator.GeneratorPreProcessor;
import org.substancemc.core.resourcepack.structure.minecraft.model.ModelElementFacingInformation;
import org.substancemc.core.resourcepack.structure.substance.ResourcePackModel;
import org.substancemc.core.resourcepack.structure.substance.ResourcePackModelCube;
import org.substancemc.entity.blockbench.convert.BlockBenchVector;
import org.substancemc.entity.blockbench.structure.BlockBenchModel;
import org.substancemc.entity.blockbench.structure.element.BlockBenchModelBone;
import org.substancemc.entity.blockbench.structure.element.BlockBenchModelCube;
import org.substancemc.entity.resourcepack.BlockBenchResourcePackModel;

import java.util.*;

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



    @ApiStatus.Experimental
    private boolean normalize(ResourcePackModelCube[] cubes, ResourcePackModel model)
    {
        double[] offset = new double[]{0, 0, 0};
        for(ResourcePackModelCube normalized : cubes) {
            normalized.shrinkCube(0.6);
            for (int i = 0; i < 3; i++) {
                if (normalized.getFrom().get()[i] + offset[i] > 32.0) {
                    offset[i] -= normalized.getFrom().get()[i] + offset[i] - 32.0;
                }

                if (normalized.getFrom().get()[i] + offset[i] < -16.0) {
                    offset[i] -= normalized.getFrom().get()[i] + offset[i] + 16;
                }

                if (normalized.getTo().get()[i] + offset[i] > 32.0) {
                    offset[i] -= normalized.getTo().get()[i] + offset[i] - 32.0;
                }

                if (normalized.getTo().get()[i] + offset[i] < -16.0) {
                    offset[i] -= normalized.getTo().get()[i] + offset[i] + 16;
                }
            }
        }

        boolean from, to;

        Iterator<ResourcePackModelCube> normalizeIterator = Arrays.stream(cubes).iterator();

        do {
            if(!normalizeIterator.hasNext()) {
                normalizeIterator = Arrays.stream(cubes).iterator();
                while (normalizeIterator.hasNext()) {
                    ResourcePackModelCube normalized = normalizeIterator.next();
                    if(normalized.getRotation() == null || normalized.getRotation().getOrigin() == null) continue;
                    normalized.getRotation().getOrigin().shrink(0.6);
                    normalized.getRotation().getOrigin().addOffset(offset);
                }
                if (offset[0] != 0 || offset[1] != 0 || offset[2] != 0) {
                    model.getDisplay().get("head").moveTranslation(offset);
                }
                model.getDisplay().get("head").setScale(3.8095, 3.8095, 3.8095);
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
        for(ResourcePackModelCube normalized : cubes) {
            normalized.shrinkCube(0.6);
            for (int i = 0; i < 3; i++) {
                if (normalized.getFrom().get()[i] + offset[i] > 32.0) {
                    offset[i] -= normalized.getFrom().get()[i] + offset[i] - 32.0;
                }

                if (normalized.getFrom().get()[i] + offset[i] < -16.0) {
                    offset[i] -= normalized.getFrom().get()[i] + offset[i] + 16;
                }

                if (normalized.getTo().get()[i] + offset[i] > 32.0) {
                    offset[i] -= normalized.getTo().get()[i] + offset[i] - 32.0;
                }

                if (normalized.getTo().get()[i] + offset[i] < -16.0) {
                    offset[i] -= normalized.getTo().get()[i] + offset[i] + 16;
                }
            }
        }


        Iterator<ResourcePackModelCube> normalizeIterator = Arrays.stream(cubes).iterator();
        while (normalizeIterator.hasNext()) {
            ResourcePackModelCube normalized = normalizeIterator.next();
            normalized.getRotation().getOrigin().shrink(0.42857143);
            normalized.getRotation().getOrigin().addOffset(offset);
        }
        if (offset[0] != 0 || offset[1] != 0 || offset[2] != 0) {
            model.getDisplay().get("head").moveTranslation(offset);
        }
        model.getDisplay().get("head").setScale(3.7333333, 3.7333333, 3.7333333);
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
