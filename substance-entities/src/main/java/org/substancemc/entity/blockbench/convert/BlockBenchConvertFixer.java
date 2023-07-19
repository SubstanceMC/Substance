package org.substancemc.entity.blockbench.convert;

import org.substancemc.entity.blockbench.structure.BlockBenchModel;
import org.substancemc.entity.blockbench.error.BlockBenchParseException;
import org.substancemc.entity.blockbench.structure.texture.BlockBenchModelTexture;

import java.util.ArrayList;
import java.util.List;

public class BlockBenchConvertFixer {

    private final List<String> textureNames = new ArrayList<>();

    public void fix(BlockBenchModel model) throws BlockBenchParseException
    {
        //Try to fix names
        if(model.getModelIdentifier().isEmpty() && model.getName().isEmpty()) throw new BlockBenchParseException("Your model has to have either name or id");
        if(model.getModelIdentifier().isEmpty()) model.setModelIdentifier(model.getName().toLowerCase().replace(" ", "_"));
        else if(model.getName().isEmpty()) model.setName(model.getModelIdentifier());


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
    }

}
