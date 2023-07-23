package org.substancemc.core.resourcepack.structure.substance;

public class DoubleModifiableComponent implements ModifiableComponent {

    private final transient double[] toUpdate;

    public DoubleModifiableComponent(double[] toUpdate)
    {
        this.toUpdate = toUpdate;
    }

    public double[] get()
    {
        return toUpdate;
    }

    public void set(double... set)
    {
        toUpdate[0] = set[0];
        toUpdate[1] = set[1];
        toUpdate[2] = set[2];
    }

    @Override
    public void addOffset(double... offset) {
        toUpdate[0] = toUpdate[0] + offset[0];
        toUpdate[1] = toUpdate[1] + offset[1];
        toUpdate[2] = toUpdate[2] + offset[2];
    }

    @Override
    public void shrink(double ratio) {
        toUpdate[0] = shrink(toUpdate[0], ratio);
        toUpdate[1] = shrink(toUpdate[1], ratio);
        toUpdate[2] = shrink(toUpdate[2], ratio);
    }
}
