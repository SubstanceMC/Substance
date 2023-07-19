package org.substancemc.core.resourcepack.generator.atlas;

import org.substancemc.core.resourcepack.ResourcePackFile;

public class ResourcePackAtlasFile extends ResourcePackFile {
    public ResourcePackAtlasFile(String relativePath) {
        super("minecraft/atlases/" + relativePath);
    }
}
