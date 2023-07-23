package org.substancemc.core.resourcepack.structure.substance;

public interface ModifiableComponent {

    void set(double... set);
    void addOffset(double... offset);
    void shrink(double ratio);

    default double shrink(double p, double r)
    {
        return 8.0 * (1.0 - r) + r * p;
    }
}
