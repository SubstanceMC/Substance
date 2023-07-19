package org.substancemc.core.resourcepack.generator.atlas;

import org.substancemc.core.resourcepack.structure.minecraft.atlas.ResourcePackAtlasConfiguration;
import org.substancemc.core.resourcepack.structure.minecraft.atlas.ResourcePackAtlasEntry;
import org.substancemc.core.util.gson.GsonFileWriter;
import org.substancemc.core.util.gson.GsonWriter;
import org.substancemc.core.resourcepack.generator.GeneratorPreProcessor;
import org.substancemc.core.resourcepack.generator.ResourcePackGenerator;

import java.util.List;
import java.util.Map;

public class ResourcePackAtlasGenerator implements ResourcePackGenerator<Map.Entry<String, List<ResourcePackAtlasEntry>>, ResourcePackAtlasConfiguration>, GeneratorPreProcessor<Map.Entry<String, List<ResourcePackAtlasEntry>>, ResourcePackAtlasConfiguration> {
    @Override
    public GeneratorPreProcessor<Map.Entry<String, List<ResourcePackAtlasEntry>>, ResourcePackAtlasConfiguration> getProcessor() {
        return this;
    }

    @Override
    public void generate(Map.Entry<String, List<ResourcePackAtlasEntry>> context) {
        ResourcePackAtlasConfiguration atlasConfig = getProcessor().process(context);
        GsonWriter<ResourcePackAtlasConfiguration> writer = new GsonFileWriter<>();
        ResourcePackAtlasFile file = new ResourcePackAtlasFile(atlasConfig.getAtlasId() + ".json");
        writer.write(file.getFile(), atlasConfig);
    }


    @Override
    public ResourcePackAtlasConfiguration process(Map.Entry<String, List<ResourcePackAtlasEntry>> original) {
        ResourcePackAtlasConfiguration processed = new ResourcePackAtlasConfiguration();
        processed.setAtlasId(original.getKey());
        processed.setSources(original.getValue());
        return processed;
    }
}
