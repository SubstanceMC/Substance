package org.substancemc.core.util.gson;

import java.io.File;

public interface GsonWriter<T> {
    void write(File file, T toJson);
}
