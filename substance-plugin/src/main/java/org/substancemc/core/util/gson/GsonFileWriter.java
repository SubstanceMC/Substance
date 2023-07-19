package org.substancemc.core.util.gson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class GsonFileWriter<T> implements GsonWriter<T> {
    @Override
    public void write(File file, T toJson) {
        if(!file.getParentFile().exists()) file.getParentFile().mkdirs();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (FileWriter writer = new FileWriter(file)) {
            if(!file.exists()) file.createNewFile();
            gson.toJson(toJson, writer);
            writer.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
