package org.substancemc.entity.resourcepack.generator.model;

import com.google.gson.JsonPrimitive;
import org.substancemc.entity.SubstanceEntityAddon;
import org.substancemc.entity.blockbench.error.BlockBenchParseException;
import org.substancemc.entity.blockbench.structure.BlockBenchModel;
import org.substancemc.entity.blockbench.structure.element.BlockBenchModelCube;
import org.substancemc.entity.resourcepack.BlockBenchResourcePackModel;
import org.substancemc.core.SubstancePlugin;
import org.substancemc.entity.blockbench.structure.element.BlockBenchModelBone;
import org.substancemc.core.resourcepack.generator.GeneratorPreProcessor;
import org.substancemc.core.resourcepack.generator.model.ResourcePackModelFile;
import org.substancemc.core.resourcepack.structure.substance.ResourcePackModel;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EntityModelGeneratorPreProcessor implements GeneratorPreProcessor<BlockBenchModel, Map<ResourcePackModelFile, ResourcePackModel>> {
    @Override
    public Map<ResourcePackModelFile, ResourcePackModel> process(BlockBenchModel model) {
        Map<ResourcePackModelFile, ResourcePackModel> processed = new HashMap<>();
        try {
            for(BlockBenchModelBone bone : model.getBones())
            {
                if(bone.getCubeChildren().isEmpty()) continue;
                List<BlockBenchModelCube> cubesForEachModel = Arrays.stream(model.getCubes()).filter(cube -> bone.getCubeChildren().contains(new JsonPrimitive(cube.getUUID()))).toList();
                ResourcePackModel resourcePackModel = new BlockBenchResourcePackModel(model, cubesForEachModel);
                ResourcePackModelFile standard = new ResourcePackModelFile("entity/" + model.getModelIdentifier() + "/" + bone.getName().toLowerCase().replace(" ", "_").replace("-", "_") + ".json");
                processed.put(standard, resourcePackModel);
                if(SubstancePlugin.get().getConfig().getBoolean("misc.hurtColor"))
                {
                    ResourcePackModel hurtModel = new BlockBenchResourcePackModel(model, cubesForEachModel, true);
                    ResourcePackModelFile hurt = new ResourcePackModelFile( standard.getTopLevelRelativePath().substring(0, standard.getTopLevelRelativePath().length() - 5) + "_hurt.json");
                    processed.put(hurt, hurtModel);
                }
                SubstancePlugin.get().getLogger().warning("Generated sub-model of model " + model.getName()  + " with id " + model.getModelIdentifier() + " for bone " + bone.getName() + " with " + resourcePackModel.getElements().length + " cubes");

            }
        } catch (BlockBenchParseException e) {
            throw new RuntimeException(e);
        }
        processed.keySet().forEach(modelFile -> {
            SubstanceEntityAddon.get().getBlockBenchManager().addModelLocator(modelFile.getResourceLocator());
        });
        return processed;
    }
}
