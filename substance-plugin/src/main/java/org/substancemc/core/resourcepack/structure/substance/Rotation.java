package org.substancemc.core.resourcepack.structure.substance;

public class Rotation {

    private final transient DoubleModifiableComponent originComponent = new DoubleModifiableComponent(new double[3]);
    private final double[] origin = originComponent.get();
    private double angle;
    private String axis;

    public DoubleModifiableComponent getOrigin()
    {
        return originComponent;
    }

    public double getAngle()
    {
        return angle;
    }

    public void setAngle(double angle)
    {
        this.angle = angle;
    }

    public String getAxis()
    {
        return axis;
    }

    public void setAxis(String axis)
    {
        this.axis = axis;
    }

    public Double getOrigin(int i)
    {
        if(origin.length == 0) return null;
        i = Math.max(0, Math.min(origin.length - 1, i));
        return origin[i];
    }


}
