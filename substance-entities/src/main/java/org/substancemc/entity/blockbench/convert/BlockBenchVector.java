package org.substancemc.entity.blockbench.convert;

import org.joml.Vector3d;

public class BlockBenchVector extends Vector3d {

    public BlockBenchVector(double[] xyz)
    {
        super(xyz[0], xyz[1], xyz[2]);
    }

    public BlockBenchVector() {
    }

    public double[] toArray()
    {
        return new double[]{x, y, z};
    }

    public BlockBenchVector add(double d)
    {
        this.x = x + d;
        this.y = y + d;
        this.z = z + d;
        return this;
    }

    public BlockBenchVector mul(double d)
    {
        super.mul(d);
        return this;
    }

    public BlockBenchVector add(BlockBenchVector other)
    {
        super.add(other);
        return this;
    }

    public BlockBenchVector sub(BlockBenchVector other)
    {
        super.sub(other);
        return this;
    }

}
