package org.substancemc.core.resourcepack.structure.minecraft.atlas;

public class ResourcePackAtlasEntry {
    private String prefix;
    private String source;
    private String type;

    public ResourcePackAtlasEntry(String id, String type)
    {
        this.source = id;
        this.type = type;
        this.prefix = this.source + "/";
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


}
