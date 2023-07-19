package org.substancemc.core.resourcepack.structure.minecraft.model;

public class ModelElementFacingInformation {
    private double[] uv;
    private String texture;

    public double[] getUV()
    {
        return uv;
    }

    public void setUV(double[] uv)
    {
        this.uv = uv;
    }

    public String getTextureId()
    {
        return texture;
    }

    public void setTextureId(String textureId)
    {
        this.texture = textureId;
    }
}
