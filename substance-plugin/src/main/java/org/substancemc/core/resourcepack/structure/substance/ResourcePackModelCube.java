package org.substancemc.core.resourcepack.structure.substance;


import org.substancemc.core.resourcepack.structure.minecraft.model.ModelElementFacing;
import org.substancemc.core.resourcepack.structure.minecraft.model.ModelCube;

public class ResourcePackModelCube {

    private double[] from;
    private double[] to;
    private ModelElementFacing faces;

    public ResourcePackModelCube(ModelCube cube)
    {
        from = cube.getFrom();
        to = cube.getTo();
        faces = cube.getFaces();

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
