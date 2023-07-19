package org.substancemc.core.resourcepack.generator.model;

import org.substancemc.core.resourcepack.ResourcePackSubstanceFile;

public class ResourcePackModelFile extends ResourcePackSubstanceFile {
    public ResourcePackModelFile(String relativePath) {
        super("models/" + relativePath);
        this.relativePath = relativePath;
    }

    private final String relativePath;

    public String getTopLevelRelativePath()
    {
        return relativePath;
    }
}
