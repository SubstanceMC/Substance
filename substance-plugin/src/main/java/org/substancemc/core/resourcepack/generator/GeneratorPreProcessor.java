package org.substancemc.core.resourcepack.generator;

public interface GeneratorPreProcessor<T, E> {
    E process(T original);
}
