package org.substancemc.core.resourcepack.structure.minecraft.model;

public class ModelCube {
    protected double[] from;
    protected double[] to;
    protected ModelElementFacing faces;

    public ModelCube(double[] from, double[] to, ModelElementFacing faces)
    {
        this.from = from;
        this.to = to;
        this.faces = faces;
    }

    public double[] getFrom() {
        return from;
    }

    public void setFrom(double[] from) {
        this.from = from;
    }

    public double[] getTo() {
        return to;
    }

    public void setTo(double[] to) {
        this.to = to;
    }

    public ModelElementFacing getFaces() {
        return faces;
    }

    public void setFaces(ModelElementFacing faces) {
        this.faces = faces;
    }
}
