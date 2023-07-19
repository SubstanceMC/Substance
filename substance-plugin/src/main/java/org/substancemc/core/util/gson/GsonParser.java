package org.substancemc.core.util.gson;

public interface GsonParser<T> {
    T parse(Object toParse, Class<T> clazz);
}
