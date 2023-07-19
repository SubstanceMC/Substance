package org.substancemc.core.resourcepack.generator.model;

import org.substancemc.core.resourcepack.generator.GeneratorPreProcessor;
import org.substancemc.core.resourcepack.generator.ResourcePackGenerator;
import org.substancemc.core.resourcepack.structure.substance.ResourcePackModel;
import org.substancemc.core.util.gson.GsonFileWriter;
import org.substancemc.core.util.gson.GsonWriter;

import java.util.Map;

public class ResourcePackModelGenerator<T> implements ResourcePackGenerator<T, Map<ResourcePackModelFile, ResourcePackModel>> {

    public void generate(T context) {
            Map<ResourcePackModelFile, ResourcePackModel> processed = getProcessor().process(context);
            GsonWriter<ResourcePackModel> writer = new GsonFileWriter<>();
            processed.keySet().forEach(file -> {
                ResourcePackModel model = processed.get(file);
                writer.write(file.getFile(), model);
            });
    }

    private final GeneratorPreProcessor<T, Map<ResourcePackModelFile, ResourcePackModel>> processor;
    public ResourcePackModelGenerator(GeneratorPreProcessor<T, Map<ResourcePackModelFile, ResourcePackModel>> processor)
    {
        this.processor = processor;
    }


    @Override
    public GeneratorPreProcessor<T, Map<ResourcePackModelFile, ResourcePackModel>> getProcessor() {
        return processor;
    }
}
