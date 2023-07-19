package org.substancemc.core.util.gson;

import com.google.gson.Gson;
import org.substancemc.core.util.file.DataFolderFile;

import java.io.FileReader;
import java.io.IOException;

public class GsonFileParser<T> implements GsonParser<T> {

    public T parse(Object toParse, Class<T> clazz)
    {
        if(!(toParse instanceof DataFolderFile file)) return null;
        try(FileReader reader = new FileReader(file.getFile())) {
            Gson gson = new Gson();
            T result = gson.fromJson(reader, clazz);
            reader.close();
            return result;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
