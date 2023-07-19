package org.substancemc.core.resourcepack.structure.minecraft.atlas;

import java.util.List;

public class ResourcePackAtlasConfiguration {
    ResourcePackAtlasEntry[] sources;

    private transient String atlasId;

    public void setAtlasId(String atlasId)
    {
        this.atlasId = atlasId;
    }

    public String getAtlasId()
    {
        return atlasId;
    }

    public ResourcePackAtlasEntry[] getSources()
    {
        return sources;
    }

    public void setSources(ResourcePackAtlasEntry... sources)
    {
        this.sources = sources;
    }

    public void setSources(List<ResourcePackAtlasEntry> sources)
    {
        this.sources = new ResourcePackAtlasEntry[sources.size()];
        for(int i = 0; i < sources.size(); i++)
        {
            this.sources[i] = sources.get(i);
        }
    }

}
