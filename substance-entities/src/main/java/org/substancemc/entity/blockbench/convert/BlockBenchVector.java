package org.substancemc.entity.blockbench.convert;

import org.joml.Vector3d;

public class BlockBenchVector extends Vector3d {

    public BlockBenchVector(double[] xyz)
    {
        if(xyz == null || xyz.length < 3) {
            this.x = 0;
            this.y = 0;
            this.z = 0;
            return;
        }
        this.x = xyz[0];
        this.y =  xyz[1];
        this.z = xyz[2];
    }

    public BlockBenchVector() {
        super();
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

    public BlockBenchVector sub(double d)
    {
        this.x = x - d;
        this.y = y - d;
        this.z = z - d;
        return this;
    }

}
