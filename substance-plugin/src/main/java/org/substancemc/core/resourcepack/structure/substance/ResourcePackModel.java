package org.substancemc.core.resourcepack.structure.substance;

import java.util.HashMap;
import java.util.Map;

public class ResourcePackModel {
    private int[] texture_size;
    private Map<String, String> textures;
    private ResourcePackModelCube[] elements;

    private String parent;

    private Map<String, ResourcePackModelCubeDisplay> display;


    public Map<String, ResourcePackModelCubeDisplay> getDisplay()
    {
        if(display == null) display = new HashMap<>();
        return display;
    }

    public String getParent()
    {
        return parent;
    }

    public void setParent(String parent)
    {
        this.parent = parent;
    }

    public void setDisplay(Map<String, ResourcePackModelCubeDisplay> display)
    {
        this.display = display;
    }

    public int[] getTextureSize() {
        return texture_size;
    }

    public void setTextureSize(int[] textureSize) {
        this.texture_size = textureSize;
    }

    public Map<String, String> getTextures() {
        return textures;
    }

    public void setTextures(Map<String, String> textures) {
        this.textures = textures;
    }

    public ResourcePackModelCube[] getElements() {
        return elements;
    }

    public void setElements(ResourcePackModelCube[] elements) {
        this.elements = elements;
    }

    public ResourcePackModelCube getElement(int index)
    {
        return getElements()[index];
    }


}
