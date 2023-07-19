package org.substancemc.core.resourcepack.generator.texture;

import org.substancemc.core.resourcepack.ResourcePackSubstanceFile;

public class ResourcePackTextureFile extends ResourcePackSubstanceFile {


    public ResourcePackTextureFile(String relativePath) {
        super("textures/" + relativePath);
        this.relativePath = relativePath;
    }

    private final String relativePath;

    public String getTopLevelRelativePath()
    {
        return relativePath;
    }
}
