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
                continue;
            }
            boneNames.add(bone.getName());
        }
    }



}
