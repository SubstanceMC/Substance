package org.substancemc.core.resourcepack.structure.substance;

import java.util.Map;

public class ResourcePackModel {
    private int[] texture_size;
    private Map<String, String> textures;
    private ResourcePackModelCube[] elements;

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


}
