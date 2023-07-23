package org.substancemc.entity.resourcepack.generator.model;

import org.substancemc.core.SubstancePlugin;
import org.substancemc.core.resourcepack.generator.GeneratorPreProcessor;
import org.substancemc.core.resourcepack.generator.model.ResourcePackModelFile;
import org.substancemc.core.resourcepack.structure.substance.ResourcePackModel;
import org.substancemc.entity.SubstanceEntityAddon;
import org.substancemc.entity.blockbench.structure.BlockBenchModel;
import org.substancemc.entity.resourcepack.BlockBenchResourcePackModel;
import org.substancemc.entity.resourcepack.generator.cube.MinecraftCubeProcessor;

import java.util.HashMap;
import java.util.Map;

public class EntityModelGeneratorPreProcessor implements GeneratorPreProcessor<BlockBenchModel, Map<ResourcePackModelFile, ResourcePackModel>> {
    @Override
    public Map<ResourcePackModelFile, ResourcePackModel> process(BlockBenchModel model) {
        Map<ResourcePackModelFile, ResourcePackModel> processed = new HashMap<>();
        MinecraftCubeProcessor minecraftCubeProcessor = new MinecraftCubeProcessor(model);
        model.getStructure().entrySet().forEach(entry -> {
            if(entry.getValue() == null || entry.getValue().isEmpty()) return;
            ResourcePackModel parentAbstractModel = minecraftCubeProcessor.process(entry);
            ResourcePackModelFile abstractModelFile = new ResourcePackModelFile(String.format("entity/%s/abstract/%s.json", model.getModelIdentifier(), entry.getKey().getName().toLowerCase().replace(" ", "_").replace("-", "_")));
            String abstractModelResourceLocator = abstractModelFile.getResourceLocator();
            processed.put(abstractModelFile, parentAbstractModel);
            ResourcePackModel defaultModel = new BlockBenchResourcePackModel(entry.getValue(), abstractModelResourceLocator, model, false);
            ResourcePackModelFile defaultModelFile = new ResourcePackModelFile(String.format("entity/%s/default/%s.json", model.getModelIdentifier(), entry.getKey().getName().toLowerCase().replace(" ", "_").replace("-", "_")));
            processed.put(defaultModelFile, defaultModel);
            SubstanceEntityAddon.get().getBlockBenchManager().addModelLocator(defaultModelFile.getResourceLocator());
            if(!SubstancePlugin.get().getConfig().contains("entity.hurtColor") || SubstancePlugin.get().getConfig().getBoolean("entity.hurtColor"))
            {
                ResourcePackModel hurtModel = new BlockBenchResourcePackModel(entry.getValue(), abstractModelResourceLocator, model, true);
                ResourcePackModelFile hurtModelFile = new ResourcePackModelFile(String.format("entity/%s/hurt/%s.json", model.getModelIdentifier(), entry.getKey().getName().toLowerCase().replace(" ", "_").replace("-", "_")));
                processed.put(hurtModelFile, hurtModel);
                SubstanceEntityAddon.get().getBlockBenchManager().addModelLocator(hurtModelFile.getResourceLocator());
            }
        });
        return processed;
    }

}
