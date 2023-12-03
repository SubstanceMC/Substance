package org.substancemc.entity.blockbench.convert.util;

import org.joml.AxisAngle4f;
import org.joml.Quaternionf;
import org.substancemc.entity.blockbench.convert.BlockBenchVector;

public class BlockBenchRotationVector extends BlockBenchVector {

    public BlockBenchRotationVector(double[] rotation)
    {
        super(rotation);
    }

    public BlockBenchRotationVector() {
    }

    public AxisAngle4f getLeftRotation()
    {
        Quaternionf rotationQuaternion = new Quaternionf().rotationXYZ(
                (float) Math.toRadians(x),
                (float) Math.toRadians(y),
                (float) Math.toRadians(z)
        );

        // Convert quaternion to AxisAngle4f
        AxisAngle4f leftRotation = new AxisAngle4f();
        leftRotation.set(rotationQuaternion);

        return leftRotation;
    }

    public AxisAngle4f getRightRotation()
    {
        Quaternionf rotationQuaternion = new Quaternionf().rotationXYZ(
                (float) Math.toRadians(x),
                (float) Math.toRadians(y),
                (float) Math.toRadians(z)
        );

        // Invert the quaternion to get the opposite rotation
        rotationQuaternion.invert();

        // Convert quaternion to AxisAngle4f
        AxisAngle4f rightRotation = new AxisAngle4f();
        rightRotation.set(rotationQuaternion);

        return rightRotation;
    }

}
