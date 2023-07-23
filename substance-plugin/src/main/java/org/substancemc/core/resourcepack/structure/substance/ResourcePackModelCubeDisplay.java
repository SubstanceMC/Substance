package org.substancemc.core.resourcepack.structure.substance;

public class ResourcePackModelCubeDisplay {
    private double[] translation = new double[]{0.0, -6.4, 0.0};
    private double[] rotation = new double[]{0.0, 0.0, 0.0};
    private double[] scale = new double[3];

    public ResourcePackModelCubeDisplay() {
    }

    public void moveTranslation(double... offsets) {
        translation[0] -= offsets[0] * this.scale[0];
        translation[1] -= offsets[1] * this.scale[1];
        translation[2] -= offsets[2] * this.scale[2];
    }

    public void setTranslation(double x, double y, double z) {
        this.translation = new double[] {x,y,z};
    }

    public void setRotation(double x, double y, double z) {
        this.rotation = new double[] {x,y,z};
    }

    public void setScale(double x, double y, double z) {
        this.scale = new double[] {x,y,z};
    }

    public boolean checkTranslation() {
        return Math.abs(this.translation[0]) <= 80.0 && Math.abs(this.translation[1]) <= 80.0 && Math.abs(this.translation[2]) <= 80.0;
    }

    public double[] getTranslation() {
        return this.translation;
    }

    public double[] getRotation()
    {
        return this.rotation;
    }

    public double[] getScale() {
        return this.scale;
    }
}
