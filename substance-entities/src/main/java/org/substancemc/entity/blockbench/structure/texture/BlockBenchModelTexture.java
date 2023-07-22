package org.substancemc.entity.blockbench.structure.texture;

public class BlockBenchModelTexture {

    private String id;
    private String source;

    private String name;

    private transient int resolution;

    public int getResolution()
    {
        return resolution;
    }

    public void setResolution(int resolution)
    {
        this.resolution = resolution;
    }

    public String getName()
    {
        return name.toLowerCase();
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

}
