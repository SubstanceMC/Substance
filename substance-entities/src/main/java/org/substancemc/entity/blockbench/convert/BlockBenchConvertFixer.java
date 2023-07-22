package org.substancemc.entity.blockbench.convert;

import org.jetbrains.annotations.ApiStatus;
import org.substancemc.core.SubstancePlugin;
import org.substancemc.core.resourcepack.structure.minecraft.model.ModelElementFacingInformation;
import org.substancemc.entity.blockbench.error.BlockBenchParseException;
import org.substancemc.entity.blockbench.structure.BlockBenchModel;
import org.substancemc.entity.blockbench.structure.element.BlockBenchModelBone;
import org.substancemc.entity.blockbench.structure.element.BlockBenchModelCube;
import org.substancemc.entity.blockbench.structure.texture.BlockBenchModelTexture;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class BlockBenchConvertFixer {

    private final List<String> textureNames = new ArrayList<>();
    private final List<String> boneNames = new ArrayList<>();
    public void fix(BlockBenchModel model) throws BlockBenchParseException
    {
        //Try to fix names
        if((model.getModelIdentifier() == null || model.getModelIdentifier().isEmpty()) && (model.getName() == null || model.getName().isEmpty())) throw new BlockBenchParseException("Your model has to have either name or id");
        if(model.getModelIdentifier() == null || model.getModelIdentifier().isEmpty()) model.setModelIdentifier(model.getName().toLowerCase().replace(" ", "_"));
        else if(model.getName() == null || model.getName().isEmpty()) model.setName(model.getModelIdentifier());


        //Try to fix duplicate textures
        for(BlockBenchModelTexture texture : model.getTextures())
        {
            if(textureNames.contains(texture.getName()))
            {
                if(textureNames.contains(model.getModelIdentifier())) throw new BlockBenchParseException("Your model textures were not uniquely named, and escaping the names was not possible");
                texture.setName(model.getModelIdentifier());
            }
            textureNames.add(texture.getName());
        }

        //Fix bones with empty names or duplicate
        for(BlockBenchModelBone bone : model.getBones())
        {
            if(boneNames.contains(bone.getName()))
            {
                bone.setName(bone.getUUID());
                return;
            }
            boneNames.add(bone.getName());
        }

        model.getStructure().keySet().forEach(bone -> {
            List<BlockBenchModelCube> children = model.getStructure().get(bone);
            if(children == null || children.isEmpty()) return;
            //Fix cube 3D definition, also make every cube accessible
            bone.setZoom(updatePosition(children));
            //Fix cube UV definition
            children.forEach(child -> {
                updateUV(model, child);
            });
        });
    }

    @ApiStatus.Experimental
    private double updatePosition(List<BlockBenchModelCube> childGroup)
    {
        portToOrigin(childGroup);
        return 1;
        //return findSize(childGroup, 1);
    }

    private Map.Entry<BlockBenchModelCube, BlockBenchVector> findOriginNearest(List<BlockBenchModelCube> childGroup)
    {
        BlockBenchVector origin = new BlockBenchVector();
        Map.Entry<BlockBenchModelCube, BlockBenchVector> nearest = null;
        for(BlockBenchModelCube child : childGroup)
        {
            BlockBenchVector childVector = new BlockBenchVector(child.getFrom());
            if(nearest == null || nearest.getValue().distance(origin) > childVector.distance(origin))
            {
                nearest = Collections.singletonMap(child, childVector).entrySet().iterator().next();
            }
        }
        return nearest;
    }

    private void portToOrigin(List<BlockBenchModelCube> childGroup)
    {
        Map.Entry<BlockBenchModelCube, BlockBenchVector> nearest = findOriginNearest(childGroup);
        assert nearest != null;
        BlockBenchVector difference = nearest.getValue().mul(-1);
        for(BlockBenchModelCube child : childGroup)
        {
                double[] from = child.getFrom();
                double[] to = child.getTo();
                from = new BlockBenchVector(from).add(difference).toArray();
                to = new BlockBenchVector(to).add(difference).toArray();
                child.setFrom(from);
                child.setTo(to);
        }
    }

    private void updateSize(List<BlockBenchModelCube> childGroup, double newSize)
    {
        BlockBenchModelCube originCube = findOriginNearest(childGroup).getKey();
        originCube.setTo(new BlockBenchVector(originCube.getTo()).mul(newSize).toArray());
        for(BlockBenchModelCube child : childGroup)
        {
            if(child != originCube)
            {
                BlockBenchVector childToOrigin = new BlockBenchVector(originCube.getTo()).sub(new BlockBenchVector(child.getFrom()));
                childToOrigin.mul(newSize);
                BlockBenchVector childToOriginAbs = new BlockBenchVector(originCube.getFrom()).add(childToOrigin);
                BlockBenchVector fromTo = new BlockBenchVector(originCube.getTo()).sub(new BlockBenchVector(child.getFrom()));
                child.setFrom(childToOriginAbs.toArray());
                fromTo.mul(newSize);
                BlockBenchVector childFromToAbs = new BlockBenchVector(child.getFrom()).add(fromTo);
                child.setTo(childFromToAbs.toArray());
            }
        }

    }
    private double findSize(List<BlockBenchModelCube> childGroup, double currentSize)
    {
        updateSize(childGroup, currentSize);
        boolean hasIllegalElement = false;
        for(BlockBenchModelCube cube : childGroup)
        {
            if(hasIllegalElement) continue;
            if(cube.isMinecraftIllegal())
            {
                hasIllegalElement = true;
            }
        }
        if(hasIllegalElement)
        {
            double newSize = currentSize * 0.95;
            return findSize(childGroup, newSize);
        }
        return currentSize;
    }

    private void updateUV(BlockBenchModel parent, BlockBenchModelCube cube)
    {
        ModelElementFacingInformation[] facing = cube.getFaces().getAllFacings();
        for(ModelElementFacingInformation information : facing)
        {
            double[] uv = information.getUV();
            for(int uvIndex = 0; uvIndex < uv.length; uvIndex++)
            {
                uv[uvIndex] = Math.round(((uv[uvIndex] / parent.getResolution().getWidth()) * 16.0) * 100.0) / 100.0;
            }
            information.setUV(uv);
        }
    }


}
