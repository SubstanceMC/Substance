package org.substancemc.core.resourcepack.generator;

public interface ResourcePackGenerator<T, E> {
    void generate(T context);

    GeneratorPreProcessor<T, E> getProcessor();

}
