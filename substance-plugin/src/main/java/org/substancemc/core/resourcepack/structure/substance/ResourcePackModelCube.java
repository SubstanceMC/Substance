package org.substancemc.core.resourcepack.structure.substance;


import org.substancemc.core.resourcepack.structure.minecraft.model.ModelCube;
import org.substancemc.core.resourcepack.structure.minecraft.model.ModelElementFacing;

import java.util.HashMap;
import java.util.Map;

public class ResourcePackModelCube {

    private final transient DoubleModifiableComponent fromComponent = new DoubleModifiableComponent(new double[3]);
    private final double[] from = fromComponent.get();
    private final transient DoubleModifiableComponent toComponent = new DoubleModifiableComponent(new double[3]);
    private final double[] to = toComponent.get();
    private ModelElementFacing faces;

    private Rotation rotation;

    public ResourcePackModelCube(ModelCube cube)
    {
        fromComponent.set(cube.getFrom());
        toComponent.set(cube.getTo());
        faces = cube.getFaces();
    }

    public DoubleModifiableComponent getFrom() {
        return fromComponent;
    }

    public DoubleModifiableComponent getTo() {
        return toComponent;
    }

    public void shrinkCube(double ratio)
    {
        getFrom().shrink(ratio);
        getTo().shrink(ratio);
    }

    public boolean checkFrom()
    {
        return this.from[0] <= 32.0F && this.from[0] >= -16.0F && this.from[1] <= 32.0F && this.from[1] >= -16.0F && this.from[2] <= 32.0F && this.from[2] >= -16.0F;
    }

    public boolean checkTo()
    {
        return this.to[0] <= 32.0F && this.to[0] >= -16.0F && this.to[1] <= 32.0F && this.to[1] >= -16.0F && this.to[2] <= 32.0F && this.to[2] >= -16.0F;
    }

    public ModelElementFacing getFaces() {
        return faces;
    }

    public Rotation getRotation()
    {
        return rotation;
    }

    public void setRotation(Rotation rotation)
    {
        this.rotation = rotation;
    }

    public void setFaces(ModelElementFacing faces) {
        this.faces = faces;
    }
}
